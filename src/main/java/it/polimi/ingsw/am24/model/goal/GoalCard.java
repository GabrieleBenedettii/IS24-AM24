package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.modelView.GameCardView;

/**
 * The {@code GoalCard} class represents a goal card in the game.
 * It includes methods for accessing the image ID, points, calculating points for a player, and generating a view of the card.
 */
public abstract class GoalCard {
    /**
     * The ID of the image representing the goal card.
     */
    private final int imageId;

    /**
     * The points associated with the goal card.
     */
    private final int points;

    /**
     * Initializes a {@code GoalCard} with the specified image ID and points.
     *
     * @param imageId The ID of the image representing the goal card.
     * @param points  The points associated with the goal card.
     */
    public GoalCard(int imageId, Integer points) {
        this.imageId = imageId;
        this.points = points;
    }

    /**
     * Retrieves the image ID of the goal card.
     *
     * @return The image ID.
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Retrieves the points associated with the goal card.
     *
     * @return The points.
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Calculates the points scored by a player for this goal card.
     *
     * @param p The player for whom the points are calculated.
     * @return The points scored by the player.
     */
    public int calculatePoints(Player p) {
        return 0;
    }

    /**
     * Generates a string representation of the goal card.
     *
     * @return A string representation of the goal card.
     */
    public String printCard(){
        return "";
    }

    /**
     * Generates a view of the goal card.
     *
     * @return A {@link GameCardView} representing the goal card.
     */
    public GameCardView getView() {
        return new GameCardView("Goal Card", imageId, printCard());
    }
}
