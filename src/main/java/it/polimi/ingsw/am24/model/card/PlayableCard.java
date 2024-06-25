package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

/**
 * The {@code PlayableCard} class represents a specific type of game card that extends {@code GameCard}.
 * It includes points and methods related to the card's gameplay.
 */
public abstract class PlayableCard extends GameCard{
    private final int points;

    /**
     * Constructs a {@code PlayableCard} with the specified attributes.
     *
     * @param imageId the ID of the image associated with the card
     * @param symbols an array of symbols to be assigned to the card's corners
     * @param kingdom the kingdom associated with the card
     * @param points the points assigned to the card
     */
    public PlayableCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols, kingdom);
        this.points = points;
    }


    /**
     * Retrieves the points assigned to the card.
     *
     * @return the points assigned to the card
     */
    public int getPoints() {
        return points;
    }

    /**
     * Retrieves the type of the card.
     *
     * @return an empty string (placeholder method to be overridden)
     */
    public String getType(){
        return "";
    }

    /**
     * Retrieves the side of the card based on whether it is front or back.
     *
     * @param front {@code true} if retrieving the front side, {@code false} for the back side
     * @return the card side (front or back)
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
     * Checks if the requirements for playing the card are met based on visible symbols.
     *
     * @param visibleSymbols the map of visible symbols to check against card requirements
     * @return {@code true} if requirements are met, {@code false} otherwise
     */
    public boolean checkRequirementsMet(HashMap<Symbol, Integer> visibleSymbols){
        return true;
    }
}
