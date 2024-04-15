package it.polimi.ingsw.am24.modelTest.cardTest;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GoldCard;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import it.polimi.ingsw.am24.model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceCardTest {
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

    public void testPrintCard() {
        // Definisci i requisiti della carta
        int imageId = 1;
        Symbol[] symbols = {Symbol.ANIMAL, Symbol.PLANT};
        Kingdom kingdom = Kingdom.PLANT;
        int points = 0;

        // Crea una nuova carta d'oro
        ResourceCard resourceCard = new ResourceCard(imageId, symbols, kingdom, points);        String text = new String();
        text=" ";
        assertEquals(text,resourceCard.printCard());
    }
}
