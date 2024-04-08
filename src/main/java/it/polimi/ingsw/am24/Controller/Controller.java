package it.polimi.ingsw.am24.Controller;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.PlayableCard;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Controller {
    private Game game;
    private ArrayList<Player> players;
    private ArrayList<GameListener> listeners;
    private Player current;
    private int playerCount;
    private boolean cardPlaced, cardDrawn, selectedSide;
    private boolean started;
    private int readyPlayers;   //used to know how many players have already done the side of initial card choice
                                // and start the rotation

    public Controller(int numPlayers) {
        this.game = new Game();
        this.players = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.playerCount = numPlayers;
        this.readyPlayers = 0;
        started = false;
    }

    //add new player and set the game started if the player added is the last
    public boolean addNewPlayer(String nickname, GameListener listener){
        boolean res = false;
        synchronized (players) {
            players.add(new Player(nickname));
            listeners.add(listener);
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
            for (Player p : players) {
                if (p.getNickname().equals(nickname)) {
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
            }
            return false;
        }
    }

    //decks creation and card's distribution
    public void startGame() {
        game.start();
        for (Player p : players) {
            p.setInitialCard(game.drawInitialCard());
            p.setPlayingHand(game.drawResourceCard(), game.drawResourceCard(), game.drawGoldCard());
        }
    }

    //choice of the hidden goal by a player
    public boolean chooseGoal(String nickname, int goalId, GameListener listener){
        synchronized (players) {
            for (Player p : players) {
                if (p.getNickname().equals(nickname)) {
                    p.setHiddenGoal(game.chosenGoalCard(goalId));
                    listener.update(new InitialCardDealtMessage(p.getInitialcard().getView(), p.getInitialcard().getBackView()));
                    return true;
                }
            }
            return false;
        }
    }

    //choice of the side of the initial card by a player
    public boolean chooseInitialCardSide(String nickname, boolean isFront, GameListener listener){
        synchronized (players) {
            for (Player p : players) {
                if (p.getNickname().equals(nickname)) {
                    p.getInitialcard().setFront(isFront);
                    p.play(isFront ? p.getInitialcard() : p.getInitialcard().getBackCard(),40,40);
                    readyPlayers++;
                    if(readyPlayers == playerCount)
                        System.out.println("THE GAME IS STARTING!!!");
                    return true;
                }
            }
            return false;
        }
    }

    public void playCard(int index){
        current.getPlayingHand().remove(index);
        current.draw();
    }
    public void drawCard(PlayableCard card){
        current.getPlayingHand().add(card);
    }
    public void startEndPhase(){
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
    }

    public Player nextPlayer(){
        if(players.indexOf(current) == players.size()-1)
            return players.getFirst();
        return players.get(players.indexOf(current)+1);
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Player getCurrentPlayer() {
        return current;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.current = currentPlayer;
    }
    public void shufflePlayerOrder(){
        Collections.shuffle(players);
    }

    public boolean isCardPlaced() {
        return cardPlaced;
    }

    public void setCardPlaced(boolean cardPlaced) {
        this.cardPlaced = cardPlaced;
    }

    public boolean isCardDrawn() {
        return cardDrawn;
    }

    public void setCardDrawn(boolean cardDraw) {
        this.cardDrawn = cardDraw;
    }

    public boolean isSelectedSide() {
        return selectedSide;
    }

    public void setSelectedSide(boolean selectedSide) {
        this.selectedSide = selectedSide;
    }

    public int getNumOfActivePlayers() {
        return players.size();
    }

    //if the game is started, it sends the list of players in the lobby, otherwise it sends the secret cards
    private void notifyAllListeners(){
        synchronized (players){
            if(started){
                startGame();
                for(GameListener gl : listeners){
                    String nickname = players.get(listeners.indexOf(gl)).getNickname();
                    Message m = new SecretObjectiveDealtMessage(game.drawGoalCards().stream().map(gc -> gc.getView()).toList());
                    if(gl != null) gl.update(m);
                }
            }
            else{
                Message m = new PlayersInLobbyMessage(players.stream().map(p -> p.getNickname()).toList());
                for(GameListener gl : listeners){
                    if(gl != null) gl.update(m);
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

