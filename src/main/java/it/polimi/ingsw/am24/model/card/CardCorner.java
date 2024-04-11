package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Symbol;

public class CardCorner {
    private Symbol symbol;
    private boolean isHidden;
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

    public void setEmpty() {
        this.isCovered = false;
        this.isHidden = false;
        this.symbol = null;
    }

    public char getCornerText() {
        char text = '-';
        if(isCovered) {
            text = ' ';
        } else if(symbol != null && !isHidden) {
            text = symbol.toString().charAt(0);
        } else if (symbol == null && !isHidden) {
            text = 'E';
        } else if (isHidden){
            text = 'H';
        }
        return text;
    }
}
