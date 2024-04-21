package it.polimi.ingsw.am24.Controller;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class GameController implements GameControllerInterface, Serializable, Runnable {
    private static int gameCounter = 10000;
    private int gameId;
    private Game game;
    private ArrayList<String> rotation;
    private HashMap<String, Player> players;
    private HashMap<String, GameListener> listeners;
    private String currentPlayer;
    private int playerCount;
    private boolean started;
    private int readyPlayers;   //used to know how many players have already done the side of initial card choice
                                // and start the rotation
    private boolean beginEndGame;
    private boolean isLastRound;
    private String winner;

    public GameController(int numPlayers) {
        this.game = new Game();
        this.rotation = new ArrayList<>();
        this.players = new HashMap<>();
        this.listeners = new HashMap<>();
        this.playerCount = numPlayers;
        this.readyPlayers = 0;
        beginEndGame = false;
        isLastRound = false;
        started = false;
        gameId = gameCounter++;
        new Thread(this).start();
    }

    //add new player and set the game started if the player added is the last
    public boolean addNewPlayer(String nickname, GameListener listener) throws RemoteException {
        boolean res = false;
        synchronized (players) {
            rotation.add(nickname);
            players.put(nickname, new Player(nickname));
            listeners.put(nickname, listener);
            if(players.size() == playerCount){
                started = true;
                res = true;
            }
            notifyListener(listener);
        }
        return res;
    }

    //return the player with the given nickname
    public Player getPlayer(String nickname){
        synchronized (players) {
            return players.get(nickname);
        }
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void removePlayer(String nickname) {
        synchronized (players){
            players.remove(getPlayer(nickname));
        }
    }
    //choice of the color by a player
    public boolean chooseColor(String nickname, String color, GameListener listener) throws RemoteException {
        //find the player
        synchronized (players) {
            Player p = players.get(nickname);
            if (p != null) {
                PlayerColor pc = PlayerColor.valueOf(color.toUpperCase());
                //if the color is still available, set color on the model and notify
                if (game.isAvailable(pc)) {
                    p.setColor(pc);
                    game.chooseColor(pc);
                    notifyAllListeners();
                    return true;
                } else {
                    //if the color is not available, send the updated list
                    listener.availableColors(new ArrayList<>(game.getAvailableColors()));
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
            p.setPlayingHand(game.drawResourceCard(), game.drawResourceCard(), game.drawGoldCard());
        }
    }

    //choice of the hidden goal by a player
    public boolean chooseGoal(String nickname, int goalId, GameListener listener) throws RemoteException {
        synchronized (players) {
            Player p = players.get(nickname);
            if (p != null) {
                p.setHiddenGoal(game.chosenGoalCard(goalId));
                listener.initialCardSide(p.getInitialcard().getView(), p.getInitialcard().getBackView());
                return true;
            }
            return false;
        }
    }

    //choice of the side of the initial card by a player
    public boolean chooseInitialCardSide(String nickname, boolean isFront, GameListener listener) throws RemoteException {
        synchronized (players) {
            Player p = players.get(nickname);
            if (p != null) {
                p.getInitialcard().setFront(isFront);
                p.playInitialCard(isFront);
                readyPlayers++;
                if(readyPlayers == playerCount) {
                    Collections.shuffle(rotation);
                    currentPlayer = rotation.get(0);
                    System.out.println("THE GAME IS STARTING!!!");
                    notifyAllListeners();
                }
                return true;
            }
            return false;
        }
    }

    public boolean playCard(String nickname, int cardIndex, boolean isFront, int x, int y, GameListener listener) throws RemoteException {
        synchronized (players) {
            Player p = players.get(nickname);
            if (p != null) {
                boolean res = p.play(cardIndex, isFront, x, y);
                if(res) {
                    if(p.getScore() >= 20 && !beginEndGame) beginEndGame = true;
                    listener.beginDraw();
                    return true;
                }
                else {
                    notifyAllListeners();
                    return false;
                }
            }
            return false;
        }
    }

    public boolean drawCard(String nickname, int cardIndex, GameListener listener) throws RemoteException {
        synchronized (players) {
            Player p = players.get(nickname);
            if (p != null) {
                boolean res = false;
                switch (cardIndex) {
                    case 0 -> {
                        res = p.draw(game.getVisibleResCard().get(0));
                        game.addResourceCard();
                    }
                    case 1 -> {
                        res = p.draw(game.getVisibleResCard().get(1));
                        game.addResourceCard();
                    }
                    case 2 -> {
                        res = p.draw(game.getVisibleGoldCard().get(0));
                        game.addGoldCard();
                    }
                    case 3 -> {
                        res = p.draw(game.getVisibleGoldCard().get(1));
                        game.addGoldCard();
                    }
                    case 4 -> res = p.draw(game.drawResourceCard());
                    case 5 -> res = p.draw(game.drawGoldCard());
                }

                if(res) {
                    nextPlayer();
                    notifyAllListeners();
                    return true;
                }
                else {
                    notifyAllListeners();
                    return false;
                }
            }
            return false;
        }
    }

    public void nextPlayer() throws RemoteException {
        int max = 0;
        int nextPlayerIndex = rotation.indexOf(currentPlayer) + 1;
        if(nextPlayerIndex == playerCount) {
            if(isLastRound) {
                for(String p : players.keySet()) {
                    players.get(p).addPoints(players.get(p).getHiddenGoal().calculatePoints(players.get(p)));
                    players.get(p).addPoints(game.getCommonGoal(0).calculatePoints(players.get(p)));
                    players.get(p).addPoints(game.getCommonGoal(1).calculatePoints(players.get(p)));
                    winner = calculateWinner();
                }
                notifyAllListenersEndGame();
            }
            else if(beginEndGame) {
                isLastRound = true;
            }
            nextPlayerIndex -= playerCount;
        }
        currentPlayer = rotation.get(nextPlayerIndex);
    }

    public boolean isMyTurn(String nickname){
        return currentPlayer.equals(getPlayer(nickname));
    }

    public String calculateWinner() {
        int max = 0;
        String win = "";
        for (String p : players.keySet()) {
            if (players.get(p).getScore() > max) {
                win = p;
            } else if (players.get(p).getScore() == max) {
                win = win + "," + p;
            }
        }
        return win;
    }

    public int getNumOfActivePlayers() {
        return players.size();
    }

    public boolean isFull() {
        return playerCount == players.size();
    }

    public void disconnectPlayer(){
        //Todo ?
    }

    //if the game is started, it sends the list of players in the lobby, otherwise it sends the secret cards
    private void notifyAllListeners() throws RemoteException {
        synchronized (players){
            if(started) {
                //if the game is started and all players are ready for the rotation
                if(readyPlayers == playerCount) {
                    for (String p : listeners.keySet()) {
                        if (p != null && listeners.get(p) != null) listeners.get(p).beginTurn(new GameView(currentPlayer, gameId, players.get(p).getPlayerView(), game.getPublicBoardView()));
                    }
                }
                //if the game is started, but they have to choose secret goal card and initial card side
                else {
                    startGame();
                    for (String p : listeners.keySet()) {
                        if (p != null && listeners.get(p) != null) listeners.get(p).hiddenGoalChoice(new ArrayList<>(game.drawGoalCards().stream().map(gc -> gc.getView()).toList()));
                    }
                }
            }
            else{
                for(String p : listeners.keySet()){
                    if(p != null && listeners.get(p) != null) listeners.get(p).playerJoined(new ArrayList<>(players.keySet().stream().toList()));
                }
            }
        }
    }

    private void notifyAllListenersEndGame() throws RemoteException {
        synchronized (players) {
            HashMap<String, Integer> rank = new HashMap<>();
            for (String p : listeners.keySet()) {
                rank.put(p, players.get(p).getScore());
            }
            for (String p : listeners.keySet()) {
                if (p != null && listeners.get(p) != null) listeners.get(p).gameEnded(winner, rank);
            }
        }
    }

    //notify the single listener for the color choice
    private void notifyListener(GameListener listener) throws RemoteException {
        listener.availableColors(new ArrayList<>(game.getAvailableColors()));
    }

    public int getGameId() {
        return gameId;
    }

    @Override
    public void run() {

    }
}
