package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.InitialCard;

public class ObliqueDisposition extends GoalCard{
    private Kingdom kingdom;

    public ObliqueDisposition(String frontImage, String backImage, Integer points, Kingdom kingdom) {
        super(frontImage, backImage, points);
        this.kingdom = kingdom;
    }

    public int calculatePoints(InitialCard card){
        return points;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }
}