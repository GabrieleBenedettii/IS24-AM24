package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.modelView.GameCardView;

/**
 * The {@code GoalCard} class represents an abstract goal card in the game.
 * Specific types of goal cards will extend this class and define their own behavior.
 */
public abstract class GoalCard {
    /** The unique identifier of the card's image. */
    private final int imageId;
    /** The points associated with achieving this goal. */
    private final int points;

    /**
     * Constructs a {@code GoalCard} with the specified attributes.
     *
     * @param imageId the ID of the image associated with the card
     * @param points the points assigned to the card
     */
    public GoalCard(int imageId, Integer points) {
        this.imageId = imageId;
        this.points = points;
    }

    /**
     * Retrieves the image ID associated with the goal card.
     *
     * @return the image ID
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Retrieves the points associated with achieving the goal.
     *
     * @return the points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Calculates the points scored by a player for achieving this goal.
     * Subclasses will override this method to define specific scoring rules.
     *
     * @param p the player for whom the points are calculated
     * @return the points scored by the player for this goal
     */
    public int calculatePoints(Player p) {
        return 0;
    }

    /**
     * Generates a textual representation of the goal card.
     * Subclasses will override this method to provide specific details.
     *
     * @return a string representing the details of the goal card
     */
    public String printCard() {
        return "";
    }

    /**
     * Generates a view of the goal card for display in the game.
     *
     * @return a {@code GameCardView} object representing the goal card
     */
    public GameCardView getView() {
        return new GameCardView("Goal Card", imageId, printCard());
    }
}
