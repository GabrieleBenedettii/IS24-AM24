package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
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

    public int calculatePoints(GameCard[][] board){
        int points = 0;
        for(int i = (secondaryKingdomCorner <= 1 ? 1 : 0); i < board.length - (secondaryKingdomCorner <= 1 ? 0 : 1); i++){
            for(int j = (secondaryKingdomCorner % 2 == 0 ? 1 : 0); j < board[i].length - (secondaryKingdomCorner % 2 == 0 ? 0 : 1); j++){
                if(mainKingdom.equals(board[i][j].getKingdom()) && mainKingdom.equals(board[i+2][j].getKingdom()) && secondaryKingdom.equals(board[i+(secondaryKingdomCorner <= 1 ? -1 : 3)][j+(secondaryKingdomCorner % 2 == 0 ? -1 : 1)].getKingdom())){
                    points = points + 3;
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
        text += "\nDisposition: 2 vertical " + mainKingdom + " and 1 " + secondaryKingdom + " in corner " + secondaryKingdomCorner;
        return text;
    }

    public GameCardView getView() {
        return new GameCardView("Goal Card - Vertical disposition", this.getImageId(), this.printCard());
    }
}
