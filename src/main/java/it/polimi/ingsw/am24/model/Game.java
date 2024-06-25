package it.polimi.ingsw.am24.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.am24.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.Exceptions.WrongHiddenGoalException;
import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.deck.*;
import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalDeck;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.CommonBoardView;
import it.polimi.ingsw.am24.modelView.GameCardView;


/**
 * The {@code Game} class represents the overall game state, including decks of cards,
 * face-up cards on the table, common goals, available colors, and drawn goal cards.
 */
public class Game {
    /** The different decks of cards. */
    private ResourceDeck resourceDeck;
    private GoldDeck goldDeck;
    private InitialDeck initialDeck;
    private GoalDeck goalDeck;

    /** Lists of face-up cards on the table. */
    private final ArrayList<ResourceCard> visibleResCard;
    private final ArrayList<GoldCard> visibleGoldCard;

    /** List of common goal cards. */
    private final ArrayList<GoalCard> commonGoals;

    /** List of available player colors. */
    private final ArrayList<PlayerColor> availableColors;

    /** Map of drawn goal cards by their image ID. */
    private final HashMap<Integer,GoalCard> drawnGoalCards;   //used to save the goal card's objects while players are choosing

    /**
     * Constructs a new game instance.
     * Initializes the face-up cards, common goals, available colors, and drawn goal cards.
     */
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

    /**
     * Starts the game by creating and shuffling the decks, setting up the table with initial cards,
     * and drawing the initial common goals.
     */
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

    /**
     * Gets the size of the resource deck.
     *
     * @return the number of cards in the resource deck
     */
    public int resDeckSize() {
        return resourceDeck.deckSize();
    }

    /**
     * Gets the size of the gold deck.
     *
     * @return the number of cards in the gold deck
     */
    public int goldDeckSize() {
        return goldDeck.deckSize();
    }

    /**
     * Draws a resource card from the visible resource cards.
     *
     * @param index the index of the card to draw
     * @return the drawn resource card
     */
    public ResourceCard drawnResCard(int index) {
        ResourceCard drawn = visibleResCard.get(index);
        visibleResCard.remove(index);
        return drawn;
    }

    /**
     * Draws a gold card from the visible gold cards.
     *
     * @param index the index of the card to draw
     * @return the drawn gold card
     */
    public GoldCard drawnGoldCard(int index) {
        GoldCard drawn = visibleGoldCard.get(index);
        visibleGoldCard.remove(index);
        return drawn;
    }

    /**
     * Draws two goal cards from the goal deck and returns them.
     *
     * @return a list containing the two drawn goal cards
     */
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

    /**
     * Draws an initial card from the initial deck.
     *
     * @return the drawn initial card
     */
    public InitialCard drawInitialCard() {
        return initialDeck.drawCard();
    }

    /**
     * Draws a resource card from the resource deck.
     *
     * @return the drawn resource card
     * @throws EmptyDeckException if the resource deck is empty
     */
    public ResourceCard drawResourceCard() throws EmptyDeckException {
        return resourceDeck.drawCard();
    }

    /**
     * Draws a gold card from the gold deck.
     *
     * @return the drawn gold card
     * @throws EmptyDeckException if the gold deck is empty
     */
    public GoldCard drawGoldCard() throws EmptyDeckException {
        return goldDeck.drawCard();
    }

    /**
     * Adds a resource card to the visible resource cards from the resource deck.
     *
     * @throws EmptyDeckException if the resource deck is empty
     */
    public void addResourceCard() throws EmptyDeckException {
        visibleResCard.add(resourceDeck.drawCard());
    }

    /**
     * Adds a gold card to the visible gold cards from the gold deck.
     *
     * @throws EmptyDeckException if the gold deck is empty
     */
    public void addGoldCard() throws EmptyDeckException {
        visibleGoldCard.add(goldDeck.drawCard());
    }

    /**
     * Chooses a goal card by its index from the drawn goal cards.
     *
     * @param index the index of the goal card to choose
     * @return the chosen goal card
     * @throws WrongHiddenGoalException if the goal card is not found in the drawn goal cards
     */
    public GoalCard chosenGoalCard(int index) throws WrongHiddenGoalException {
        synchronized (drawnGoalCards) {
            if(!drawnGoalCards.containsKey(index)) throw new WrongHiddenGoalException();
            GoalCard g = drawnGoalCards.get(index);
            drawnGoalCards.remove(g);
            return g;
        }
    }

    /**
     * Gets the list of available player colors as strings.
     *
     * @return a list of available player colors
     */
    public List<String> getAvailableColors() {
        return availableColors.stream().map(c -> c != null ? c.toString() : "").toList();
    }

    /**
     * Removes the chosen color from the available colors.
     *
     * @param color the chosen player color
     */
    public void chooseColor(PlayerColor color) {
        availableColors.remove(color);
    }

    /**
     * Checks if a player color is available.
     *
     * @param color the player color to check
     * @return {@code true} if the color is available, {@code false} otherwise
     */
    public boolean isAvailable(PlayerColor color) {
        return availableColors.contains(color);
    }

    /**
     * Gets a common goal card by its index.
     *
     * @param index the index of the common goal card
     * @return the common goal card
     */
    public GoalCard getCommonGoal(int index) {
        return commonGoals.get(index);
    }

    /**
     * Gets the list of drawn goal cards' IDs.
     *
     * @return a list of drawn goal cards' IDs
     */
    public List<Integer> getDrawnGoalCardsIds() {
        return drawnGoalCards.keySet().stream().toList();
    }

    /**
     * Gets the common board view, including top cards of resource and gold decks,
     * visible resource and gold cards, and common goals.
     *
     * @return the common board view
     */
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
