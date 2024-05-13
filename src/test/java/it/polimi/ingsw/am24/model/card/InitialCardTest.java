package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.deck.InitialDeck;
import it.polimi.ingsw.am24.modelView.GameCardView;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.runners.model.MultipleFailureException.assertEmpty;
import static org.testng.AssertJUnit.assertTrue;

public class InitialCardTest {
    InitialCard card;
    @Before
    public void setUp(){
        InitialDeck deck = new InitialDeck();
        deck.loadCards();
        card = deck.getCards().getFirst();
    }
    @Test
    public void testGetters() {
        // Verifica che i valori restituiti dai metodi getter corrispondano ai valori impostati nel costruttore
        assertEquals(0, card.getImageId());
        assertTrue(card.getKingdoms().isEmpty());
    }

    @Test
    public void testPrintCard(){
        String expected ="FRONT\n\tKingdoms: " + "\n\tCorners: " +
                Constants.getText(Symbol.FUNGI) + " " + Constants.getText(Symbol.PLANT) + " " + Constants.getText(Symbol.INSECT) + " "
                + Constants.getText(Symbol.ANIMAL) + " ";
        assertEquals(expected, card.printCard());
    }

    @Test
    public void testPrintBackCard(){
        String expected ="BACK\n\tKingdoms: " + Constants.getText(Kingdom.INSECT) + " " + "\n\tCorners: " +
                Constants.EMPTY + " " + Constants.getText(Symbol.PLANT) + " " + Constants.getText(Symbol.INSECT) + " "
                + Constants.EMPTY + " ";
        assertEquals(expected, card.printBackCard());
    }

    @Test
    public void testGetFrontView(){
        GameCardView actualView = card.getView();
        String expected ="FRONT\n\tKingdoms: " + "\n\tCorners: " +
                Constants.getText(Symbol.FUNGI) + " " + Constants.getText(Symbol.PLANT) + " " + Constants.getText(Symbol.INSECT) + " "
                + Constants.getText(Symbol.ANIMAL) + " ";
        GameCardView expectedView = new GameCardView("Initial Card - front", 0, expected);
        assertEquals(expectedView.getCardType(), actualView.getCardType());
        assertEquals(expectedView.getCardId(), actualView.getCardId());
        assertEquals(expectedView.getCardDescription(), actualView.getCardDescription());
    }

    @Test
    public void testGetBackView(){
        GameCardView actualView = card.getBackView();
        String expected ="BACK\n\tKingdoms: " + Constants.getText(Kingdom.INSECT) + " " + "\n\tCorners: " +
                Constants.EMPTY + " " + Constants.getText(Symbol.PLANT) + " " + Constants.getText(Symbol.INSECT) + " "
                + Constants.EMPTY + " ";
        GameCardView expectedView = new GameCardView("Initial Card - back", 0, expected);
        assertEquals(expectedView.getCardType(), actualView.getCardType());
        assertEquals(expectedView.getCardId(), actualView.getCardId());
        assertEquals(expectedView.getCardDescription(), actualView.getCardDescription());
    }
}
