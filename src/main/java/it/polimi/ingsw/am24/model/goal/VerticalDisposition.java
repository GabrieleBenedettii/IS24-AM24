package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.modelView.GameCardView;

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

    public int calculatePoints(Player p){
        int points = 0;
        boolean[][] alreadyUsed = new boolean[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];
        for(int i = (secondaryKingdomCorner <= 1 ? 1 : 0); i < p.getGameBoard().length - (secondaryKingdomCorner <= 1 ? 0 : 1); i++){
            for(int j = (secondaryKingdomCorner % 2 == 0 ? 1 : 0); j < p.getGameBoard()[i].length - (secondaryKingdomCorner % 2 == 0 ? 0 : 1); j++){
                if(!alreadyUsed[i][j] && p.getGameBoard()[i][j]!= null && p.getGameBoard()[i+2][j]!= null && p.getGameBoard()[i+(secondaryKingdomCorner <= 1 ? -1 : 3)][j+(secondaryKingdomCorner % 2 == 0 ? -1 : 1)] != null && mainKingdom.equals(p.getGameBoard()[i][j].getKingdom()) && mainKingdom.equals(p.getGameBoard()[i+2][j].getKingdom()) && secondaryKingdom.equals(p.getGameBoard()[i+(secondaryKingdomCorner <= 1 ? -1 : 3)][j+(secondaryKingdomCorner % 2 == 0 ? -1 : 1)].getKingdom())){
                    points = points + 3;
                    alreadyUsed[i][j] = true;
                    alreadyUsed[i+2][j] = true;
                    alreadyUsed[i+(secondaryKingdomCorner <= 1 ? -1 : 3)][j+(secondaryKingdomCorner % 2 == 0 ? -1 : 1)] = true;
                }
            }
        }
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
        text += "\n\tDisposition: 2 vertical " + Constants.getText(mainKingdom) + " and 1 " + Constants.getText(secondaryKingdom) + " in the " + Constants.corner.get(secondaryKingdomCorner) + " corner";
        return text;
    }

    public GameCardView getView() {
        return new GameCardView("Goal Card - Vertical disposition", this.getImageId(), this.printCard());
    }
}
