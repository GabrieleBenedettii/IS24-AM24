package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;

/**
 * The {@code GameCard} class represents a card in the game, encapsulating its
 * state, corners, image ID, and associated kingdom. This class provides methods
 * to interact with and manipulate these properties.
 */
public abstract class GameCard {
    /**
     * Indicates whether the card is showing its front.
     */
    private boolean front;

    /**
     * The list of corners associated with this card.
     */
    private final ArrayList<CardCorner> corners;

    /**
     * The image ID of this card.
     */
    private final int imageId;

    /**
     * The kingdom associated with this card.
     */
    private final Kingdom kingdom;

    /**
     * Initializes a {@code GameCard} with the specified image ID, symbols, and kingdom.
     * The card is not showing its front by default.
     *
     * @param imageId The image ID of this card.
     * @param symbols An array of symbols to be assigned to the corners of this card.
     * @param kingdom The kingdom associated with this card.
     */
    public GameCard(int imageId, Symbol[] symbols, Kingdom kingdom) {
        this.front = false;
        this.corners = new ArrayList<>();
        for (Symbol s : symbols) {
            this.corners.add(new CardCorner(s, false));
        }
        this.imageId = imageId;
        this.kingdom = kingdom;
    }

    /**
     * Sets the card's {@code front} state to the specified value.
     *
     * @param front {@code true} if the card is showing its front, {@code false} otherwise.
     */
    public void setFront(boolean front) {
        this.front = front;
    }

    /**
     * Retrieves the list of corners associated with this card.
     *
     * @return The list of corners.
     */
    public ArrayList<CardCorner> getCorners() {
        return corners;
    }

    /**
     * Retrieves a specific corner of the card by its index.
     *
     * @param index The index of the corner to retrieve.
     * @return The corner at the specified index.
     */
    public CardCorner getCornerByIndex(int index) {
        return corners.get(index);
    }

    /**
     * Retrieves the image ID of this card.
     *
     * @return The image ID.
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Retrieves the kingdom associated with this card.
     *
     * @return The kingdom.
     */
    public Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * Returns a string representation of the card's current state.
     *
     * @return A string representation of the card.
     */
    public String printCard() {
        return "";
    }

    /**
     * Creates a view representation of this card.
     *
     * @return A {@code GameCardView} object representing this card.
     */
    public GameCardView getView() {
        return new GameCardView("GameCard", imageId, printCard());
    }

    /**
     * Retrieves a formatted string representing the state of the card's corners.
     *
     * @return A formatted string representing the card's corners.
     */
    public String getStringForCard() {
        return Constants.getText(getCornerByIndex(0).getCornerText()) + "*" +
                Constants.getText(getCornerByIndex(1).getCornerText()) +
                Constants.getText(getCornerByIndex(2).getCornerText()) + "*" +
                Constants.getText(getCornerByIndex(3).getCornerText());
    }
}