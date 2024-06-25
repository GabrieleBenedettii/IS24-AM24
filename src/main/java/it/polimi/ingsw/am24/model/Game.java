package it.polimi.ingsw.am24.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.am24.exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.exceptions.WrongHiddenGoalException;
import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.deck.*;
import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalDeck;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelview.CommonBoardView;
import it.polimi.ingsw.am24.modelview.GameCardView;


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
        try {
            visibleResCard.add(resourceDeck.drawCard());
            visibleResCard.add(resourceDeck.drawCard());
            visibleGoldCard.add(goldDeck.drawCard());
            visibleGoldCard.add(goldDeck.drawCard());
        } catch(EmptyDeckException e) {
            //ignored, can't happen
        }

        commonGoals.add(goalDeck.drawCard());
        commonGoals.add(goalDeck.drawCard());
    }

    public int resDeckSize() {
        return resourceDeck.deckSize();
    }

    public int goldDeckSize() {
        return goldDeck.deckSize();
    }

    public ResourceCard drawnResCard(int index) {
        ResourceCard drawn = visibleResCard.get(index);
        visibleResCard.remove(index);
        return drawn;
    }

    public GoldCard drawnGoldCard(int index) {
        GoldCard drawn = visibleGoldCard.get(index);
        visibleGoldCard.remove(index);
        return drawn;
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

    public ResourceCard drawResourceCard() throws EmptyDeckException {
        return resourceDeck.drawCard();
    }

    public GoldCard drawGoldCard() throws EmptyDeckException {
        return goldDeck.drawCard();
    }

    public void addResourceCard() throws EmptyDeckException {
        visibleResCard.add(resourceDeck.drawCard());
    }

    public void addGoldCard() throws EmptyDeckException {
        visibleGoldCard.add(goldDeck.drawCard());
    }

    public GoalCard chosenGoalCard(int index) throws WrongHiddenGoalException {
        synchronized (drawnGoalCards) {
            if(!drawnGoalCards.containsKey(index)) throw new WrongHiddenGoalException();
            GoalCard g = drawnGoalCards.get(index);
            drawnGoalCards.remove(g);
            return g;
        }
    }

    public List<String> getAvailableColors() {
        return availableColors.stream().map(c -> c != null ? c.toString() : "").toList();
    }

    public void chooseColor(PlayerColor color) {
        availableColors.remove(color);
    }

    public boolean isAvailable(PlayerColor color) {
        return availableColors.contains(color);
    }

    public GoalCard getCommonGoal(int index) {
        return commonGoals.get(index);
    }

    public List<Integer> getDrawnGoalCardsIds() {
        return drawnGoalCards.keySet().stream().toList();
    }

    public CommonBoardView getCommonBoardView(){
        String topGold = Constants.getText(goldDeck.getFirstCardKingdom());
        String topRes = Constants.getText(resourceDeck.getFirstCardKingdom());
        ArrayList<GameCardView> res = new ArrayList<>(), gold = new ArrayList<>(), goals = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            goals.add(new GameCardView("goal", commonGoals.get(i).getImageId(), commonGoals.get(i).printCard()));
            res.add(new GameCardView(visibleResCard.get(i).getType(), visibleResCard.get(i).getImageId(), visibleResCard.get(i).printCard()));
            gold.add(new GameCardView(visibleGoldCard.get(i).getType(), visibleGoldCard.get(i).getImageId(), visibleGoldCard.get(i).printCard()));
        }
        return new CommonBoardView(goals, res, gold, topRes, topGold);
    }
}
