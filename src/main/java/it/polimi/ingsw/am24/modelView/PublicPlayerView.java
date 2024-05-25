package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PublicPlayerView implements Serializable {
    private final int playerScore;
    private final GameCardView[][] board;
    private final ArrayList<Placement> placeOrder;
    private final HashMap<String, Integer> visibleSymbols;
    private final boolean[][] possiblePlacements;

    public PublicPlayerView(int playerScore, GameCardView[][] board, ArrayList<Placement> placeOrder, HashMap<String, Integer> visibleSymbols, boolean[][] possiblePlacements) {
        this.playerScore = playerScore;
        this.board = board;
        this.placeOrder = placeOrder;
        this.visibleSymbols = visibleSymbols;
        this.possiblePlacements = possiblePlacements;
    }

    public GameCardView[][] getBoard() {
        return board;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public ArrayList<Placement> getPlaceOrder() {
        return placeOrder;
    }

    public HashMap<String, Integer> getVisibleSymbols() {
        return visibleSymbols;
    }

    public boolean[][] getPossiblePlacements() {
        return possiblePlacements;
    }
}
