package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Player;
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

    public int calculatePoints(Player p){
        boolean[][] alreadyUsed = new boolean[21][41];
        int points = 0;
        for(int i = 1-direction; i < p.getGameBoard().length - (1+direction); i++){
            for(int j = 0; j < p.getGameBoard()[i].length - 2; j++){
                if(!alreadyUsed[i][j] && p.getGameBoard()[i][j] != null && p.getGameBoard()[i+2*direction][j+2] != null && p.getGameBoard()[i+direction][j+1] != null && !alreadyUsed[i+direction][j+1] && !alreadyUsed[i+2*direction][j+2] && kingdom.equals(p.getGameBoard()[i][j].getKingdom()) && kingdom.equals(p.getGameBoard()[i+direction][j+1].getKingdom()) && kingdom.equals(p.getGameBoard()[i+2*direction][j+2].getKingdom())) {
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