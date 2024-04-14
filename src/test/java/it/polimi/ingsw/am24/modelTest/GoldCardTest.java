package it.polimi.ingsw.am24.modelTest;

import java.util.HashMap;
import java.util.Map;
import it.polimi.ingsw.am24.model.card.GoldCard;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoldCardTest {
    @Test
    public void testCheckRequirementsMet_RequirementsMet() {
        // Definisci i simboli visibili
        HashMap<Symbol, Integer> visibleSymbols = new HashMap<>();
        visibleSymbols.put(Symbol.ANIMAL, 3);
        visibleSymbols.put(Symbol.FUNGI, 3);

        // Definisci i requisiti della carta
        Map<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.ANIMAL, 2);
        requirements.put(Symbol.FUNGI, 1);

        // Crea una nuova carta d'oro
        GoldCard goldCard = new GoldCard(1, new Symbol[]{Symbol.ANIMAL, Symbol.PLANT}, Kingdom.PLANT, 2, false, Symbol.PLANT, requirements);

        // Verifica che i requisiti siano soddisfatti
        goldCard.checkRequirementsMet(visibleSymbols);
        assertTrue(goldCard.isRequirementsMet());
    }

    @Test
    public void testCheckRequirementsMet_RequirementsNotMet() {
        // Definisci i simboli visibili
        HashMap<Symbol, Integer> visibleSymbols = new HashMap<>();
        visibleSymbols.put(Symbol.ANIMAL, 3);
        visibleSymbols.put(Symbol.FUNGI, 1);

        // Definisci i requisiti della carta
        Map<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI, 5);

        // Crea una nuova carta d'oro
        GoldCard goldCard = new GoldCard(1, new Symbol[]{Symbol.ANIMAL, Symbol.PLANT}, Kingdom.PLANT, 5, false, Symbol.PLANT, requirements);

        // Verifica che i requisiti non siano soddisfatti
        goldCard.checkRequirementsMet(visibleSymbols);
        assertFalse(goldCard.isRequirementsMet());
    }
    @Test
    public void constructorTest(){
        Map<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI, 5);
        GoldCard goldCard = new GoldCard(1, new Symbol[]{Symbol.ANIMAL, Symbol.PLANT}, Kingdom.PLANT, 5, false, Symbol.PLANT, requirements);
        assertEquals(1, goldCard.getImageId());
        assertEquals(Kingdom.PLANT, goldCard.getKingdom());
        assertEquals(goldCard.getPoints(), 5);
        assertEquals(goldCard.getRequirements(), requirements);
        assertFalse(goldCard.getPointsForCoveringCorners());
        assertEquals(goldCard.getCoveringSymbol(), Symbol.PLANT);
    }

    public void testPrintCard() {
        // Definisci i requisiti della carta
        Map<Symbol, Integer> requirements = new HashMap<>();
        requirements.put(Symbol.FUNGI, 5);

        // Crea una nuova carta d'oro
        GoldCard goldCard = new GoldCard(1, new Symbol[]{Symbol.ANIMAL, Symbol.PLANT}, Kingdom.PLANT, 5, false, Symbol.PLANT, requirements);
        String text = new String();
        text="Kingdom: \nCorners: ";
        assertEquals(text,goldCard.printCard());
    }
}
