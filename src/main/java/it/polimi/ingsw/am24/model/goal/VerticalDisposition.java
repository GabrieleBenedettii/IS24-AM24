package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.InitialCard;

public class VerticalDisposition extends GoalCard{
    private Kingdom mainKingodm;
    private Kingdom secondaryKingdom;

    public VerticalDisposition(String frontImage, String backImage, Integer points, Kingdom mainKingodm, Kingdom secondaryKingdom) {
        super(frontImage, backImage, points);
        this.mainKingodm = mainKingodm;
        this.secondaryKingdom = secondaryKingdom;
    }

    public int calculatePoints(InitialCard card){
        return points;
    }

    public Kingdom getMainKingodm() {
        return mainKingodm;
    }

    public Kingdom getSecondaryKingdom() {
        return secondaryKingdom;
    }
}
