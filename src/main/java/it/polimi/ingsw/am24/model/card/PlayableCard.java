package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

/**
 * The {@code PlayableCard} class represents a card that can be played in the game.
 * It includes properties such as points and methods for checking requirements and handling card sides.
 */
public abstract class PlayableCard extends GameCard {
    /**
     * The points associated with this playable card.
     */
    private final int points;

    /**
     * Initializes a {@code PlayableCard} with the specified image ID, symbols, kingdom, and points.
     *
     * @param imageId  The image ID of this card.
     * @param symbols  An array of symbols to be assigned to the corners of this card.
     * @param kingdom  The kingdom associated with this card.
     * @param points   The points value of this card.
     */
    public PlayableCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols, kingdom);
        this.points = points;
    }

    /**
     * Retrieves the points associated with this playable card.
     *
     * @return The points value.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the type of the card.
     *
     * @return An empty string representing the type.
     */
    public String getType() {
        return "";
    }

    /**
     * Sets the card to either its front or back side.
     * If the back side is selected, the corners are emptied.
     *
     * @param front {@code true} to set the front side, {@code false} to set the back side.
     * @return The playable card with the specified side set.
     */
    public PlayableCard getCardSide(boolean front) {
        if (!front) {
            for (int i = 0; i < 4; i++) {
                this.getCornerByIndex(i).setEmpty();
            }
        }
        return this;
    }

    /**
     * Checks if the requirements for playing this card are met.
     * This implementation always returns {@code true} and should be overridden by subclasses.
     *
     * @param visibleSymbols A map of visible symbols and their counts.
     * @return {@code true} if requirements are met, {@code false} otherwise.
     */
    public boolean checkRequirementsMet(HashMap<Symbol, Integer> visibleSymbols) {
        return true;
    }
}
