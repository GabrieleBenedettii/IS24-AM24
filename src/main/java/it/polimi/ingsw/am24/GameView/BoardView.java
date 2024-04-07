package it.polimi.ingsw.am24.GameView;

import javafx.util.Pair;

public class BoardView {
    private final String[][] board;
    private final Pair<Integer, Integer> possiblePlacements;
    public BoardView(String[][] board, Pair<Integer, Integer> possiblePlacements) {
        this.board = board;
        this.possiblePlacements = possiblePlacements;
    }

    public String[][] getBoard() {
        return board;
    }
   public Pair<Integer, Integer> getPossiblePlacements() {
       return possiblePlacements;
   }
}
