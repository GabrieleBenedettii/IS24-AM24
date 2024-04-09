package it.polimi.ingsw.am24.Controller;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import it.polimi.ingsw.am24.model.card.PlayableCard;
import it.polimi.ingsw.am24.modelView.GameView;

import java.util.*;

public class Controller {
    private Game game;
    private ArrayList<String> rotation;
    private HashMap<String, Player> players;
    private HashMap<String, GameListener> listeners;
    private String currentPlayer;
    private int playerCount;
    private boolean started;
    private int readyPlayers;   //used to know how many players have already done the side of initial card choice
                                // and start the rotation

    public Controller(int numPlayers) {
        this.game = new Game();
        this.rotation = new ArrayList<>();
        this.players = new HashMap<>();
        this.listeners = new HashMap<>();
        this.playerCount = numPlayers;
        this.readyPlayers = 0;
        started = false;
    }

    //add new player and set the game started if the player added is the last
    public boolean addNewPlayer(String nickname, GameListener listener){
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

    //choice of the color by a player
    public boolean chooseColor(String nickname, String color, GameListener listener) {
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
                    listener.update(new AvailableColorsMessage(game.getAvailableColors()));
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
    public boolean chooseGoal(String nickname, int goalId, GameListener listener){
        synchronized (players) {
            Player p = players.get(nickname);
            if (p != null) {
                p.setHiddenGoal(game.chosenGoalCard(goalId));
                listener.update(new InitialCardDealtMessage(p.getInitialcard().getView(), p.getInitialcard().getBackView()));
                return true;
            }
            return false;
        }
    }

    //choice of the side of the initial card by a player
    public boolean chooseInitialCardSide(String nickname, boolean isFront, GameListener listener){
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

    public boolean playCard(String nickname, int cardIndex, boolean isFront, int x, int y, GameListener listener){
        synchronized (players) {
            Player p = players.get(nickname);
            if (p != null) {
                boolean res = p.play(cardIndex, isFront, x, y);
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

    /*public void drawCard(PlayableCard card){
        current.getPlayingHand().add(card);
    }*/

    public void nextPlayer(){
        currentPlayer = rotation.get((rotation.indexOf(currentPlayer) + 1) % playerCount);
    }

    public int getNumOfActivePlayers() {
        return players.size();
    }

    /*public void startEndPhase(){
        if(current.getScore() >= 20){
            //triggera l'ultimo turno dei giocatori
            if(current.equals(players.getFirst())){
                //finisco il giro
            }
            else{
                //devo fare il giro fino al giocatore che precede il corrente
            }
        }
    }
    public void endGame(){}
    public int getPlayerCount() {
        return playerCount;
    }*/

    //if the game is started, it sends the list of players in the lobby, otherwise it sends the secret cards
    private void notifyAllListeners(){
        synchronized (players){
            if(started) {
                //if the game is started and all players are ready for the rotation
                if(readyPlayers == playerCount) {
                    for (String p : listeners.keySet()) {
                        Message m = new GameViewMessage(new GameView(currentPlayer, players.get(p).getPlayerView(), game.getPublicBoardView()));
                        if (p != null && listeners.get(p) != null) listeners.get(p).update(m);
                    }
                }
                //if the game is started, but they have to choose secret goal card and initial card side
                else {
                    startGame();
                    for (String p : listeners.keySet()) {
                        Message m = new SecretObjectiveDealtMessage(game.drawGoalCards().stream().map(gc -> gc.getView()).toList());
                        if (p != null && listeners.get(p) != null) listeners.get(p).update(m);
                    }
                }
            }
            else{
                Message m = new PlayersInLobbyMessage(players.keySet().stream().toList());
                for(String p : listeners.keySet()){
                    if(p != null && listeners.get(p) != null) listeners.get(p).update(m);
                }
            }
        }
    }

    //notify the single listener for the color choice
    private void notifyListener(GameListener listener) {
        Message m = new AvailableColorsMessage(game.getAvailableColors());
        listener.update(m);
    }
}

