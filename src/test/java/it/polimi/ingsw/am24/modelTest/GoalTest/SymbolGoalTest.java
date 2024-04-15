package it.polimi.ingsw.am24.modelTest.GoalTest;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.goal.ObliqueDisposition;
import it.polimi.ingsw.am24.model.goal.SymbolGoal;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SymbolGoalTest {

    public void testCalculatePoints()
    {
        HashMap<Symbol, Integer> visibleSymbols = new HashMap<>();
        visibleSymbols.put(Symbol.ANIMAL, 3);
        visibleSymbols.put(Symbol.FUNGI, 3);
        int points = 0;

        HashMap<Symbol, Integer> symbol  = new HashMap<>();
        symbol.put(Symbol.ANIMAL, 2);

        SymbolGoal symbolgoal =new SymbolGoal(1,2,symbol);

        points=symbolgoal.calculatePoints(visibleSymbols);
        assert(points == 2);
    }

    public void testPrintCard() {
        // Definisci i requisiti della carta
        HashMap<Symbol, Integer> symbol  = new HashMap<>();
        symbol.put(Symbol.ANIMAL, 2);

        // Crea una nuova carta d'oro
        SymbolGoal symbolgoal =new SymbolGoal(1,2,symbol);

        String text = new String();
        text="";
        assertEquals(text,symbolgoal.printCard());
    }

}
