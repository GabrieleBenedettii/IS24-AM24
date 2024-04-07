package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;

public class GameCardView implements Serializable {
    private final String cardType;
    private final String cardDescription;
    private final int cardId;

    public GameCardView(String type, int id, String desc){
        this.cardId = id;
        this.cardDescription = desc;
        this.cardType = type;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public int getCardId() {
        return cardId;
    }
}
