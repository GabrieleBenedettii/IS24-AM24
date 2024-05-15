package it.polimi.ingsw.am24.model.card;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.deck.GoldDeck;
import it.polimi.ingsw.am24.modelView.GameCardView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoldCardTest {
    private GoldCard card1, card2;
    private final HashMap<Symbol, Integer> visibleSymbols = new HashMap<>();
    @Before
    public void setUp(){
        GoldDeck deck = new GoldDeck();
        deck.loadCards();
        card1 = deck.getCards().getFirst();
        card2 = deck.getCards().get(1);
        visibleSymbols.put(Symbol.ANIMAL, 3);
        visibleSymbols.put(Symbol.FUNGI, 3);
        visibleSymbols.put(Symbol.PLANT, 0);
        visibleSymbols.put(Symbol.INK, 0);
        visibleSymbols.put(Symbol.QUILL, 0);
        visibleSymbols.put(Symbol.MANUSCRIPT, 0);
        visibleSymbols.put(Symbol.INSECT, 0);

    }
    @Test
    public void testCheckRequirementsMet_RequirementsMet() {

        assertTrue(card1.checkRequirementsMet(visibleSymbols));
        assertFalse(card2.checkRequirementsMet(visibleSymbols));
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

    @Test
    public void testPrintCard() {
        String expected ="Kingdom: " + Constants.getText(Kingdom.FUNGI) +  "\n\tCorners: " +
                Constants.HIDDEN + " " + Constants.EMPTY + " " + Constants.EMPTY + " " + Constants.getText(Symbol.QUILL) + " " +
                "\n\tRequirements: " + Constants.getText(Symbol.FUNGI) + " -> 2 " + Constants.getText(Symbol.ANIMAL) + " -> 1 " +
                "\n\tPoints: 1 points for each QUILL";
        assertEquals(expected, card1.printCard());
    }

    @Test
    public void testGetView() {
        String desc = "Kingdom: " + Constants.getText(Kingdom.FUNGI) +  "\n\tCorners: " +
                Constants.HIDDEN + " " + Constants.EMPTY + " " + Constants.EMPTY + " " + Constants.getText(Symbol.QUILL) + " " +
                "\n\tRequirements: " + Constants.getText(Symbol.FUNGI) + " -> 2 " + Constants.getText(Symbol.ANIMAL) + " -> 1 " +
                "\n\tPoints: 1 points for each QUILL";
        GameCardView Expected = new GameCardView("Gold Card", 0, desc);
        GameCardView cardView = card1.getView();
        assertEquals(Expected.getCardType(), cardView.getCardType());
        assertEquals(Expected.getCardId(), cardView.getCardId());
        assertEquals(Expected.getCardDescription(), cardView.getCardDescription());
    }

    @Test
    public void testGetType(){
        String desc = "gold";
        assertEquals(desc, card1.getType());
    }
}
