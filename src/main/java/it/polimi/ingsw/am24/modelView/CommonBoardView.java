package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;
import java.util.ArrayList;

public class CommonBoardView implements Serializable {
    private final ArrayList<GameCardView> goals;
    private final ArrayList<GameCardView> resourceCards;
    private final ArrayList<GameCardView> goldCards;
    private final String resourceDeck;
    private final String goldDeck;

    public CommonBoardView(ArrayList<GameCardView> goals, ArrayList<GameCardView> resourceCards, ArrayList<GameCardView> goldCards, String resourceDeck, String goldDeck) {
        this.goals = goals;
        this.resourceCards = resourceCards;
        this.goldCards = goldCards;
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
    }

    public ArrayList<GameCardView> getGoals() {
        return goals;
    }

    public ArrayList<GameCardView> getResourceCards() {
        return resourceCards;
    }

    public ArrayList<GameCardView> getGoldCards() {
        return goldCards;
    }

    public String getResourceDeck() {
        return resourceDeck;
    }

    public String getGoldDeck() {
        return goldDeck;
    }
}
