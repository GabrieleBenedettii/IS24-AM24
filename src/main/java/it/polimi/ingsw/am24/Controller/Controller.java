package it.polimi.ingsw.am24.Controller;

import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.InitialCard;
import it.polimi.ingsw.am24.model.card.PlayableCard;
import it.polimi.ingsw.am24.model.goal.GoalCard;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    private Game game;
    private ArrayList<Player> players;
    private Player current;
    private int playerCount;
    private boolean cardPlaced, cardDrawn, selectedSide;

    public void chooseGoal(GoalCard card){
        current.setHiddenGoal(card);
    }
    public void placeInitialCard(boolean front){
        GameCard[][] board = current.getGameboard();
        if(front){
            board[40][40] = current.getInitialcard();
        }
        else if(!front){
            board[40][40] = current.getInitialcard().getBackCard();
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
    public ArrayList<GoalCard> addFirstPlayer(Player player, int numPlayers){
        players.add(player);
        this.playerCount = numPlayers;
        ArrayList<GoalCard> objs = new ArrayList<>();
        objs.add(game.getGoalDeck().drawCard());
        objs.add(game.getGoalDeck().drawCard());
        player.setInitialCard(game.getInitialDeck().drawCard());
        return objs;
    }
    public ArrayList<GoalCard> addNewPlayer(Player player){
        if(players.size() < playerCount){
            players.add(player);ArrayList<GoalCard> objs = new ArrayList<>();
            objs.add(game.getGoalDeck().drawCard());
            objs.add(game.getGoalDeck().drawCard());
            player.setInitialCard(game.getInitialDeck().drawCard());
            return objs;
        }
        else{
            System.out.println("Error, max number of players already reached");
            return null;
        }
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
}

