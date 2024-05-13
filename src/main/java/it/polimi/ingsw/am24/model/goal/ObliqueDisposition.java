package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.modelView.GameCardView;

public class ObliqueDisposition extends GoalCard{
    private final Kingdom kingdom;
    private final int direction;    //direction = 1 -> top-left (\), direction = -1 -> top-right (/)

    public ObliqueDisposition(int imageId, int points, Kingdom kingdom, int direction) {
        super(imageId, points);
        this.kingdom = kingdom;
        this.direction = direction;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int calculatePoints(GameCard[][] board){
        boolean[][] alreadyUsed = new boolean[21][41];
        int points = 0;
        for(int i = 1-direction; i < board.length - (1+direction); i++){
            for(int j = 0; j < board[i].length - 2; j++){
                if(!alreadyUsed[i][j] && !alreadyUsed[i+direction][j+1] && !alreadyUsed[i+2*direction][j+2] && kingdom.equals(board[i][j].getKingdom()) && kingdom.equals(board[i+direction][j+1].getKingdom()) && kingdom.equals(board[i+2*direction][j+2].getKingdom())) {
                    points += getPoints();
                    alreadyUsed[i][j] = true;
                    alreadyUsed[i+direction][j+1] = true;
                    alreadyUsed[i+2*direction][j+2] = true;
                }
            }
        }
        return points;
    }

    public String printCard() {
        String text = "Points: " + this.getPoints();
        text += "\n\tDisposition: 3 oblique " + Constants.getText(kingdom) + " from " + (direction == 0 ? "bottom-right to top-left" : "bottom-left to top-right");
        return text;
    }

    public GameCardView getView() {
        return new GameCardView("Goal Card - Oblique disposition", this.getImageId(), this.printCard());
    }
}