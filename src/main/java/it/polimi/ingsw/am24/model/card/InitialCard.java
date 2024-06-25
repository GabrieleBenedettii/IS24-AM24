package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;

/**
 * The {@code InitialCard} class represents a specific type of game card that extends {@code GameCard}.
 * It includes multiple kingdoms and methods to print card details for both front and back views.
 */
public class InitialCard extends GameCard{
    private final ArrayList<Kingdom> kingdoms;
    private InitialCard backCard;

    /**
     * Constructs an {@code InitialCard} with the specified attributes.
     *
     * @param imageId the ID of the image associated with the card
     * @param symbols an array of symbols to be assigned to the card's corners
     * @param kingdoms the list of kingdoms associated with the card
     */
    public InitialCard(int imageId, Symbol[] symbols, ArrayList<Kingdom> kingdoms) {
        super(imageId,symbols,null);
        this.kingdoms = kingdoms;
    }

    /**
     * Retrieves the back card associated with this initial card.
     *
     * @return the back card associated with this initial card
     */
    public InitialCard getBackCard() {
        return backCard;
    }

    /**
     * Retrieves the list of kingdoms associated with this card.
     *
     * @return the list of kingdoms associated with this card
     */
    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    /**
     * Generates a string representation of the card's front side, including kingdoms and corners.
     *
     * @return a string representation of the card's front side
     */
    @Override
    public String printCard() {
        StringBuilder print = new StringBuilder("FRONT\n\tKingdoms: ");
        for (Kingdom k: kingdoms) {
            print.append(" ").append(Constants.getText(k)).append(" ");
        }
        print.append("\n\tCorners: ");
        for (CardCorner c: this.getCorners()) {
            print.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        return print.toString();
    }

    /**
     * Generates a string representation of the card's back side, including kingdoms and corners.
     *
     * @return a string representation of the card's back side
     */
    public String printBackCard() {
        StringBuilder print = new StringBuilder("BACK\n\tKingdoms:");
        for (Kingdom k: backCard.kingdoms) {
            print.append(" ").append(Constants.getText(k)).append(" ");
        }
        print.append("\n\tCorners: ");
        for (CardCorner c: backCard.getCorners()) {
            print.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        return print.toString();
    }

    /**
     * Generates a {@code GameCardView} representing the view of the initial card's front side.
     *
     * @return a {@code GameCardView} object representing the view of the initial card's front side
     */
    public GameCardView getView() {
        return new GameCardView("Initial Card - front", this.getImageId(), this.printCard());
    }

    /**
     * Generates a {@code GameCardView} representing the view of the initial card's back side.
     *
     * @return a {@code GameCardView} object representing the view of the initial card's back side
     */
    public GameCardView getBackView() {
        return new GameCardView("Initial Card - back", this.getImageId(), this.printBackCard());
    }

    /**
     * Retrieves the ID of the initial card.
     *
     * @return the ID of the initial card
     */
    public int getId(){
        return getImageId();
    }
}
