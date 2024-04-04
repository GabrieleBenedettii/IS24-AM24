package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Cell;
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

    public int calculatePoints(Cell[][] board){
        int points = 0;
        if(mainKingdom.equals(Kingdom.PLANT)){
            for(int i = 0; i < board.length - 1; i++){
                for(int j = 1; j < board[i].length; j++){
                    if(board[i][j].getCard().getKingdom().equals(Kingdom.PLANT) && board[i+2][j].getCard().getKingdom().equals(Kingdom.PLANT) && board[i+3][j-1].getCard().getKingdom().equals(Kingdom.INSECT)){
                        points = points + 3;
                    }
                }
            }
        }
        else if(mainKingdom.equals(Kingdom.FUNGI)){
            for(int i = 0; i < board.length - 1; i++){
                for(int j = 0; j < board[i].length - 1; j++){
                    if(board[i][j].getCard().getKingdom().equals(Kingdom.FUNGI) && board[i+2][j].getCard().getKingdom().equals(Kingdom.FUNGI) && board[i+3][j+1].getCard().getKingdom().equals(Kingdom.PLANT)){
                        points = points + 3;
                    }
                }
            }
        }
        else if(mainKingdom.equals(Kingdom.ANIMAL)){
            for(int i = 1; i < board.length; i++){
                for(int j = 0; j < board[i].length - 1; j++){
                    if(board[i][j].getCard().getKingdom().equals(Kingdom.ANIMAL) && board[i+2][j].getCard().getKingdom().equals(Kingdom.ANIMAL) && board[i-1][j+1].getCard().getKingdom().equals(Kingdom.FUNGI)){
                        points = points + 3;
                    }
                }
            }
        }
        else if(mainKingdom.equals(Kingdom.INSECT)){
            for(int i = 1; i < board.length; i++){
                for(int j = 1; j < board[i].length; j++){
                    if(board[i][j].getCard().getKingdom().equals(Kingdom.PLANT) && board[i+2][j].getCard().getKingdom().equals(Kingdom.PLANT) && board[i-1][j-1].getCard().getKingdom().equals(Kingdom.INSECT)){
                        points = points + 3;
                    }
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
}
