package it.polimi.ingsw.am24.modelTest.GoalTest;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.goal.ObliqueDisposition;
import it.polimi.ingsw.am24.model.goal.SymbolGoal;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SymbolGoalTest {

    @Test
    public void testCalculatePoints()
    {
        HashMap<Symbol, Integer> visibleSymbolsK = new HashMap<>();
        HashMap<Symbol, Integer> visibleSymbolsG = new HashMap<>();
        HashMap<Symbol, Integer> visibleSymbolsT = new HashMap<>();

        visibleSymbolsK.put(Symbol.ANIMAL, 3);
        visibleSymbolsG.put(Symbol.QUILL, 2);
        visibleSymbolsT.put(Symbol.INK, 1);
        visibleSymbolsT.put(Symbol.MANUSCRIPT, 1);
        visibleSymbolsT.put(Symbol.QUILL, 1);

        int pointsK;
        int pointsG;
        int pointsT;

        HashMap<Symbol, Integer> symbolK  = new HashMap<>();
        HashMap<Symbol, Integer> symbolG  = new HashMap<>();
        HashMap<Symbol, Integer> symbolT  = new HashMap<>();

        symbolK.put(Symbol.ANIMAL, 3);
        symbolG.put(Symbol.QUILL, 2);
        symbolT.put(Symbol.INK, 1);
        symbolT.put(Symbol.MANUSCRIPT, 1);
        symbolT.put(Symbol.QUILL, 1);


        SymbolGoal symbolgoalK =new SymbolGoal(1,2,symbolK);
        SymbolGoal symbolgoalG =new SymbolGoal(1,2,symbolG);
        SymbolGoal symbolgoalT =new SymbolGoal(1,2,symbolT);

        pointsK=symbolgoalK.calculatePoints(visibleSymbolsK);
        pointsG=symbolgoalG.calculatePoints(visibleSymbolsG);
        pointsT=symbolgoalT.calculatePoints(visibleSymbolsT);

        assert(pointsK == 2);
        assert(pointsG == 2);
        assert(pointsT == 3);
    }
}
