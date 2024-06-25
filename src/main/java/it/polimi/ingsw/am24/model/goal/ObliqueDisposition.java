package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.modelView.GameCardView;

/**
 * The {@code ObliqueDisposition} class represents a specific type of goal card where players score points based on oblique arrangements on their game board.
 * It extends {@code GoalCard}.
 */
public class ObliqueDisposition extends GoalCard{
    /** The kingdom associated with this goal card. */
    private final Kingdom kingdom;
    /** The direction of the oblique disposition: 1 for top-left (\), -1 for top-right (/). */
    private final int direction;

    /**
     * Constructs an {@code ObliqueDisposition} goal card with specified attributes.
     *
     * @param imageId the image ID of the card
     * @param points the points awarded for achieving this goal
     * @param kingdom the kingdom associated with this goal
     * @param direction the direction of the oblique disposition (1 for top-left, -1 for top-right)
     */
    public ObliqueDisposition(int imageId, int points, Kingdom kingdom, int direction) {
        super(imageId, points);
        this.kingdom = kingdom;
        this.direction = direction;
    }

    /**
     * Retrieves the kingdom associated with this goal card.
     *
     * @return the kingdom
     */
    public Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * Calculates the points scored by a player based on fulfilling the oblique disposition goal.
     *
     * @param p the player whose board is checked for fulfilling the goal
     * @return the points scored for this goal
     */
    public int calculatePoints(Player p){
        boolean[][] alreadyUsed = new boolean[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];
        int points = 0;
        for(int i = 1-direction; i < p.getGameBoard().length - (1+direction); i++){
            for(int j = 0; j < p.getGameBoard()[i].length - 2; j++){
                if(!alreadyUsed[i][j] && !alreadyUsed[i+direction][j+1] && !alreadyUsed[i+2*direction][j+2] &&
                        p.getGameBoard()[i][j] != null && p.getGameBoard()[i+direction][j+1] != null && p.getGameBoard()[i+2*direction][j+2] != null &&
                        kingdom.equals(p.getGameBoard()[i][j].getKingdom()) && kingdom.equals(p.getGameBoard()[i+direction][j+1].getKingdom()) && kingdom.equals(p.getGameBoard()[i+2*direction][j+2].getKingdom())) {
                    points += getPoints();
                    alreadyUsed[i][j] = true;
                    alreadyUsed[i+direction][j+1] = true;
                    alreadyUsed[i+2*direction][j+2] = true;
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
        text += "\n\tDisposition: 3 oblique " + Constants.getText(kingdom) + " from " + (direction == 0 ? "bottom-right to top-left" : "bottom-left to top-right");
        return text;
    }

    /**
     * Creates a {@code GameCardView} object to display the goal card's information.
     *
     * @return a {@code GameCardView} representing this goal card
     */
    public GameCardView getView() {
        return new GameCardView("Goal Card - Oblique disposition", this.getImageId(), this.printCard());
    }
}