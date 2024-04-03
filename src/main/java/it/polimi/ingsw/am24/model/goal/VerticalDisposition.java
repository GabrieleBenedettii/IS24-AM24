package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GameCard;

public class VerticalDisposition extends GoalCard{
    private final Kingdom mainKingdom;
    private final Kingdom secondaryKingdom;
    private final int secondaryKingdomCorner;

    public VerticalDisposition(int imageId, int points, Kingdom mainKingdom, Kingdom secondaryKingdom, int secondaryKingdomCorner) {
        super(imageId, points);
        this.mainKingdom = mainKingdom;
        this.secondaryKingdom = secondaryKingdom;
        this.secondaryKingdomCorner = secondaryKingdomCorner;
    }

    public int calculatePoints(GameCard[][] board){
        int points = 0;
        //todo
        return points;
    }

    public Kingdom getMainKingdom() {
        return mainKingdom;
    }

    public Kingdom getSecondaryKingdom() {
        return secondaryKingdom;
    }
    public int getSecondaryKingdomCorner() {
        return secondaryKingdomCorner;
    }

    public String printCard() {
        String text = "Points: " + this.getPoints();
        text += "\nDisposition: 2 vertical " + mainKingdom + " and 1 " + secondaryKingdom + " in corner " + secondaryKingdomCorner;
        return text;
    }
}
