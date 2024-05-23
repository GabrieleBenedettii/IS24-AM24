package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.am24.constants.Constants.getText;

/**
 * The {@code GoldCard} class represents a special type of playable card in the game.
 * It includes additional properties such as requirements for playing the card,
 * points for covering corners, and a specific symbol associated with these points.
 */
public class GoldCard extends PlayableCard {
    /**
     * The map of requirements for playing this card, specifying the symbol and its required count.
     */
    private final Map<Symbol, Integer> requirements;

    /**
     * Indicates whether the card grants points for covering corners.
     */
    private final boolean pointsForCoveringCorners;

    /**
     * The symbol associated with points granted for covering corners.
     */
    private final Symbol coveringSymbol;

    /**
     * Initializes a {@code GoldCard} with the specified properties.
     *
     * @param imageId The image ID of this card.
     * @param symbols An array of symbols to be assigned to the corners of this card.
     * @param kingdom The kingdom associated with this card.
     * @param points The points value of this card.
     * @param pointsForCoveringCorners {@code true} if the card grants points for covering corners, {@code false} otherwise.
     * @param pointSymbol The symbol associated with points granted for covering corners.
     * @param requirements A map specifying the symbol requirements and their counts.
     */
    public GoldCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points, boolean pointsForCoveringCorners, Symbol pointSymbol, Map<Symbol, Integer> requirements) {
        super(imageId, symbols, kingdom, points);
        this.pointsForCoveringCorners = pointsForCoveringCorners;
        this.coveringSymbol = pointSymbol;
        this.requirements = requirements;
    }

    /**
     * Checks if the requirements for playing this card are met based on the provided visible symbols.
     *
     * @param visibleSymbols A map of visible symbols and their counts.
     * @return {@code true} if all requirements are met, {@code false} otherwise.
     */
    public boolean checkRequirementsMet(HashMap<Symbol, Integer> visibleSymbols) {
        for (Symbol s : requirements.keySet()) {
            if (requirements.get(s) > (visibleSymbols.get(s))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the requirements for playing this card.
     *
     * @return A map of symbol requirements and their counts.
     */
    public Map<Symbol, Integer> getRequirements() {
        return requirements;
    }

    /**
     * Checks if the card grants points for covering corners.
     *
     * @return {@code true} if the card grants points for covering corners, {@code false} otherwise.
     */
    public boolean getPointsForCoveringCorners() {
        return pointsForCoveringCorners;
    }

    /**
     * Retrieves the symbol associated with points granted for covering corners.
     *
     * @return The covering symbol.
     */
    public Symbol getCoveringSymbol() {
        return coveringSymbol;
    }

    /**
     * Returns a string representation of the card's current state.
     *
     * @return A string representation of the card.
     */
    @Override
    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Constants.getText(this.getKingdom()));
        text.append("\n\tCorners: ");
        for (CardCorner c : this.getCorners()) {
            text.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol() != null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        text.append("\n\tRequirements: ");
        for (Symbol s : requirements.keySet()) {
            text.append(Constants.getText(s));
            text.append(" -> ");
            text.append(requirements.get(s));
            text.append(" ");
        }
        text.append("\n\tPoints: ");
        if (pointsForCoveringCorners) {
            text.append(this.getPoints()).append(" points for each covered corner");
        } else if (coveringSymbol != null) {
            text.append(this.getPoints()).append(" points for each ").append(coveringSymbol.toString());
        } else {
            text.append(this.getPoints());
        }
        return text.toString();
    }

    /**
     * Returns the type of the card.
     *
     * @return The string "gold".
     */
    @Override
    public String getType() {
        return "gold";
    }

    /**
     * Creates a view representation of this card.
     *
     * @return A {@code GameCardView} object representing this card.
     */
    @Override
    public GameCardView getView() {
        return new GameCardView("Gold Card", this.getImageId(), this.printCard());
    }
}
