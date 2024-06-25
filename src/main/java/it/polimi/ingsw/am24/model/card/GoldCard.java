package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelview.GameCardView;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.am24.constants.Constants.getText;

/**
 * The {@code GoldCard} class represents a specific type of playable card in the game,
 * extending {@code PlayableCard}. It includes requirements, points mechanics,
 * and methods to verify requirements and print card details.
 */
public class GoldCard extends PlayableCard {
    private final Map<Symbol,Integer> requirements;
    private final boolean pointsForCoveringCorners;
    private final Symbol coveringSymbol;

    /**
     * Constructs a {@code GoldCard} with the specified attributes.
     *
     * @param imageId the ID of the image associated with the card
     * @param symbols an array of symbols to be assigned to the card's corners
     * @param kingdom the kingdom associated with the card
     * @param points the points value of the card
     * @param pointsForCoveringCorners {@code true} if the card awards points for covering corners, {@code false} otherwise
     * @param pointSymbol the symbol for which the card awards points, if any
     * @param requirements the map of symbol requirements for the card
     */
    public GoldCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points, boolean pointsForCoveringCorners, Symbol pointSymbol, Map<Symbol,Integer> requirements) {
        super(imageId, symbols, kingdom, points);
        this.pointsForCoveringCorners = pointsForCoveringCorners;
        this.coveringSymbol = pointSymbol;
        this.requirements = requirements;

    }

    /**
     * Checks if the requirements of the card are met based on the visible symbols.
     *
     * @param visibleSymbols the map of visible symbols and their counts
     * @return {@code true} if all requirements are met, {@code false} otherwise
     */
    public boolean checkRequirementsMet(HashMap<Symbol, Integer> visibleSymbols){
        for(Symbol s : requirements.keySet()){
            if(requirements.get(s) > (visibleSymbols.get(s))){
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the requirements map of the card.
     *
     * @return the map of symbol requirements
     */
    public Map<Symbol, Integer> getRequirements() {
        return requirements;
    }

    /**
     * Checks if the card awards points for covering corners.
     *
     * @return {@code true} if points are awarded for covering corners, {@code false} otherwise
     */
    public boolean getPointsForCoveringCorners() {
        return pointsForCoveringCorners;
    }

    /**
     * Retrieves the symbol for which the card awards points.
     *
     * @return the symbol for which points are awarded, or {@code null} if none
     */
    public Symbol getCoveringSymbol() {
        return coveringSymbol;
    }

    /**
     * Generates a string representation of the card, including its kingdom, corners, requirements, and points.
     *
     * @return a string representation of the card
     */
    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Constants.getText(this.getKingdom()));
        text.append("\n\tCorners: ");
        for (CardCorner c: this.getCorners()) {
            text.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        text.append("\n\tRequirements: ");
        for (Symbol s: requirements.keySet()) {
            text.append(Constants.getText(s));
            text.append(" -> ");
            text.append(requirements.get(s));
            text.append(" ");
        }
        text.append("\n\tPoints: ");
        if(pointsForCoveringCorners)
            text.append(this.getPoints() + " points for each covered corner");
        else if(coveringSymbol != null)
            text.append(this.getPoints() + " points for each " + coveringSymbol.toString());
        else
            text.append(this.getPoints());
        return text.toString();
    }

    /**
     * Retrieves the type of the card.
     *
     * @return the type of the card, which is "gold"
     */
    public String getType(){
        return "gold";
    }

    /**
     * Generates a {@code GameCardView} representing the view of the gold card.
     *
     * @return a {@code GameCardView} object representing the view of the gold card
     */
    public GameCardView getView() {
        return new GameCardView("Gold Card", this.getImageId(), this.printCard());
    }
}
