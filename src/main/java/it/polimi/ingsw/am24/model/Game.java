package it.polimi.ingsw.am24.model;

import java.util.ArrayList;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.deck.*;
import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalDeck;
import it.polimi.ingsw.am24.model.goal.GoalCard;


public class Game {
    private ArrayList<Player> players;
    private int numPlayers;
    private ResourceDeck resourceDeck;
    private GoldDeck goldDeck;
    private InitialDeck initialDeck;
    private GoalDeck goalDeck;
    private Player currentPlayer;
    private ArrayList<ResourceCard> visibleResCard;
    private ArrayList<GoldCard> visibleGoldCard;
    private ArrayList<GoalCard> commonGoal;

    public Game() {
        this.players = new ArrayList<>();
        this.visibleResCard = new ArrayList<>();
        this.visibleGoldCard = new ArrayList<>();
        this.commonGoal = new ArrayList<>();
    }

    public void start(){
        //devo fare lo shuffle dei mazzi e settare le carte visibili
        resourceDeck = new ResourceDeck();
        goldDeck = new GoldDeck();
        initialDeck = new InitialDeck();
        goalDeck = new GoalDeck();

        resourceDeck.shuffle(); //shuffle carte risorse
        goldDeck.shuffle(); //shuffle carte oro
        initialDeck.shuffle(); //shuffle carte iniziali
        goalDeck.shuffle(); //shuffle carte obiettivo

        visibleResCard.add(resourceDeck.drawCard()); //preparo le carte scoperte
        visibleResCard.add(resourceDeck.drawCard());
        visibleGoldCard.add(goldDeck.drawCard());
        visibleGoldCard.add(goldDeck.drawCard());
        commonGoal.add(goalDeck.drawCard());
        commonGoal.add(goalDeck.drawCard());

        for(Player player : players){ //distribuisco le carte iniziali, gli obiettivi segreti e la mano iniziale del giocatore
            player.setInitialCard(initialDeck.drawCard());
            player.setGoals(goalDeck.drawCard(), goalDeck.drawCard());
            player.setPlayingHand(resourceDeck.drawCard(),resourceDeck.drawCard(),goldDeck.drawCard());
        }
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public GoalDeck getGoalDeck() {
        return goalDeck;
    }

    public ArrayList<ResourceCard> getVisibleResCard() {
        return visibleResCard;
    }

    public ArrayList<GoldCard> getVisibleGoldCard() {
        return visibleGoldCard;
    }

    public ArrayList<GoalCard> getCommonGoal() {
        return commonGoal;
    }

    public ResourceDeck getResourceDeck() {
        return resourceDeck;
    }

    public GoldDeck getGoldDeck() {
        return goldDeck;
    }

    public InitialDeck getInitialDeck() {
        return initialDeck;
    }

    public void addFirstPlayer(Player player, int numPlayers){
        players.add(player);
        this.numPlayers = numPlayers;
    }
    public void addNewPlayer(Player player)/* throws PlayerNumberException*/ {
        if(players.size() < Costants.MAX_PLAYERS){
            players.add(player);
        }
        else{
            //throw new PlayerNumberException();
        }
    }
    public Player nextPlayer(){
        if(players.indexOf(currentPlayer) == players.size()-1)
            return players.getFirst();
        return players.get(players.indexOf(currentPlayer)+1);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /*public Deck CreateDecks(DeckType type){
        Deck deck = new Deck(type);
        return deck;
    }*/

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
