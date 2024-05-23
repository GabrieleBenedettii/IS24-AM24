package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.modelView.GameCardView;

/**
 * The {@code ObliqueDisposition} class represents a goal card based on oblique disposition of game cards on the board.
 * It includes methods for calculating points based on the disposition, generating a string representation of the card, and generating a view of the card.
 */
public class ObliqueDisposition extends GoalCard{
    /**
     * The kingdom associated with the disposition.
     */
    private final Kingdom kingdom;

    /**
     * The direction of the oblique disposition.
     * direction = 1 -> top-left (\), direction = -1 -> top-right (/)
     */
    private final int direction;

    /**
     * Initializes an {@code ObliqueDisposition} with the specified image ID, points, kingdom, and direction.
     *
     * @param imageId   The ID of the image representing the goal card.
     * @param points    The points associated with the goal card.
     * @param kingdom   The kingdom associated with the disposition.
     * @param direction The direction of the oblique disposition.
     */
    public ObliqueDisposition(int imageId, int points, Kingdom kingdom, int direction) {
        super(imageId, points);
        this.kingdom = kingdom;
        this.direction = direction;
    }

    /**
     * Retrieves the kingdom associated with the disposition.
     *
     * @return The {@link Kingdom}.
     */
    public Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * Calculates the points scored by the player based on the disposition of game cards on the board.
     *
     * @param board The game board represented as a 2D array of {@link GameCard} objects.
     * @return The points scored.
     */
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

    /**
     * Generates a string representation of the oblique disposition goal card.
     *
     * @return A string representation of the goal card.
     */
    public String printCard() {
        String text = "Points: " + this.getPoints();
        text += "\n\tDisposition: 3 oblique " + Constants.getText(kingdom) + " from " + (direction == 0 ? "bottom-right to top-left" : "bottom-left to top-right");
        return text;
    }

    /**
     * Generates a view of the oblique disposition goal card.
     *
     * @return A {@link GameCardView} representing the goal card.
     */
    public GameCardView getView() {
        return new GameCardView("Goal Card - Oblique disposition", this.getImageId(), this.printCard());
    }
}
