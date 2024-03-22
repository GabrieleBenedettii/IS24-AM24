package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Gold;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

public class DoubleGold extends GoalCard {
    private Gold symbol;

    public DoubleGold(String frontImage, String backImage, Integer points, Gold symbol) {
        super(frontImage, backImage, points);
        this.symbol = symbol;
    }
    public int calculatePoints(HashMap<Symbol,Integer> visibleSymbols){
        return points;
    }

    public Gold getSymbol() {
        return symbol;
    }
}
