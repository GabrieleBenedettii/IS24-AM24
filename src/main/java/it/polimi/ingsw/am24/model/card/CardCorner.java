package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Symbol;

public class CardCorner {
    private final Symbol symbol;
    private final boolean isHidden;
    private boolean isCovered;

    public CardCorner(Symbol symbol, boolean hidden) {
        this.symbol = symbol;
        this.isHidden = hidden;
        this.isCovered = false;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void coverCorner() {
        this.isCovered = true;
    }
    public boolean isHidden() {
        return isHidden;
    }
    public boolean isCovered() {
        return isCovered;
    }
    public char getCornerText() {
        char text;
        if(symbol != null && !isHidden) {
            text = symbol.toString().charAt(0);
        } else if (isHidden) {
            text = ' ';
        } else {
            text = '*';
        }
        return text;
    }
}
