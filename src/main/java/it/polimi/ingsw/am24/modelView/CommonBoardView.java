package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The CommonBoardView class represents the view of the common board in the game.
 * It includes the goals, resource cards, and gold cards visible to all players,
 * as well as the top cards of the resource and gold decks.
 */
public class CommonBoardView implements Serializable {
    private final ArrayList<GameCardView> goals;
    private final ArrayList<GameCardView> resourceCards;
    private final ArrayList<GameCardView> goldCards;
    private final String resourceDeck;
    private final String goldDeck;

    /**
     * Constructs a CommonBoardView object with the provided information.
     * @param goals The list of goal cards visible on the common board.
     * @param resourceCards The list of resource cards visible on the common board.
     * @param goldCards The list of gold cards visible on the common board.
     * @param resourceDeck The description of the top card of the resource deck.
     * @param goldDeck The description of the top card of the gold deck.
     */
    public CommonBoardView(ArrayList<GameCardView> goals, ArrayList<GameCardView> resourceCards, ArrayList<GameCardView> goldCards, String resourceDeck, String goldDeck) {
        this.goals = goals;
        this.resourceCards = resourceCards;
        this.goldCards = goldCards;
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
    }

    /**
     * Retrieves the list of goal cards visible on the common board.
     * @return The list of goal cards.
     */
    public ArrayList<GameCardView> getGoals() {
        return goals;
    }

    /**
     * Retrieves the list of resource cards visible on the common board.
     * @return The list of resource cards.
     */
    public ArrayList<GameCardView> getResourceCards() {
        return resourceCards;
    }

    /**
     * Retrieves the list of gold cards visible on the common board.
     * @return The list of gold cards.
     */
    public ArrayList<GameCardView> getGoldCards() {
        return goldCards;
    }


    /**
     * Retrieves the description of the top card of the resource deck.
     * @return The description of the resource deck.
     */
    public String getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Retrieves the description of the top card of the gold deck.
     * @return The description of the gold deck.
     */
    public String getGoldDeck() {
        return goldDeck;
    }
}
