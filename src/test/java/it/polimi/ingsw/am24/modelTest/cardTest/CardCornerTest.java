package it.polimi.ingsw.am24.modelTest.cardTest;

import it.polimi.ingsw.am24.model.card.CardCorner;
import it.polimi.ingsw.am24.model.Symbol;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardCornerTest {
    @Test
    public void testGettersAndSetters() {
        // Crea un oggetto CardCorner per il test
        Symbol symbol = Symbol.ANIMAL;
        boolean hidden = false;
        CardCorner cardCorner = new CardCorner(symbol, hidden);

        // Verifica che i valori restituiti dai metodi getter corrispondano ai valori impostati nel costruttore
        assertEquals(symbol, cardCorner.getSymbol());
        assertEquals(hidden, cardCorner.isHidden());

        // Imposto l'angolo coperto
        cardCorner.coverCorner();

        // Verifica che le carte coperte e che coprono siano state impostate correttamente
        assertTrue( cardCorner.isCovered());
    }
    @Test
    public void testGetCornerText() {
        // Testa il metodo getCornerText() con una carta visibile
        CardCorner visibleCorner = new CardCorner(Symbol.ANIMAL, false);
        assertEquals('A', visibleCorner.getCornerText());

        // Testa il metodo getCornerText() con una carta coperta
        CardCorner coveredCorner = new CardCorner(Symbol.ANIMAL, true);
        assertEquals(' ', coveredCorner.getCornerText());

        // Testa il metodo getCornerText() con una carta nascosta
        CardCorner hiddenCorner = new CardCorner(null, false);
        assertEquals('*', hiddenCorner.getCornerText());
    }

}
