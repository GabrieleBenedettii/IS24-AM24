package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;

public class DecksView implements Serializable {
    private final String resourceDeck;
    private final String goldDeck;

    public DecksView(String topGold, String topRes) {
        this.goldDeck = topGold;
        this.resourceDeck = topRes;
    }
    public String getResourceDeck() {
        return resourceDeck;
    }
    public String getGoldDeck() {
        return goldDeck;
    }
}
