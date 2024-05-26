package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;

/**
 * The {@code InitialCard} class represents the initial card in the game,
 * encapsulating its state, associated kingdoms, and its front and back representations.
 */
public class InitialCard extends GameCard {
    /**
     * The list of kingdoms associated with this card.
     */
    private final ArrayList<Kingdom> kingdoms;

    /**
     * The card representing the back side of this initial card.
     */
    private InitialCard backCard;

    /**
     * Initializes an {@code InitialCard} with the specified image ID, symbols, and kingdoms.
     *
     * @param imageId  The image ID of this card.
     * @param symbols  An array of symbols to be assigned to the corners of this card.
     * @param kingdoms The list of kingdoms associated with this card.
     */
    public InitialCard(int imageId, Symbol[] symbols, ArrayList<Kingdom> kingdoms) {
        super(imageId, symbols, null);
        this.kingdoms = kingdoms;
    }

    /**
     * Retrieves the card representing the back side of this initial card.
     *
     * @return The back side card.
     */
    public InitialCard getBackCard() {
        return backCard;
    }

    /**
     * Retrieves the list of kingdoms associated with this card.
     *
     * @return The list of kingdoms.
     */
    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    /**
     * Returns a string representation of the front side of the card.
     *
     * @return A string representation of the front side of the card.
     */
    @Override
    public String printCard() {
        StringBuilder print = new StringBuilder("FRONT\n\tKingdoms: ");
        for (Kingdom k : kingdoms) {
            print.append(" ").append(Constants.getText(k)).append(" ");
        }
        print.append("\n\tCorners: ");
        for (CardCorner c : this.getCorners()) {
            print.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol() != null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        return print.toString();
    }

    /**
     * Returns a string representation of the back side of the card.
     *
     * @return A string representation of the back side of the card.
     */
    public String printBackCard() {
        StringBuilder print = new StringBuilder("BACK\n\tKingdoms:");
        for (Kingdom k : backCard.kingdoms) {
            print.append(" ").append(Constants.getText(k)).append(" ");
        }
        print.append("\n\tCorners: ");
        for (CardCorner c : backCard.getCorners()) {
            print.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol() != null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        return print.toString();
    }

    /**
     * Creates a view representation of the front side of this card.
     *
     * @return A {@code GameCardView} object representing the front side of this card.
     */
    @Override
    public GameCardView getView() {
        return new GameCardView("Initial Card - front", this.getImageId(), this.printCard());
    }

    /**
     * Creates a view representation of the back side of this card.
     *
     * @return A {@code GameCardView} object representing the back side of this card.
     */
    public GameCardView getBackView() {
        return new GameCardView("Initial Card - back", this.getImageId(), this.printBackCard());
    }

    /**
     * Retrieves the image ID of this card.
     *
     * @return The image ID.
     */
    public int getId() {
        return getImageId();
    }
}
