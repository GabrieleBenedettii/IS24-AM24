package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SymbolGoal extends GoalCard{
    private final HashMap<Symbol,Integer> symbols;
    public SymbolGoal(int imageId, Integer points, HashMap<Symbol,Integer> symbols) {
        super(imageId, points);
        this.symbols = symbols;
    }

    public int calculatePoints(HashMap<Symbol,Integer> visibleSymbols){
        ArrayList<Integer> num = new ArrayList<>();
        for(Symbol s : symbols.keySet()) {
            num.add(visibleSymbols.get(s)/symbols.get(s));
        }
        return Collections.min(num)*this.getPoints();
    }

    public String printCard() {
        StringBuilder text = new StringBuilder("Points: " + this.getPoints());
        for (Symbol s : symbols.keySet())
            text.append("\n" + s.toString() + " -> " + symbols.get(s));
        return text.toString();
    }
}
