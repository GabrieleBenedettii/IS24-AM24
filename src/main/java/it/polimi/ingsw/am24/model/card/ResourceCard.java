package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.Kingdom;
public class ResourceCard extends GameCard{
    private Kingdom kingdom;
    private final int points;

    public ResourceCard(String frontImage, String backImage, Symbol[] symbols, Kingdom kingdom, int points) {
        super(frontImage, backImage, symbols);
        this.kingdom = kingdom;
        this.points = points;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int getPoints() {
        return points;
    }

    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Costants.getText(kingdom));
        text.append("\nCorners: ");
        for (CardCorner c: this.getCorners()) {
            text.append(c.isHidden() ? Costants.HIDDEN + " " : (c.getSymbol()!=null ? Costants.getText(c.getSymbol()) : Costants.EMPTY) + " ");
        }
        text.append("\nPoints: ");
        text.append(points);
        return text.toString();
    }
}
