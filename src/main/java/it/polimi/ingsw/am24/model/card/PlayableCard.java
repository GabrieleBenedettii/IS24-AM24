package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

public abstract class PlayableCard extends GameCard{
    private final int points;

    public PlayableCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols, kingdom);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getType(){
        return "";
    }

    public PlayableCard getCardSide(boolean front) {
        if (!front) {
            for (int i = 0; i < 4; i++) {
                this.getCornerByIndex(i).setEmpty();
            }
        }
        return this;
    }
}
