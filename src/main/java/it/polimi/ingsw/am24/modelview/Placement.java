package it.polimi.ingsw.am24.modelview;

import java.io.Serializable;

/**
 * The Placement class represents a placement of a game card on the board.
 */
public class Placement implements Serializable {
    private int x;
    private int y;
    private GameCardView card;
    private boolean front;

    /**
     * Constructs a Placement object with the specified coordinates, game card, and orientation.
     * @param x The x-coordinate of the placement.
     * @param y The y-coordinate of the placement.
     * @param card The game card being placed.
     * @param front Whether the front side of the card is facing up.
     */
    public Placement(int x, int y, GameCardView card, boolean front) {
        this.x = x;
        this.y = y;
        this.card = card;
        this.front = front;
    }

    /**
     * Get the x-coordinate of the placement.
     * @return The x-coordinate of the placement.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-coordinate of the placement.
     * @return The y-coordinate of the placement.
     */
    public int getY() {
        return y;
    }

    /**
     * Get the game card being placed.
     * @return The game card being placed.
     */
    public GameCardView getCard() {
        return card;
    }

    /**
     * Check if the front side of the card is facing up.
     * @return True if the front side of the card is facing up, false otherwise.
     */
    public boolean getFront() {
        return front;
    }
}
