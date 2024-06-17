package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SymbolGoal extends GoalCard{
    private final HashMap<Symbol,Integer> symbols;
    public SymbolGoal(int imageId, Integer points, HashMap<Symbol,Integer> symbols) {
        super(imageId, points);
        this.symbols = symbols;
    }

    public int calculatePoints(Player p){
        ArrayList<Integer> num = new ArrayList<>();
        for(Symbol s : symbols.keySet()) {
            num.add(p.getVisibleSymbols().get(s)/symbols.get(s));
        }
        return Collections.min(num)*this.getPoints();
    }

    public String printCard() {
        StringBuilder text = new StringBuilder("Points: " + this.getPoints());
        for (Symbol s : symbols.keySet())
            text.append("\n\t").append(Constants.getText(s)).append(" -> ").append(symbols.get(s));
        return text.toString();
    }

    public GameCardView getView() {
        return new GameCardView("Goal Card - Symbol goal", this.getImageId(), this.printCard());
    }
}
