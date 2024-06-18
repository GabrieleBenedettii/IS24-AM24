package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.Exceptions.*;
import it.polimi.ingsw.am24.chat.Chat;
import it.polimi.ingsw.am24.heartbeat.HeartBeat;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;
import it.polimi.ingsw.am24.modelView.PublicPlayerView;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.view.flow.utility.GameStatus;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class GameController implements GameControllerInterface, Serializable, Runnable {
    private static int gameCounter = 10000;
    private final int gameId;
    private final Game game;
    private final ArrayList<String> rotation;
    private final HashMap<String, Player> players;
    private final HashMap<String, GameListener> listeners;
    private String currentPlayer;
    private final int playerCount;
    private int readyPlayers;   //used to know how many players have already done the side of initial card choice and start the rotation
    private int readyPlayersForFirstPhase; //used to know how many players have already done the color choice

    private GameStatus status;
    private String winner;

    private final Object lockPlayers = new Object();

    private final Chat chat;

    private final Map<GameListener, HeartBeat> heartbeats;

    public GameController(int numPlayers) {
        this.game = new Game();
        this.rotation = new ArrayList<>();
        this.players = new HashMap<>();
        this.listeners = new HashMap<>();
        this.playerCount = numPlayers;
        this.readyPlayers = 0;
        this.readyPlayersForFirstPhase = 0;
        status = GameStatus.NOT_STARTED;
        gameId = gameCounter++;
        chat = new Chat();
        heartbeats = new HashMap<>();
        new Thread(this).start();
    }

    //add new player and set the game started if the player added is the last
    public void addPlayer(String nickname, GameListener listener) throws RemoteException, FullLobbyException {
        if(players.size() == playerCount) throw new FullLobbyException();
        synchronized (lockPlayers) {
            rotation.add(nickname);
            players.put(nickname, new Player(nickname));
            listeners.put(nickname, listener);
            notifyListener(listener);
        }
    }

    //return the player with the given nickname
    public Player getPlayer(String nickname){
        synchronized (lockPlayers) {
            return players.get(nickname);
        }
    }

    /*public void removePlayer(String nickname) {
        synchronized (lockPlayers){
            players.remove(nickname);
        }
    }*/

    //choice of the color by a player
    public boolean chooseColor(String nickname, String color, GameListener listener) throws RemoteException {
        //find the player
        synchronized (lockPlayers) {
            Player p = players.get(nickname);
            if (p != null) {
                PlayerColor pc = PlayerColor.valueOf(color.toUpperCase());
                //if the color is still available, set color on the model and notify
                if (game.isAvailable(pc)) {
                    p.setColor(pc);
                    game.chooseColor(pc);
                    readyPlayersForFirstPhase++;
                    if(readyPlayersForFirstPhase == playerCount) {
                        status = GameStatus.FIRST_PHASE;
                    }
                    notifyAllListeners();
                    return true;
                } else {
                    //if the color is not available, send the updated list
                    listener.notAvailableColors(new ArrayList<>(game.getAvailableColors()));
                    return false;
                }
            }
            return false;
        }
    }

    //decks creation and card's distribution
    public void startGame() {
        game.start();
        for (Player p : players.values()) {
            p.setInitialCard(game.drawInitialCard());
            try {
                p.setPlayingHand(game.drawResourceCard(), game.drawResourceCard(), game.drawGoldCard());
            } catch (EmptyDeckException ignored) {
                //can't happen
            }
        }
    }

    //choice of the hidden goal by a player
    public boolean chooseGoal(String nickname, int goalId, GameListener listener) throws RemoteException {
        synchronized (lockPlayers) {
            Player p = players.get(nickname);
            if (p != null) {
                try {
                    p.setHiddenGoal(game.chosenGoalCard(goalId));
                } catch (WrongHiddenGoalException e) {
                    return false;
                }
                readyPlayers++;
                if(readyPlayers == playerCount) {
                    Collections.shuffle(rotation);
                    currentPlayer = rotation.getFirst();
                    System.out.println("THE GAME [" + gameId + "] IS STARTING!!!");
                    status = GameStatus.RUNNING;
                    notifyAllListeners_beginTurn();
                }
                return true;
            }
            return false;
        }
    }

    //choice of the side of the initial card by a player
    public boolean chooseInitialCardSide(String nickname, boolean isFront, GameListener listener) throws RemoteException {
        synchronized (lockPlayers) {
            Player p = players.get(nickname);
            HashMap<String, PublicPlayerView> playerViews = new HashMap<>();
            for(String pl : rotation) {
                playerViews.put(pl, players.get(pl).getPublicPlayerView());
            }
            if (p != null) {
                p.getInitialcard().setFront(isFront);
                p.playInitialCard(isFront);
                listener.hiddenGoalChoice(new ArrayList<>(game.drawGoalCards().stream().map(GoalCard::getView).toList()), new GameView(p.getNickname(), gameId, p.getPrivatePlayerView(), new PublicBoardView(game.getCommonBoardView(), rotation, playerViews), null));
                return true;
            }
            return false;
        }
    }

    public boolean playCard(String nickname, int cardIndex, boolean isFront, int x, int y, GameListener listener) throws RemoteException {
        synchronized (lockPlayers) {
            Player p = players.get(nickname);
            if (p != null) {
                try {
                    p.play(cardIndex, isFront, x, y);
                    if(p.getScore() >= 20 && !status.equals(GameStatus.LAST_LAST_ROUND) && !status.equals(GameStatus.LAST_ROUND)) status = GameStatus.LAST_LAST_ROUND;
                    notifyAllListeners_beginDraw();
                    return true;
                } catch (InvalidPositioningException e) {
                    listener.invalidPositioning();
                    return false;
                } catch (RequirementsNotMetException e) {
                    listener.requirementsNotMet();
                }
            }
            return false;
        }
    }

    public boolean drawCard(String nickname, int cardIndex, GameListener listener) throws RemoteException {
        synchronized (lockPlayers) {
            Player p = players.get(nickname);
            if (p != null) {
                try {
                    switch (cardIndex) {
                        case 0 -> {
                            p.draw(game.drawnResCard(0));
                            game.addResourceCard();
                        }
                        case 1 -> {
                            p.draw(game.drawnResCard(1));
                            game.addResourceCard();
                        }
                        case 2 -> {
                            p.draw(game.drawnGoldCard(0));
                            game.addGoldCard();
                        }
                        case 3 -> {
                            p.draw(game.drawnGoldCard(1));
                            game.addGoldCard();
                        }
                        case 4 -> p.draw(game.drawResourceCard());
                        case 5 -> p.draw(game.drawGoldCard());
                    }

                    //one between the two decks is empty, the final part begins
                    if(game.goldDeckSize() == 0 || game.resDeckSize() == 0) status = GameStatus.LAST_LAST_ROUND;

                    nextPlayer();
                    notifyAllListeners_beginTurn();
                    return true;
                } catch (EmptyDeckException | AlreadyDrawnException e) {
                    return false;
                }
            }
            return false;
        }
    }

    public void nextPlayer() throws RemoteException {
        int nextPlayerIndex = rotation.indexOf(currentPlayer) + 1;
        if(nextPlayerIndex == playerCount) {
            if(status.equals(GameStatus.LAST_ROUND)) {
                synchronized (lockPlayers) {
                    for (String p : players.keySet()) {
                        status = GameStatus.ENDED;
                        players.get(p).addPoints(players.get(p).getHiddenGoal().calculatePoints(players.get(p)));
                        players.get(p).addPoints(game.getCommonGoal(0).calculatePoints(players.get(p)));
                        players.get(p).addPoints(game.getCommonGoal(1).calculatePoints(players.get(p)));
                        winner = calculateWinner();
                    }
                    notifyAllListeners_endGame();
                }
            }
            else if(status.equals(GameStatus.LAST_LAST_ROUND)) {
                status = GameStatus.LAST_ROUND;
            }
            nextPlayerIndex -= playerCount;
        }
        currentPlayer = rotation.get(nextPlayerIndex);
    }

    public String calculateWinner() {
        int max = 0;
        String win = "";
        for (String p : players.keySet()) {
            if (players.get(p).getScore() > max) {
                win = p;
                max = players.get(p).getScore();
            } else if (players.get(p).getScore() == max) {
                win = win + "," + p;
            }
        }
        return win;
    }

    public int getNumOfPlayers() {
        return players.size();
    }

    public Game getGame() {
        return game;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean sentPublicMessage(String sender, String message) throws RemoteException {
        if(!players.containsKey(sender)) return false;
        chat.addPublicMessage(sender, message);
        notifyAllListeners_sentMessage();
        return true;
    }

    public boolean sentPrivateMessage(String sender, String receiver, String message) throws RemoteException {
        if(!players.containsKey(sender)) return false;
        chat.addPrivateMessage(sender, receiver, message);
        notifyAllListeners_sentMessage();
        return true;
    }

    //if the game is started, it sends the list of players in the lobby, otherwise it sends the secret cards
    private void notifyAllListeners() throws RemoteException {
        if(status == GameStatus.FIRST_PHASE) {
            startGame();
            for (String p : listeners.keySet()) {
                if (p != null && listeners.get(p) != null) {
                    GameCardView front = players.get(p).getInitialcard().getView();
                    GameCardView back = players.get(p).getInitialcard().getBackView();
                    new Thread(() -> {
                        try {
                            listeners.get(p).initialCardSide(front, back);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }
            }
        }
        else {
            for(String p : listeners.keySet()){
                if(p != null && listeners.get(p) != null) {
                    new Thread(() -> {
                        try {
                            listeners.get(p).playerJoined(new ArrayList<>(players.keySet().stream().toList()),players.get(p).getNickname(), playerCount);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    }
            }
        }
    }

    private void notifyAllListeners_beginTurn() throws RemoteException {
        HashMap<String, PublicPlayerView> playerViews = new HashMap<>();
        for(String pl : rotation) {
            playerViews.put(pl, players.get(pl).getPublicPlayerView());
        }
        for (String p : listeners.keySet()) {
            if (p != null && listeners.get(p) != null) {
                GameView gw = new GameView(currentPlayer, gameId, players.get(p).getPrivatePlayerView(), new PublicBoardView(game.getCommonBoardView(), rotation, playerViews), status);
                new Thread(() -> {
                    try {
                        listeners.get(p).beginTurn(gw);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }

    private void notifyAllListeners_beginDraw() throws RemoteException {
        HashMap<String, PublicPlayerView> playerViews = new HashMap<>();
        for(String pl : rotation) {
            playerViews.put(pl, players.get(pl).getPublicPlayerView());
        }
        for (String p : listeners.keySet()) {
            if (p != null && listeners.get(p) != null) {
                GameView gw = new GameView(currentPlayer, gameId, players.get(p).getPrivatePlayerView(), new PublicBoardView(game.getCommonBoardView(), rotation, playerViews), status);
                new Thread(() -> {
                    try {
                        listeners.get(p).beginDraw(gw);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }

    private void notifyAllListeners_endGame() throws RemoteException {
        HashMap<String, Integer> rank = new HashMap<>();
        for (String p : listeners.keySet()) {
            rank.put(p, players.get(p).getScore());
        }
        for (String p : listeners.keySet()) {
            if (p != null && listeners.get(p) != null)
                new Thread(() -> {
                    try {
                        listeners.get(p).gameEnded(winner, rank);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
        }
    }

    private void notifyAllListeners_sentMessage() throws RemoteException {
        synchronized (chat) {
            for (String p : listeners.keySet()) {
                if(chat.getLast().getReceiver().isEmpty() || chat.getLast().getReceiver().equals(p) || chat.getLast().getSender().equals(p))
                    new Thread(() -> {
                        try {
                            listeners.get(p).sentMessage(chat.getLast().getSender(), chat.getLast().getReceiver(), chat.getLast().getMessage(), chat.getLast().getTime());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
            }
        }
    }

    //notify the single listener for the color choice
    private void notifyListener(GameListener listener) throws RemoteException {
        ArrayList<String> ac = new ArrayList<>(game.getAvailableColors());
        new Thread(() -> {
            try {
                listener.availableColors(ac);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public int getGameId() {
        return gameId;
    }

    public GameStatus getStatus() {
        return status;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            //checks all the heartbeat to detect disconnection
            if (status != null && !status.equals(GameStatus.NOT_STARTED)) {
                synchronized (heartbeats) {
                    Iterator<Map.Entry<GameListener, HeartBeat>> heartIter = heartbeats.entrySet().iterator();

                    while (heartIter.hasNext()) {
                        Map.Entry<GameListener, HeartBeat> el = (Map.Entry<GameListener, HeartBeat>) heartIter.next();
                        if (System.currentTimeMillis() - el.getValue().getBeat() > 4000) {
                            try {
                                this.disconnectPlayer(el.getValue().getNickname());
                                System.out.println("Disconnection detected by heartbeat of player: "+el.getValue().getNickname()+"");

                                if (this.players.isEmpty()) {
                                    LobbyController.getInstance().deleteGame(this.getGameId());
                                    return;
                                }

                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }

                            heartIter.remove();
                        }
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public synchronized void heartbeat(String nickname, GameListener listener) throws RemoteException {
        synchronized (heartbeats) {
            heartbeats.put(listener, new HeartBeat(System.currentTimeMillis(), nickname));
        }
    }

    //@Override
    public void disconnectPlayer(String nickname) throws RemoteException {
        players.remove(nickname);
        rotation.remove(nickname);
        listeners.remove(nickname);
        //Check if there is only one player playing
        if ((status.equals(GameStatus.FIRST_PHASE) || status.equals(GameStatus.RUNNING) || status.equals(GameStatus.LAST_LAST_ROUND) || status.equals(GameStatus.LAST_ROUND)) && players.size() == 1) {
            HashMap<String, Integer> rank = new HashMap<>();
            Player p = players.values().stream().toList().getFirst();
            winner = p.getNickname();
            rank.put(winner, players.get(winner).getScore());
            listeners.get(winner).gameEnded(winner, rank);
        }
    }
}

