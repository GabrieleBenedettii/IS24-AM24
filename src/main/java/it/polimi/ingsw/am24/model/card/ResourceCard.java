package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.Kingdom;

/**
 * The {@code ResourceCard} class represents a specific type of playable card that extends {@code PlayableCard}.
 * It is used to manage resource cards in the game.
 */
public class ResourceCard extends PlayableCard{

    /**
     * Constructs a {@code ResourceCard} with the specified attributes.
     *
     * @param imageId the ID of the image associated with the card
     * @param symbols an array of symbols to be assigned to the card's corners
     * @param kingdom the kingdom associated with the card
     * @param points the points assigned to the card
     */
    public ResourceCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols, kingdom, points);
    }

    /**
     * Generates a textual representation of the card.
     *
     * @return a string representing the card's details, including kingdom, corners, and points
     */
    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Constants.getText(this.getKingdom()));
        text.append("\n\tCorners: ");
        for (CardCorner c: this.getCorners()) {
            text.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        text.append("\n\tPoints: ");
        text.append(this.getPoints());
        return text.toString();
    }

    /**
     * Retrieves the type of the card.
     *
     * @return the type of the card, which is "resource" for resource cards
     */
    public String getType(){
        return "resource";
    }
}
