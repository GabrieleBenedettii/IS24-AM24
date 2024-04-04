package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Cell;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.PlayableCard;

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

    public int calculatePoints(Cell[][] board){
        int points = 0;
        for(int i = 0; i < board.length - 2; i = i + 2){
            for(int j = 2; j < board[i].length; j = j + 2){
                if(board[i][j].getCard().getKingdom().equals(kingdom) && board[i+1][j-1].getCard().getKingdom().equals(kingdom) && board[i+2][j-2].getCard().getKingdom().equals(kingdom)){
                    points = points + 2;
                }
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