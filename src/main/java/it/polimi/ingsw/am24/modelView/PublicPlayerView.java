package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The PublicPlayerView class represents the public view of a player.
 */
public class PublicPlayerView implements Serializable {

    private final String color;
    private final int playerScore;
    private final GameCardView[][] board;
    private final ArrayList<Placement> placeOrder;
    private final HashMap<String, Integer> visibleSymbols;
    private final boolean[][] possiblePlacements;

    /**
     * Constructs a PublicPlayerView object with the specified color, player score, board, placement order, visible symbols, and possible placements.
     * @param color The color of the player.
     * @param playerScore The score of the player.
     * @param board The player's board.
     * @param placeOrder The order of placements made by the player.
     * @param visibleSymbols The visible symbols on the player's board.
     * @param possiblePlacements The possible placements on the player's board.
     */
    public PublicPlayerView(String color,int playerScore, GameCardView[][] board, ArrayList<Placement> placeOrder, HashMap<String, Integer> visibleSymbols, boolean[][] possiblePlacements) {
        this.color = color;
        this.playerScore = playerScore;
        this.board = board;
        this.placeOrder = placeOrder;
        this.visibleSymbols = visibleSymbols;
        this.possiblePlacements = possiblePlacements;
    }

    /**
     * Get the player's board.
     * @return The player's board.
     */
    public GameCardView[][] getBoard() {
        return board;
    }

    /**
     * Get the player's score.
     * @return The player's score.
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Get the order of placements made by the player.
     * @return The order of placements made by the player.
     */
    public ArrayList<Placement> getPlaceOrder() {
        return placeOrder;
    }

    /**
     * Get the visible symbols on the player's board.
     * @return The visible symbols on the player's board.
     */
    public HashMap<String, Integer> getVisibleSymbols() {
        return visibleSymbols;
    }

    /**
     * Get the possible placements on the player's board.
     * @return The possible placements on the player's board.
     */
    public boolean[][] getPossiblePlacements() {
        return possiblePlacements;
    }

    /**
     * Get the color of the player.
     * @return The color of the player.
     */
    public String getColor() {
        return color;
    }
}
