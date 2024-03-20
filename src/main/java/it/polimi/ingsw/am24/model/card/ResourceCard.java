package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.Kingdom;
public class ResourceCard extends GameCard{
    private final Kingdom kingdom;
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
}
