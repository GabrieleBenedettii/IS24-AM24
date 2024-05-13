package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.deck.ResourceDeck;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceCardTest {
    private ResourceCard card;

    @Before
    public void setUp(){
        ResourceDeck deck = new ResourceDeck();
        deck.loadCards();
        card = deck.getCards().getFirst();
    }
    @Test
    public void testGetters() {
        // Definisci i valori per il test
        int imageId = 1;
        Symbol[] symbols = {Symbol.ANIMAL, Symbol.PLANT};
        Kingdom kingdom = Kingdom.PLANT;
        int points = 0;
        // Crea una nuova carta risorsa
        ResourceCard resourceCard = new ResourceCard(imageId, symbols, kingdom, points);
        // Verifica i valori restituiti dai metodi getter
        assertEquals(imageId, resourceCard.getImageId());
        //assertArrayEquals(symbols, resourceCard.getCorners().toArray()); funziona ma bisogna scandire in modo diverso l'array dei corner
        assertEquals(kingdom, resourceCard.getKingdom());
        assertEquals(points, resourceCard.getPoints());
    }

    @Test
    public void testPrintCard() {
        String expected ="Kingdom: " + Constants.getText(Kingdom.FUNGI) +  "\n\tCorners: " +
                Constants.getText(Symbol.FUNGI) + " " + Constants.EMPTY + " " + Constants.getText(Symbol.FUNGI) + " " + Constants.EMPTY + " " + "\n\tPoints: 0";
        assertEquals(expected, card.printCard());
    }
}
