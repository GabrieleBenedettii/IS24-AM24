package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.modelView.GameCardView;

/**
 * The {@code VerticalDisposition} class represents a goal card based on vertical disposition of game cards on the board.
 * It includes methods for calculating points based on the disposition, generating a string representation of the card, and generating a view of the card.
 */
public class VerticalDisposition extends GoalCard{
    /**
     * The main kingdom associated with the disposition.
     */
    private final Kingdom mainKingdom;

    /**
     * The secondary kingdom associated with the disposition.
     */
    private final Kingdom secondaryKingdom;

    /**
     * The corner of the secondary kingdom where the disposition occurs.
     */
    private final int secondaryKingdomCorner;

    /**
     * Initializes a {@code VerticalDisposition} with the specified image ID, points, main kingdom, secondary kingdom, and secondary kingdom corner.
     *
     * @param imageId                The ID of the image representing the goal card.
     * @param points                 The points associated with the goal card.
     * @param mainKingdom            The main kingdom associated with the disposition.
     * @param secondaryKingdom       The secondary kingdom associated with the disposition.
     * @param secondaryKingdomCorner The corner of the secondary kingdom where the disposition occurs.
     */
    public VerticalDisposition(int imageId, int points, Kingdom mainKingdom, Kingdom secondaryKingdom, int secondaryKingdomCorner) {
        super(imageId, points);
        this.mainKingdom = mainKingdom;
        this.secondaryKingdom = secondaryKingdom;
        this.secondaryKingdomCorner = secondaryKingdomCorner;
    }

    /**
     * Calculates the points scored by the player based on the vertical disposition of game cards on the board.
     *
     * @param board The game board represented as a 2D array of {@link GameCard} objects.
     * @return The points scored.
     */
    public int calculatePoints(GameCard[][] board){
        int points = 0;
        boolean[][] alreadyUsed = new boolean[21][41];
        for(int i = (secondaryKingdomCorner <= 1 ? 1 : 0); i < board.length - (secondaryKingdomCorner <= 1 ? 0 : 1); i++){
            for(int j = (secondaryKingdomCorner % 2 == 0 ? 1 : 0); j < board[i].length - (secondaryKingdomCorner % 2 == 0 ? 0 : 1); j++){
                if(!alreadyUsed[i][j] && mainKingdom.equals(board[i][j].getKingdom()) && mainKingdom.equals(board[i+2][j].getKingdom()) && secondaryKingdom.equals(board[i+(secondaryKingdomCorner <= 1 ? -1 : 3)][j+(secondaryKingdomCorner % 2 == 0 ? -1 : 1)].getKingdom())){
                    points = points + 3;
                    alreadyUsed[i][j] = true;
                    alreadyUsed[i+2][j] = true;
                    alreadyUsed[i+(secondaryKingdomCorner <= 1 ? -1 : 3)][j+(secondaryKingdomCorner % 2 == 0 ? -1 : 1)] = true;
                }
            }
        }
        return points;
    }

    /**
     * Retrieves the main kingdom associated with the disposition.
     *
     * @return The {@link Kingdom}.
     */
    public Kingdom getMainKingdom() {
        return mainKingdom;
    }

    /**
     * Retrieves the secondary kingdom associated with the disposition.
     *
     * @return The {@link Kingdom}.
     */
    public Kingdom getSecondaryKingdom() {
        return secondaryKingdom;
    }

    /**
     * Retrieves the corner of the secondary kingdom where the disposition occurs.
     *
     * @return The corner index.
     */
    public int getSecondaryKingdomCorner() {
        return secondaryKingdomCorner;
    }

    /**
     * Generates a string representation of the vertical disposition goal card.
     *
     * @return A string representation of the goal card.
     */
    public String printCard() {
        String text = "Points: " + this.getPoints();
        text += "\n\tDisposition: 2 vertical " + Constants.getText(mainKingdom) + " and 1 " + Constants.getText(secondaryKingdom) + " in the " + Constants.corner.get(secondaryKingdomCorner) + " corner";
        return text;
    }

    /**
     * Generates a view of the vertical disposition goal card.
     *
     * @return A {@link GameCardView} representing the goal card.
     */
    public GameCardView getView(){
        return new GameCardView("Goal Card - Vertical disposition", this.getImageId(), this.printCard());
    }
}
