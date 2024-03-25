package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.ArrayList;

public class InitialCard{
    private final ArrayList<Kingdom> kingdoms;
    private InitialCard backCard;
    private boolean front;
    private final ArrayList<CardCorner> corners;
    private final String frontImage;
    private final String backImage;

    public InitialCard(String frontImage, String backImage, Symbol[] symbols, ArrayList<Kingdom> kingdoms) {
        this.front = false;
        this.corners = new ArrayList<>();
        for (Symbol s: symbols) {
            this.corners.add(new CardCorner(s,true));
        }
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.kingdoms = kingdoms;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public ArrayList<CardCorner> getCorners() {
        return corners;
    }

    public CardCorner getCornerByIndex(int index) {
        return corners.get(index);
    }

    public String getFrontImage() {
        return frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public InitialCard getBackCard() {
        return backCard;
    }

    public void setBackCard(InitialCard backCard) {
        this.backCard = backCard;
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public String printCard() {
        StringBuilder print = new StringBuilder("Kingdoms: ");
        for (Kingdom k: kingdoms) {
            print.append(" ").append(Costants.getText(k)).append(" ");
        }
        print.append("\nCorners: ");
        for (CardCorner c: this.getCorners()) {
            print.append(c.isHidden() ? Costants.HIDDEN + " " : (c.getSymbol()!=null ? Costants.getText(c.getSymbol()) : Costants.EMPTY) + " ");
        }
        return print.toString();
    }
}
