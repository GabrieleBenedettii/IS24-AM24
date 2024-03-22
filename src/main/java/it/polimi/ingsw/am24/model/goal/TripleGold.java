package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

public class TripleGold extends GoalCard{

    public TripleGold(String frontImage, String backImage, Integer points) {
        super(frontImage, backImage, points);
    }

    public int calculatePoints(HashMap<Symbol,Integer> visibleSymbols){
        return points;
    }
}