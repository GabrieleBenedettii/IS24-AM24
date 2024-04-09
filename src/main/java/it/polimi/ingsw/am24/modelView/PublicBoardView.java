package it.polimi.ingsw.am24.modelView;

import it.polimi.ingsw.am24.model.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class PublicBoardView implements Serializable {
    private final ArrayList<GameCardView> goals;
    private final ArrayList<GameCardView> resourceCards;
    private final ArrayList<GameCardView> goldCards;
    private final String resourceDeck;
    private final String goldDeck;
    public PublicBoardView(ArrayList<GameCardView> goals, ArrayList<GameCardView> gold, ArrayList<GameCardView> res, String topGold, String topRes){
        this.goals = goals;
        this.resourceCards = res;
        this.goldCards = gold;
        this.goldDeck = topGold;
        this.resourceDeck = topRes;
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
