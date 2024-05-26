package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Symbol;

/**
 * The {@code CardCorner} class represents a corner of a card in the game,
 * encapsulating its symbol, visibility, and coverage state. It provides
 * methods to interact with and manipulate these properties.
 */
public class CardCorner {
    /**
     * The symbol associated with this corner of the card.
     */
    private Symbol symbol;

    /**
     * Indicates whether the symbol on this corner is hidden.
     */
    private boolean isHidden;

    /**
     * Indicates whether this corner is covered.
     */
    private boolean isCovered;

    /**
     * Initializes a {@code CardCorner} with the specified symbol and visibility state.
     * The corner is not covered by default.
     *
     * @param symbol The symbol to be associated with this corner.
     * @param hidden Initial visibility state of the corner.
     */
    public CardCorner(Symbol symbol, boolean hidden) {
        this.symbol = symbol;
        this.isHidden = hidden;
        this.isCovered = false;
    }

    /**
     * Retrieves the symbol associated with this corner.
     *
     * @return The symbol of this corner.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Sets the corner's {@code isCovered} state to {@code true}, indicating that
     * the corner is covered.
     */
    public void coverCorner() {
        this.isCovered = true;
    }

    /**
     * Checks whether the corner is hidden.
     *
     * @return {@code true} if the corner is hidden, {@code false} otherwise.
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * Checks whether the corner is covered.
     *
     * @return {@code true} if the corner is covered, {@code false} otherwise.
     */
    public boolean isCovered() {
        return isCovered;
    }

    /**
     * Resets the corner to an empty state by setting the symbol to {@code null},
     * and both {@code isHidden} and {@code isCovered} to {@code false}.
     */
    public void setEmpty() {
        this.isCovered = false;
        this.isHidden = false;
        this.symbol = null;
    }

    /**
     * Retrieves a character representing the state of the corner.
     * The character returned is based on the following conditions:
     * <ul>
     *   <li>'-' if the corner is not covered and either hidden or has no symbol.</li>
     *   <li>' ' (space) if the corner is covered.</li>
     *   <li>'K' if the symbol is {@code Symbol.INK} and not hidden.</li>
     *   <li>The first character of the symbol's string representation if not hidden.</li>
     *   <li>'E' if the symbol is {@code null} and the corner is not hidden.</li>
     *   <li>'H' if the corner is hidden.</li>
     * </ul>
     *
     * @return A character representing the state of the corner.
     */
    public char getCornerText() {
        char text = '-';
        if (isCovered) {
            text = ' ';
        } else if (symbol != null && !isHidden) {
            text = symbol == Symbol.INK ? 'K' : symbol.toString().charAt(0);
        } else if (symbol == null && !isHidden) {
            text = 'E';
        } else if (isHidden) {
            text = 'H';
        }
        return text;
    }
}
