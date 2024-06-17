package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Player;
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
        Player playerK = new Player("ciao");
        Player playerG = new Player("ciao1");
        Player playerT = new Player("ciao2");

        playerK.getVisibleSymbols().put(Symbol.ANIMAL, 3);
        playerG.getVisibleSymbols().put(Symbol.QUILL, 2);
        playerT.getVisibleSymbols().put(Symbol.INK, 1);
        playerT.getVisibleSymbols().put(Symbol.MANUSCRIPT, 1);
        playerT.getVisibleSymbols().put(Symbol.QUILL, 1);


        assertEquals(2,symbolgoalK.calculatePoints(playerK));
        assertEquals(2,symbolgoalG.calculatePoints(playerG));
        assertEquals(2,symbolgoalT.calculatePoints(playerT));
    }

    @Test
    @DisplayName("Ensure each card is counted once when calculating points")
    public void testCalculatePoints2(){
        Player playerK = new Player("ciao");
        Player playerG = new Player("ciao1");
        Player playerT = new Player("ciao2");

        playerK.getVisibleSymbols().put(Symbol.ANIMAL, 5);
        playerG.getVisibleSymbols().put(Symbol.QUILL, 3);
        playerT.getVisibleSymbols().put(Symbol.INK, 2);
        playerT.getVisibleSymbols().put(Symbol.MANUSCRIPT, 1);
        playerT.getVisibleSymbols().put(Symbol.QUILL, 2);

        assertEquals(2,symbolgoalK.calculatePoints(playerK));
        assertEquals(2,symbolgoalG.calculatePoints(playerG));
        assertEquals(2,symbolgoalT.calculatePoints(playerT));
    }

    @Test
    @DisplayName("Ensure accurate detection of two achieved goals")
    public void testCalculatePoints3(){
        Player playerK = new Player("ciao");
        Player playerG = new Player("ciao1");
        Player playerT = new Player("ciao2");

        playerK.getVisibleSymbols().put(Symbol.ANIMAL, 6);
        playerG.getVisibleSymbols().put(Symbol.QUILL, 4);
        playerT.getVisibleSymbols().put(Symbol.INK, 2);
        playerT.getVisibleSymbols().put(Symbol.MANUSCRIPT, 2);
        playerT.getVisibleSymbols().put(Symbol.QUILL, 3);

        assertEquals(4,symbolgoalK.calculatePoints(playerK));
        assertEquals(4,symbolgoalG.calculatePoints(playerG));
        assertEquals(4,symbolgoalT.calculatePoints(playerT));
    }
}
