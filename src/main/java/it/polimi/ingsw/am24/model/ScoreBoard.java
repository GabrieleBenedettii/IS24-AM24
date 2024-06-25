package it.polimi.ingsw.am24.model;

import java.util.Map;

/**
 * The {@code ScoreBoard} class represents a scoreboard with coordinates and an associated image.
 */
public class ScoreBoard {
    /**
     * A map representing the coordinates on the scoreboard.
     * The key is an integer representing a specific coordinate (e.g., x-coordinate),
     * and the value is an integer representing the corresponding coordinate (e.g., y-coordinate).
     */
    private Map<Integer,Integer> coordinates;
    /**
     * A string representing the image associated with the scoreboard.
     */
    private String image;

    /**
     * Gets the coordinates of the scoreboard.
     *
     * @return a {@code Map<Integer, Integer>} representing the coordinates
     */
    public Map<Integer, Integer> getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the image associated with the scoreboard.
     *
     * @return a {@code String} representing the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the coordinates of the scoreboard.
     *
     * @param coordinates a {@code Map<Integer, Integer>} representing the new coordinates
     */
    public void setCoordinates(Map<Integer, Integer> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Sets the image associated with the scoreboard.
     *
     * @param image a {@code String} representing the new image
     */
    public void setImage(String image) {
        this.image = image;
    }
}
