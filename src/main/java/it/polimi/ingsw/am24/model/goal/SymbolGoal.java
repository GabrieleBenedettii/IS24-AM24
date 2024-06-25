package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The {@code SymbolGoal} class represents a specific type of goal card where players score points based on specific symbols on their game board.
 * It extends {@code GoalCard}.
 */
public class SymbolGoal extends GoalCard{
    /** The symbols and their required counts associated with this goal card. */
    private final HashMap<Symbol,Integer> symbols;

    /**
     * Constructs a {@code SymbolGoal} goal card with specified attributes.
     *
     * @param imageId the image ID of the card
     * @param points the points awarded for achieving this goal
     * @param symbols the symbols and their required counts associated with this goal
     */
    public SymbolGoal(int imageId, Integer points, HashMap<Symbol,Integer> symbols) {
        super(imageId, points);
        this.symbols = symbols;
    }

    /**
     * Calculates the points scored by a player based on fulfilling the symbol goal.
     *
     * @param p the player whose board is checked for fulfilling the goal
     * @return the points scored for this goal
     */
    public int calculatePoints(Player p){
        ArrayList<Integer> num = new ArrayList<>();
        for(Symbol s : symbols.keySet()) {
            num.add(p.getVisibleSymbols().get(s)/symbols.get(s));
        }
        return Collections.min(num)*this.getPoints();
    }

    /**
     * Generates a string representation of this goal card's details.
     *
     * @return a string representation of the goal card
     */
    public String printCard() {
        StringBuilder text = new StringBuilder("Points: " + this.getPoints());
        for (Symbol s : symbols.keySet())
            text.append("\n\t").append(Constants.getText(s)).append(" -> ").append(symbols.get(s));
        return text.toString();
    }

    /**
     * Creates a {@code GameCardView} object to display the goal card's information.
     *
     * @return a {@code GameCardView} representing this goal card
     */
    public GameCardView getView() {
        return new GameCardView("Goal Card - Symbol goal", this.getImageId(), this.printCard());
    }
}
