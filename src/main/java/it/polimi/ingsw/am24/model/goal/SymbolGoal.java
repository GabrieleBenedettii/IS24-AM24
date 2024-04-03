package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

public class SymbolGoal extends GoalCard{
    private final HashMap<Symbol,Integer> symbols;
    public SymbolGoal(int imageId, Integer points, HashMap<Symbol,Integer> symbols) {
        super(imageId, points);
        this.symbols = symbols;
    }

    public int calculatePoints(HashMap<Symbol,Integer> visibleSymbols){
        int points = 0;
        //todo
        return points;
    }

    public String printCard() {
        StringBuilder text = new StringBuilder("Points: " + this.getPoints());
        for (Symbol s : symbols.keySet())
            text.append("\n" + s.toString() + " -> " + symbols.get(s));
        return text.toString();
    }
}
