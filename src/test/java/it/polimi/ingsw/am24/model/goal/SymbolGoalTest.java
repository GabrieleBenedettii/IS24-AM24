package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SymbolGoalTest {
    HashMap<Symbol, Integer> visibleSymbolsK,visibleSymbolsG, visibleSymbolsT;  //symbols visible on board
    HashMap<Symbol, Integer> symbolK,symbolG,symbolT;   //symbols requirements
    SymbolGoal symbolgoalK,symbolgoalG,symbolgoalT;     //cards
    @BeforeEach
    public void setUp(){
        symbolK  = new HashMap<>();
        symbolG  = new HashMap<>();
        symbolT  = new HashMap<>();

        symbolK.put(Symbol.ANIMAL, 3);
        symbolG.put(Symbol.QUILL, 2);
        symbolT.put(Symbol.INK, 1);
        symbolT.put(Symbol.MANUSCRIPT, 1);
        symbolT.put(Symbol.QUILL, 1);

        symbolgoalK =new SymbolGoal(1,2,symbolK);
        symbolgoalG =new SymbolGoal(2,2,symbolG);
        symbolgoalT =new SymbolGoal(3,2,symbolT);

        visibleSymbolsK = new HashMap<>();
        visibleSymbolsG = new HashMap<>();
        visibleSymbolsT = new HashMap<>();
    }

    @Test
    @DisplayName("Check correct point calculation")
    public void testCalculatePoints1() {
        visibleSymbolsK.put(Symbol.ANIMAL, 3);
        visibleSymbolsG.put(Symbol.QUILL, 2);
        visibleSymbolsT.put(Symbol.INK, 1);
        visibleSymbolsT.put(Symbol.MANUSCRIPT, 1);
        visibleSymbolsT.put(Symbol.QUILL, 1);

        assertEquals(2,symbolgoalK.calculatePoints(visibleSymbolsK));
        assertEquals(2,symbolgoalG.calculatePoints(visibleSymbolsG));
        assertEquals(2,symbolgoalT.calculatePoints(visibleSymbolsT));
    }

    @Test
    @DisplayName("Ensure each card is counted once when calculating points")
    public void testCalculatePoints2(){
        visibleSymbolsK.put(Symbol.ANIMAL, 5);
        visibleSymbolsG.put(Symbol.QUILL, 3);
        visibleSymbolsT.put(Symbol.INK, 2);
        visibleSymbolsT.put(Symbol.MANUSCRIPT, 1);
        visibleSymbolsT.put(Symbol.QUILL, 2);

        assertEquals(2,symbolgoalK.calculatePoints(visibleSymbolsK));
        assertEquals(2,symbolgoalG.calculatePoints(visibleSymbolsG));
        assertEquals(2,symbolgoalT.calculatePoints(visibleSymbolsT));
    }

    @Test
    @DisplayName("Ensure accurate detection of two achieved goals")
    public void testCalculatePoints3(){
        visibleSymbolsK.put(Symbol.ANIMAL, 6);
        visibleSymbolsG.put(Symbol.QUILL, 4);
        visibleSymbolsT.put(Symbol.INK, 2);
        visibleSymbolsT.put(Symbol.MANUSCRIPT, 2);
        visibleSymbolsT.put(Symbol.QUILL, 3);

        assertEquals(4,symbolgoalK.calculatePoints(visibleSymbolsK));
        assertEquals(4,symbolgoalG.calculatePoints(visibleSymbolsG));
        assertEquals(4,symbolgoalT.calculatePoints(visibleSymbolsT));
    }
}
