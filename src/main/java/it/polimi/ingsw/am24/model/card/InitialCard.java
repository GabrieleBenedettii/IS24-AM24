package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.ArrayList;

public class InitialCard extends GameCard{
    private final ArrayList<Kingdom> kingdoms;
    private InitialCard backCard;

    public InitialCard(int imageId, Symbol[] symbols, ArrayList<Kingdom> kingdoms) {
        super(imageId,symbols);
        this.kingdoms = kingdoms;
    }

    public InitialCard getBackCard() {
        return backCard;
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public String printCard() {
        StringBuilder print = new StringBuilder("FRONT\nKingdoms: ");
        for (Kingdom k: kingdoms) {
            print.append(" ").append(Costants.getText(k)).append(" ");
        }
        print.append("\nCorners: ");
        for (CardCorner c: this.getCorners()) {
            print.append(c.isHidden() ? Costants.HIDDEN + " " : (c.getSymbol()!=null ? Costants.getText(c.getSymbol()) : Costants.EMPTY) + " ");
        }
        print.append("\nBACK\nKingdoms:");
        for (Kingdom k: backCard.kingdoms) {
            print.append(" ").append(Costants.getText(k)).append(" ");
        }
        print.append("\nCorners: ");
        for (CardCorner c: backCard.getCorners()) {
            print.append(c.isHidden() ? Costants.HIDDEN + " " : (c.getSymbol()!=null ? Costants.getText(c.getSymbol()) : Costants.EMPTY) + " ");
        }
        return print.toString();
    }
}
