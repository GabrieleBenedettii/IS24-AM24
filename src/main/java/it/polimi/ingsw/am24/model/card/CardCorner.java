package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Symbol;

/**
 * The {@code CardCorner} class represents a corner of a card, which can hold a symbol and be hidden or covered.
 */
public class CardCorner {
    private Symbol symbol;
    private boolean isHidden;
    private boolean isCovered;

    /**
     * Constructs a {@code CardCorner} with the specified symbol and hidden state.
     *
     * @param symbol the symbol to be assigned to the card corner
     * @param hidden the hidden state of the card corner
     */
    public CardCorner(Symbol symbol, boolean hidden) {
        this.symbol = symbol;
        this.isHidden = hidden;
        this.isCovered = false;
    }

    /**
     * Gets the symbol assigned to this card corner.
     *
     * @return the {@code Symbol} of this card corner
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Covers the corner, setting its covered state to true.
     */
    public void coverCorner() {
        this.isCovered = true;
    }

    /**
     * Checks if the corner is hidden.
     *
     * @return {@code true} if the corner is hidden, {@code false} otherwise
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * Checks if the corner is covered.
     *
     * @return {@code true} if the corner is covered, {@code false} otherwise
     */
    public boolean isCovered() {
        return isCovered;
    }

    /**
     * Sets the corner to be empty by removing its symbol, and setting its hidden and covered states to false.
     */
    public void setEmpty() {
        this.isCovered = false;
        this.isHidden = false;
        this.symbol = null;
    }

    /**
     * Gets the text representation of the corner for display purposes.
     *
     * @return a {@code char} representing the state of the corner:
     *         ' ' if covered, 'K' if the symbol is INK and not hidden,
     *         the first character of the symbol's string if not hidden,
     *         'E' if empty and not hidden, 'H' if hidden, '-' otherwise
     */
    public char getCornerText() {
        char text = '-';
        if(isCovered) {
            text = ' ';
        } else if(symbol != null && !isHidden) {
            text = symbol == Symbol.INK ? 'K' : symbol.toString().charAt(0);
        } else if (symbol == null && !isHidden) {
            text = 'E';
        } else if (isHidden){
            text = 'H';
        }
        return text;
    }
}
