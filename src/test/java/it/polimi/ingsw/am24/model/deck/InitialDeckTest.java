package it.polimi.ingsw.am24.model.deck;

import it.polimi.ingsw.am24.model.card.InitialCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.testng.AssertJUnit.*;

public class InitialDeckTest {
    private InitialDeck initialDeck;

    @Before
    public void setUp() {
        initialDeck = new InitialDeck();
        initialDeck.loadCards();
    }

    @Test
    public void testLoadCards() {
        assertNotNull(initialDeck.getCards());
        assertTrue(initialDeck.getCards().size() > 0);
    }

    @Test
    public void testShuffle() {
        ArrayList<InitialCard> originalCards = new ArrayList<>(initialDeck.getCards());
        initialDeck.shuffle();
        assertNotEquals(originalCards, initialDeck.getCards());
    }

    @Test
    public void testDrawCard() {
        int initialSize = initialDeck.getCards().size();
        InitialCard drawnCard = initialDeck.drawCard();
        assertNotNull(drawnCard);
        assertEquals(initialSize - 1, initialDeck.getCards().size());
    }

    @Test
    public void testGetCards() {
        assertNotNull(initialDeck.getCards());
    }
}

