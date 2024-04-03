package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GameCard;

public class ObliqueDisposition extends GoalCard{
    private final Kingdom kingdom;
    private final int direction;

    public ObliqueDisposition(int imageId, int points, Kingdom kingdom, int direction) {
        super(imageId, points);
        this.kingdom = kingdom;
        this.direction = direction;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int calculatePoints(GameCard[][] board){
        int points = 0;
        //todo
        return points;
    }

    public String printCard() {
        String text = "Points: " + this.getPoints();
        text += "\nDisposition: 3 oblique " + kingdom + " " + (direction == 0 ? "top-left" : "top-right");
        return text;
    }
}