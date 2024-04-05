package it.polimi.ingsw.am24.model;

import java.util.ArrayList;

import it.polimi.ingsw.am24.model.deck.*;
import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalDeck;
import it.polimi.ingsw.am24.model.goal.GoalCard;


public class Game {
    //DECKS
    private ResourceDeck resourceDeck;
    private GoldDeck goldDeck;
    private InitialDeck initialDeck;
    private GoalDeck goalDeck;

    //CARDS FACE UP ON THE TABLE
    private ArrayList<ResourceCard> visibleResCard;
    private ArrayList<GoldCard> visibleGoldCard;

    //COMMON GOALS
    private ArrayList<GoalCard> commonGoals;

    public Game() {
        this.visibleResCard = new ArrayList<>();
        this.visibleGoldCard = new ArrayList<>();
        this.commonGoals = new ArrayList<>();
        this.start();
    }

    public void start(){
        //decks creation
        resourceDeck = new ResourceDeck();
        goldDeck = new GoldDeck();
        initialDeck = new InitialDeck();
        goalDeck = new GoalDeck();

        //decks initial shuffle
        resourceDeck.shuffle();
        goldDeck.shuffle();
        initialDeck.shuffle();
        goalDeck.shuffle();

        //
        visibleResCard.add(resourceDeck.drawCard());
        visibleResCard.add(resourceDeck.drawCard());
        visibleGoldCard.add(goldDeck.drawCard());
        visibleGoldCard.add(goldDeck.drawCard());
        commonGoals.add(goalDeck.drawCard());
        commonGoals.add(goalDeck.drawCard());
    }

    public ArrayList<ResourceCard> getVisibleResCard() {
        return visibleResCard;
    }

    public ArrayList<GoldCard> getVisibleGoldCard() {
        return visibleGoldCard;
    }

    public ArrayList<GoalCard> getCommonGoals() {
        return commonGoals;
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
    public GoalDeck getGoalDeck() {
        return goalDeck;
    }
}
