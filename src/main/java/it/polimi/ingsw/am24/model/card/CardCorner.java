package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Symbol;

public class CardCorner {
    private GameCard coveredCard;
    private GameCard coveringCard;
    private final Symbol symbol;
    private final boolean hidden;

    public CardCorner(/*Position position, */Symbol symbol, boolean hidden) {
        //this.position = position;
        this.coveredCard = null;
        this.coveringCard = null;
        this.symbol = symbol;
        this.hidden = hidden;
    }

    public GameCard getCoveredCard() {
        return coveredCard;
    }

    public GameCard getCoveringCard() {
        return coveringCard;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setCoveredCard(GameCard coveredCard) {
        this.coveredCard = coveredCard;
    }

    public void setCoveringCard(GameCard coveringCard) {
        this.coveringCard = coveringCard;
    }
    public boolean isHidden() {
        return hidden;
    }
    public char getCornerText() {
        char text;
        if(symbol != null && !hidden) {
            text = symbol.toString().charAt(0);
        } else if (hidden) {
            text = ' ';
        } else {
            text = '*';
        }
        return text;
    }
}
