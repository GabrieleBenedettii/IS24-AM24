package it.polimi.ingsw.am24.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private ArrayList<PlayerColor> availableColors;
    private HashMap<Integer,GoalCard> drawnGoalCards;   //used to save the goal card's objects while players are choosing

    public Game() {
        this.visibleResCard = new ArrayList<>();
        this.visibleGoldCard = new ArrayList<>();
        this.commonGoals = new ArrayList<>();
        this.availableColors = new ArrayList<>();
        this.drawnGoalCards = new HashMap<>();
        availableColors.add(PlayerColor.GREEN);
        availableColors.add(PlayerColor.YELLOW);
        availableColors.add(PlayerColor.RED);
        availableColors.add(PlayerColor.BLUE);
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

        //set the table
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

    public ArrayList<GoalCard> drawGoalCards() {
        GoalCard goal1 = goalDeck.drawCard();
        GoalCard goal2 = goalDeck.drawCard();
        drawnGoalCards.put(goal1.getImageId(),goal1);
        drawnGoalCards.put(goal2.getImageId(),goal2);
        ArrayList<GoalCard> goals = new ArrayList<>();
        goals.add(goal1);
        goals.add(goal2);
        return goals;
    }

    public InitialCard drawInitialCard() {
        return initialDeck.drawCard();
    }

    public ResourceCard drawResourceCard() {
        return resourceDeck.drawCard();
    }

    public GoldCard drawGoldCard() {
        return goldDeck.drawCard();
    }

    public GoalCard chosenGoalCard(int index) {
        synchronized (drawnGoalCards) {
           GoalCard g = drawnGoalCards.get(index);
           drawnGoalCards.remove(g);
           return g;
        }
    }

    public List<String> getAvailableColors() {
        return availableColors.stream().map(c -> c.toString()).toList();
    }

    public void chooseColor(PlayerColor color) {
        availableColors.remove(color);
    }

    public boolean isAvailable(PlayerColor color) {
        return availableColors.contains(color);
    }
}
