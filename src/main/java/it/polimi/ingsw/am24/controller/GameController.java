package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.exceptions.*;
import it.polimi.ingsw.am24.chat.Chat;
import it.polimi.ingsw.am24.network.heartbeat.HeartBeat;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.modelview.PublicBoardView;
import it.polimi.ingsw.am24.modelview.PublicPlayerView;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.view.GameStatus;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

/**
 * The GameController class manages the game state, player actions, and game flow.
 * It implements the GameControllerInterface and Serializable and also runs a thread to handle game operations.
 */
public class GameController implements GameControllerInterface, Serializable, Runnable {
    private static int gameCounter = 10000;
    private final int gameId;
    private final Game game;
    private final ArrayList<String> rotation;
    private final HashMap<String, Player> players;
    private final HashMap<String, GameListener> listeners;
    private String currentPlayer;
    private int playerCount;
    private int readyPlayers;   //used to know how many players have already done the side of initial card choice and start the rotation
    private int readyPlayersForFirstPhase; //used to know how many players have already done the color choice

    private GameStatus status;
    private String winner;

    private final Object lockPlayers = new Object();

    private final Chat chat;

    private final transient Map<GameListener, HeartBeat> heartbeats;

    /**
     * Constructor for the GameController class.
     * Initializes the game, player lists, and game settings.
     * @param numPlayers the number of players in the game.
     */
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

    /**
     * Adds a player to the game.
     * @param nickname the player's nickname.
     * @param listener the GameListener for the player.
     * @throws RemoteException if there is a network error.
     * @throws FullLobbyException if the lobby is full.
     */
    public void addPlayer(String nickname, GameListener listener) throws RemoteException, FullLobbyException {
        if(players.size() == playerCount) throw new FullLobbyException();
        synchronized (lockPlayers) {
            rotation.add(nickname);
            players.put(nickname, new Player(nickname));
            listeners.put(nickname, listener);
            notifyListener_availableColors(listener);
        }
    }

    /**
     * Returns the player with the given nickname.
     * @param nickname the player's nickname.
     * @return the Player object.
     */
    public Player getPlayer(String nickname){
        synchronized (lockPlayers) {
            return players.get(nickname);
        }
    }

    /**
     * Sets a player's color attribute with the given color.
     * @param nickname the player's nickname.
     * @param color the color chosen by the player.
     * @param listener the GameListener for the player.
     * @return true if the color choice is successful, false otherwise.
     * @throws RemoteException if there is a network error.
     */
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
                    notifyAllListeners_firstPhase();
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

    /**
     * Starts the game by initializing decks and distributing initial cards to players.
     */
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

    /**
     * Sets the players hidden goal attribute with the given choice.
     * @param nickname the player's nickname.
     * @param goalId the ID of the chosen goal.
     * @param listener the GameListener for the player.
     * @return true if the goal choice is successful, false otherwise.
     * @throws RemoteException if there is a network error.
     */
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

    /**
     * Sets a player initial card side attribute with the given choice.
     * @param nickname the player's nickname.
     * @param isFront true if the front side is chosen, false otherwise.
     * @param listener the GameListener for the player.
     * @return true if the choice is successful, false otherwise.
     * @throws RemoteException if there is a network error.
     */
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

    /**
     * Allows a player to play a card.
     * @param nickname the player's nickname.
     * @param cardIndex the index of the card to be played.
     * @param isFront true if the front side is played, false otherwise.
     * @param x the x-coordinate for card placement.
     * @param y the y-coordinate for card placement.
     * @param listener the GameListener for the player.
     * @return true if the card play is successful, false otherwise.
     * @throws RemoteException if there is a network error.
     */
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

    /**
     * Allows a player to draw a card.
     * @param nickname the player's nickname.
     * @param cardIndex the index of the card to be drawn.
     * @param listener the GameListener for the player.
     * @return true if the card draw is successful, false otherwise.
     * @throws RemoteException if there is a network error.
     */
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
                    if(!status.equals(GameStatus.ENDED))
                        notifyAllListeners_beginTurn();
                    return true;
                } catch (EmptyDeckException | AlreadyDrawnException e) {
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * Shifts the players following the turn rotation.
     * @throws RemoteException if there is a network error.
     */
    public void nextPlayer() throws RemoteException {
        if(players.isEmpty()) return;
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
                    return;
                }
            }
            else if(status.equals(GameStatus.LAST_LAST_ROUND)) {
                status = GameStatus.LAST_ROUND;
            }
            nextPlayerIndex -= playerCount;
        }
        currentPlayer = rotation.get(nextPlayerIndex);
    }

    /**
     * Checks for a game winner and returns its name.
     * @return the winner's nickname.
     */
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

    /**
     * Returns the number of players of the game.
     * @return the player number.
     */
    public int getNumOfPlayers() {
        return players.size();
    }

    /**
     * Returns the Game object of the game.
     * @return the game object.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Returns the nickname of the current player.
     * @return the player's nickname.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the new current player.
     * @param currentPlayer the new current player nickname.
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Sends a chat message from a player to the public chat.
     * @param sender the player's nickname.
     * @param message the chat message.
     * @throws RemoteException if there is a network error.
     */
    public boolean sentPublicMessage(String sender, String message) throws RemoteException {
        if(!players.containsKey(sender)) return false;
        chat.addPublicMessage(sender, message);
        notifyAllListeners_sentMessage();
        return true;
    }

    /**
     * Sends a chat message from a player to the private chat.
     * @param sender the player's nickname.
     * @param receiver the receiver player name.
     * @param message the chat message.
     * @throws RemoteException if there is a network error.
     */
    public boolean sentPrivateMessage(String sender, String receiver, String message) throws RemoteException {
        if(!players.containsKey(sender)) return false;
        chat.addPrivateMessage(sender, receiver, message);
        notifyAllListeners_sentMessage();
        return true;
    }

    /**
     * Notifies all players of a game update.
     * @throws RemoteException if there is a network error.
     */
    private void notifyAllListeners_firstPhase() throws RemoteException {
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

    /**
     * Notifies all players to begin their turn.
     * @throws RemoteException if there is a network error.
     */
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

    /**
     * Notifies all players to begin drawing cards.
     * @throws RemoteException if there is a network error.
     */
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

    /**
     * Notifies all listeners that the game has ended and provides the final ranking of players.
     * @throws RemoteException if there is a network error during communication with listeners.
     */
    private void notifyAllListeners_endGame() throws RemoteException {
        HashMap<String, Integer> rank = new HashMap<>();
        String win = winner;
        for (String p : listeners.keySet()) {
            rank.put(p, players.get(p).getScore());
        }
        for (String p : listeners.keySet()) {
            if (p != null && listeners.get(p) != null)
                new Thread(() -> {
                    try {
                        listeners.get(p).gameEnded(win, rank);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
        }
    }

    /**
     * Notifies all listeners about a new message sent in the chat.
     * @throws RemoteException if there is a network error during communication with listeners.
     */
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

    /**
     * Notifies a specific player listener of a game update.
     * @param listener the GameListener to notify.
     * @throws RemoteException if there is a network error.
     */
    private void notifyListener_availableColors(GameListener listener) throws RemoteException {
        ArrayList<String> ac = new ArrayList<>(game.getAvailableColors());
        new Thread(() -> {
            try {
                listener.availableColors(ac);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * Returns the game ID.
     * @return the game ID.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Returns the game status.
     * @return the GameStatus.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Periodically checks the game status and performs necessary updates.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (status != null) {
                synchronized (heartbeats) {
                    Iterator<Map.Entry<GameListener, HeartBeat>> heartIter = heartbeats.entrySet().iterator();

                    while (heartIter.hasNext()) {
                        Map.Entry<GameListener, HeartBeat> el = (Map.Entry<GameListener, HeartBeat>) heartIter.next();
                        if (System.currentTimeMillis() - el.getValue().getBeat() > 4000) {
                            try {
                                this.disconnectPlayer(el.getValue().getNickname());
                                System.out.println("[" + gameId + "] disconnection detected by heartbeat of player: " + el.getValue().getNickname());

                                if (this.players.isEmpty()) {
                                    LobbyController.getInstance().deleteGame(this.getGameId());
                                    return;
                                }

                            } catch (RemoteException | NotExistingPlayerException e) {
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

    /**
     * Handles the heartbeat signal from a player to indicate their active presence.
     * @param nickname The nickname of the player sending the heartbeat.
     * @param listener The GameListener associated with the player.
     * @throws RemoteException if there is a network error during communication with the listener.
     */
    @Override
    public synchronized void heartbeat(String nickname, GameListener listener) throws RemoteException {
        synchronized (heartbeats) {
            heartbeats.put(listener, new HeartBeat(System.currentTimeMillis(), nickname));
        }
    }

    /**
     * Disconnects a player from the game.
     * Removes the player from the player list, rotation, and listeners.
     * Checks if there is only one player left in the game and notifies the listener about the game end.
     * @param nickname The nickname of the player to disconnect.
     * @throws RemoteException if there is a network error during communication with the listener.
     */
    @Override
    public void disconnectPlayer(String nickname) throws RemoteException, NotExistingPlayerException {
        players.remove(nickname);
        rotation.remove(nickname);
        listeners.remove(nickname);
        playerCount--;
        LobbyController.getInstance().disconnectPlayer(nickname);

        //Check if there is only one player playing
        if ((status.equals(GameStatus.FIRST_PHASE) || status.equals(GameStatus.RUNNING) || status.equals(GameStatus.LAST_LAST_ROUND) || status.equals(GameStatus.LAST_ROUND)) && players.size() == 1) {
            HashMap<String, Integer> rank = new HashMap<>();
            Player p = players.values().stream().toList().getFirst();
            winner = p.getNickname();
            rank.put(winner, players.get(winner).getScore());
            listeners.get(winner).gameEnded(winner, rank);
            return;
        }

        if(currentPlayer != null && currentPlayer.equals(nickname)) {
            nextPlayer();
            notifyAllListeners_beginTurn();
        }
    }
}

