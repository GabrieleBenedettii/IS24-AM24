package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.Kingdom;

/**
 * The {@code ResourceCard} class represents a resource card in the game,
 * extending the {@link PlayableCard} class with additional functionality specific to resource cards.
 */
public class ResourceCard extends PlayableCard {

    /**
     * Initializes a {@code ResourceCard} with the specified image ID, symbols, kingdom, and points.
     *
     * @param imageId  The image ID of this card.
     * @param symbols  An array of symbols to be assigned to the corners of this card.
     * @param kingdom  The kingdom associated with this card.
     * @param points   The points value of this card.
     */
    public ResourceCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols, kingdom, points);
    }

    /**
     * Returns a string representation of the card, including its kingdom, corners, and points.
     *
     * @return A string representation of the card.
     */
    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Constants.getText(this.getKingdom()));
        text.append("\n\tCorners: ");
        for (CardCorner c : this.getCorners()) {
            text.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol() != null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        text.append("\n\tPoints: ");
        text.append(this.getPoints());
        return text.toString();
    }

    /**
     * Returns the type of the card.
     *
     * @return A string representing the type of the card, which is "resource".
     */
    public String getType() {
        return "resource";
    }
}
