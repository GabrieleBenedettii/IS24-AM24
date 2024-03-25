package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Gold;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.Arrays;
import java.util.HashMap;

public class TripleGold extends GoalCard{

    public TripleGold(String frontImage, String backImage, Integer points) {
        super(frontImage, backImage, points);
    }

    public int calculatePoints(HashMap<Symbol,Integer> visibleSymbols){
        int[] numGold = new int[3];
        numGold[0] = visibleSymbols.get(Gold.INK);
        numGold[1] = visibleSymbols.get(Gold.QUILL);
        numGold[2] = visibleSymbols.get(Gold.MANUSCRIPT);
        return Arrays.stream(numGold).max().orElse(0);
    }
}