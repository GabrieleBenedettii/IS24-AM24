package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The {@code SymbolGoal} class represents a goal card based on symbols in the game.
 * It includes methods for calculating points based on visible symbols, generating a string representation of the card, and generating a view of the card.
 */
public class SymbolGoal extends GoalCard {
    /**
     * The mapping of symbols to their required counts for this goal.
     */
    private final HashMap<Symbol,Integer> symbols;

    /**
     * Initializes a {@code SymbolGoal} with the specified image ID, points, and symbol requirements.
     *
     * @param imageId The ID of the image representing the goal card.
     * @param points  The points associated with the goal card.
     * @param symbols The mapping of symbols to their required counts for this goal.
     */
    public SymbolGoal(int imageId, Integer points, HashMap<Symbol,Integer> symbols) {
        super(imageId, points);
        this.symbols = symbols;
    }

    /**
     * Calculates the points scored by a player for this symbol goal card based on visible symbols.
     *
     * @param visibleSymbols The mapping of visible symbols to their counts.
     * @return The points scored by the player.
     */
    public int calculatePoints(HashMap<Symbol,Integer> visibleSymbols){
        ArrayList<Integer> num = new ArrayList<>();
        for(Symbol s : symbols.keySet()) {
            num.add(visibleSymbols.get(s)/symbols.get(s));
        }
        return Collections.min(num)*this.getPoints();
    }

    /**
     * Generates a string representation of the symbol goal card.
     *
     * @return A string representation of the symbol goal card.
     */
    public String printCard() {
        StringBuilder text = new StringBuilder("Points: " + this.getPoints());
        for (Symbol s : symbols.keySet())
            text.append("\n\t").append(Constants.getText(s)).append(" -> ").append(symbols.get(s));
        return text.toString();
    }

    /**
     * Generates a view of the symbol goal card.
     *
     * @return A {@link GameCardView} representing the symbol goal card.
     */
    public GameCardView getView(){
        return new GameCardView("Goal Card - Symbol goal", this.getImageId(), this.printCard());
    }
}
