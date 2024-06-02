package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;

/**
 * The GameCardView class represents a view of a game card, containing information about its type, description, and ID.
 */
public class GameCardView implements Serializable {
    private final String cardType;
    private final String cardDescription;
    private final int cardId;

    /**
     * Constructs a GameCardView object with the specified type, ID, and description.
     * @param type The type of the card.
     * @param id The ID of the card.
     * @param desc The description of the card.
     */
    public GameCardView(String type, int id, String desc){
        this.cardId = id;
        this.cardDescription = desc;
        this.cardType = type;
    }

    /**
     * Get the type of the card.
     * @return The type of the card.
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Get the description of the card.
     * @return The description of the card.
     */
    public String getCardDescription() {
        return cardDescription;
    }

    /**
     * Get the ID of the card.
     * @return The ID of the card.
     */
    public int getCardId() {
        return cardId;
    }
}
