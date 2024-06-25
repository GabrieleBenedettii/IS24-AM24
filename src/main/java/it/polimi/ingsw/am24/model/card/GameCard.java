package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelview.GameCardView;

import java.util.ArrayList;

/**
 * The {@code GameCard} class represents an abstract game card with corners, an image ID, and a kingdom.
 * It provides methods to manipulate and retrieve information about the card.
 */
public abstract class GameCard {
    private boolean front;
    private final ArrayList<CardCorner> corners;
    private final int imageId;
    private final Kingdom kingdom;

    /**
     * Constructs a {@code GameCard} with the specified image ID, symbols for corners, and kingdom.
     *
     * @param imageId the ID of the image associated with the card
     * @param symbols an array of symbols to be assigned to the card's corners
     * @param kingdom the kingdom associated with the card
     */
    public GameCard(int imageId, Symbol[] symbols, Kingdom kingdom) {
        this.front = false;
        this.corners = new ArrayList<>();
        for (Symbol s: symbols) {
            this.corners.add(new CardCorner(s,false));
        }
        this.imageId = imageId;
        this.kingdom = kingdom;
    }

    /**
     * Sets whether the card is front-facing or not.
     *
     * @param front {@code true} if the card is front-facing, {@code false} otherwise
     */
    public void setFront(boolean front) {
        this.front = front;
    }

    /**
     * Retrieves the list of corners of the card.
     *
     * @return an {@code ArrayList} of {@code CardCorner} objects representing the corners of the card
     */
    public ArrayList<CardCorner> getCorners() {
        return corners;
    }

    /**
     * Retrieves a specific corner of the card based on the index.
     *
     * @param index the index of the corner to retrieve
     * @return the {@code CardCorner} object at the specified index
     */
    public CardCorner getCornerByIndex(int index) {
        return corners.get(index);
    }

    /**
     * Retrieves the image ID associated with the card.
     *
     * @return the image ID of the card
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Retrieves the kingdom associated with the card.
     *
     * @return the {@code Kingdom} of the card
     */
    public Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return an empty string representation (to be overridden by subclasses)
     */
    public String printCard() {
        return "";
    }


    /**
     * Generates a {@code GameCardView} representing the view of the card.
     *
     * @return a {@code GameCardView} object representing the view of the card
     */
    public GameCardView getView() {
        return new GameCardView("GameCard", imageId, printCard());
    }

    /**
     * Generates a {@code GameCardView} representing the view of the card for display in a matrix.
     *
     * @return a {@code GameCardView} object representing the view of the card for matrix display
     */
    public GameCardView getViewForMatrix(){
        return new GameCardView("GameCard", imageId, Constants.getText(getCornerByIndex(0).getCornerText()) + "*" + Constants.getText(getCornerByIndex(1).getCornerText()) +
                Constants.getText(getCornerByIndex(2).getCornerText()) + "*" + Constants.getText(getCornerByIndex(3).getCornerText()));
    }
}

