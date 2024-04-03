package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

public abstract class PlayableCard extends GameCard{
    private final Kingdom kingdom;
    private final int points;

    public PlayableCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols);
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
