package it.polimi.ingsw.am24.modelView;

import javafx.util.Pair;

import java.io.Serializable;

public class BoardView implements Serializable {
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
