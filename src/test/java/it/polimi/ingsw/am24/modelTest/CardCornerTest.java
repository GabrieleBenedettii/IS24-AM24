package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.card.CardCorner;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import org.junit.jupiter.api.Test;
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

        // Imposta una carta coperta e una carta che copre
        GameCard coveredCard = new ResourceCard("front1", "back1", new Symbol[]{Symbol.ANIMAL}, Kingdom.PLANT, 3);
        GameCard coveringCard = new ResourceCard("front2", "back2", new Symbol[]{Symbol.PLANT}, Kingdom.ANIMAL, 5);
        cardCorner.setCoveredCard(coveredCard);
        cardCorner.setCoveringCard(coveringCard);

        // Verifica che le carte coperte e che coprono siano state impostate correttamente
        assertEquals(coveredCard, cardCorner.getCoveredCard());
        assertEquals(coveringCard, cardCorner.getCoveringCard());
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
