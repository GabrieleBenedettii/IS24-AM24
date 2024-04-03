package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.card.InitialCard;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitialCardTest {
    @Test
    public void testGetters() {
        // Definisci i valori per il test
        int imageId = 1;
        Symbol[] symbols = {Symbol.ANIMAL, Symbol.PLANT};
        ArrayList<Kingdom> kingdoms = new ArrayList<>();
        kingdoms.add(Kingdom.PLANT);
        kingdoms.add(Kingdom.ANIMAL);

        // Crea una nuova InitialCard
        InitialCard initialCard = new InitialCard(imageId, symbols, kingdoms);

        // Verifica che i valori restituiti dai metodi getter corrispondano ai valori impostati nel costruttore
        assertEquals(imageId, initialCard.getImageId());
        //assertArrayEquals(symbols, initialCard.getCorners().toArray());
        assertEquals(kingdoms, initialCard.getKingdoms());
    }
}
