package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

public class TripleSymbol extends GoalCard {
    private Kingdom kingdom;


    public TripleSymbol(String frontImage, String backImage, Integer points, Kingdom kingdom) {
        super(frontImage, backImage, points);
        this.kingdom = kingdom;
    }

    public int calculatePoints(HashMap<Symbol, Integer> visibleSymbols) {
        return points;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }
}
