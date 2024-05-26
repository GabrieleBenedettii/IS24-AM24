package it.polimi.ingsw.am24.model;

import java.util.Map;

/**
 * The ScoreBoard class represents the scoreboard of the game.
 * It stores coordinates and an image associated with each player's score.
 */
public class ScoreBoard {
    private Map<Integer,Integer> coordinates; // Map of player IDs to their coordinates on the scoreboard
    private String image; // Image representing the scoreboard

    /**
     * Gets the coordinates map.
     *
     * @return The map of player IDs to their coordinates on the scoreboard.
     */
    public Map<Integer, Integer> getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the image representing the scoreboard.
     *
     * @return The image representing the scoreboard.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the coordinates map.
     *
     * @param coordinates The map of player IDs to their coordinates on the scoreboard.
     */
    public void setCoordinates(Map<Integer, Integer> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Sets the image representing the scoreboard.
     *
     * @param image The image representing the scoreboard.
     */
    public void setImage(String image) {
        this.image = image;
    }
}
