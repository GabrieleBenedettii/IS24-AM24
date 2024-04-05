package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GameCard;

public class ObliqueDisposition extends GoalCard{
    private final Kingdom kingdom;
    private final int direction;    //direction = -1 -> top-left, direction = 1 -> top-right

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
        for(int i = 1-direction; i < board.length - (1+direction); i++){
            for(int j = 0; j < board[i].length - 2; j++){
                if(kingdom.equals(board[i][j].getKingdom()) && kingdom.equals(board[i+direction][j+1].getKingdom()) && kingdom.equals(board[i+2*direction][j+2].getKingdom()))
                    points += 2;
            }
        }
        return points;
    }

    public String printCard() {
        String text = "Points: " + this.getPoints();
        text += "\nDisposition: 3 oblique " + kingdom + " " + (direction == 0 ? "top-left" : "top-right");
        return text;
    }
}