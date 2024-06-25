package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.modelView.GameCardView;

/**
 * The {@code VerticalDisposition} class represents a specific type of goal card where players score points based on vertical arrangements of kingdoms on their game board.
 * It extends {@code GoalCard}.
 */
public class VerticalDisposition extends GoalCard{
    /** The main kingdom involved in the vertical disposition. */
    private final Kingdom mainKingdom;
    /** The secondary kingdom involved in the vertical disposition. */
    private final Kingdom secondaryKingdom;
    /** The corner of the secondary kingdom involved in the vertical disposition. */
    private final int secondaryKingdomCorner;

    /**
     * Constructs a {@code VerticalDisposition} goal card with specified attributes.
     *
     * @param imageId the image ID of the card
     * @param points the points awarded for achieving this goal
     * @param mainKingdom the main kingdom involved in the vertical disposition
     * @param secondaryKingdom the secondary kingdom involved in the vertical disposition
     * @param secondaryKingdomCorner the corner of the secondary kingdom involved in the vertical disposition
     */
    public VerticalDisposition(int imageId, int points, Kingdom mainKingdom, Kingdom secondaryKingdom, int secondaryKingdomCorner) {
        super(imageId, points);
        this.mainKingdom = mainKingdom;
        this.secondaryKingdom = secondaryKingdom;
        this.secondaryKingdomCorner = secondaryKingdomCorner;
    }

    /**
     * Calculates the points scored by a player based on fulfilling the vertical disposition goal.
     *
     * @param p the player whose board is checked for fulfilling the goal
     * @return the points scored for this goal
     */
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

    /**
     * Generates a string representation of this goal card's details.
     *
     * @return a string representation of the goal card
     */
    public String printCard() {
        String text = "Points: " + this.getPoints();
        text += "\n\tDisposition: 2 vertical " + Constants.getText(mainKingdom) + " and 1 " + Constants.getText(secondaryKingdom) + " in the " + Constants.corner.get(secondaryKingdomCorner) + " corner";
        return text;
    }

    /**
     * Creates a {@code GameCardView} object to display the goal card's information.
     *
     * @return a {@code GameCardView} representing this goal card
     */
    public GameCardView getView() {
        return new GameCardView("Goal Card - Vertical disposition", this.getImageId(), this.printCard());
    }
}
