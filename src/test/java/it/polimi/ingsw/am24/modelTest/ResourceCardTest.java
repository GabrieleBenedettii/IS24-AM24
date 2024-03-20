package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import it.polimi.ingsw.am24.model.Symbol;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;

public class ResourceCardTest {
    @Test
    public void testGetters() {
        // Definisci i valori per il test
        String frontImage = "front";
        String backImage = "back";
        Symbol[] symbols = {Symbol.ANIMAL, Symbol.PLANT};
        Kingdom kingdom = Kingdom.PLANT;
        int points = 0;
        // Crea una nuova carta risorsa
        ResourceCard resourceCard = new ResourceCard(frontImage, backImage, symbols, kingdom, points);
        // Verifica i valori restituiti dai metodi getter
        assertEquals(frontImage, resourceCard.getFrontImage());
        assertEquals(backImage, resourceCard.getBackImage());
        //assertArrayEquals(symbols, resourceCard.getCorners().toArray()); funziona ma bisogna scandire in modo diverso l'array dei corner
        assertEquals(kingdom, resourceCard.getKingdom());
        assertEquals(points, resourceCard.getPoints());
    }
}
