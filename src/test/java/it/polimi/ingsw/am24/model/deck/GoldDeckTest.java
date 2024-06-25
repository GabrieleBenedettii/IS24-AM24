package it.polimi.ingsw.am24.model.deck;

import it.polimi.ingsw.am24.exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GoldCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class GoldDeckTest {
    private GoldDeck goldDeck;

    @Before
    public void setUp() {
        goldDeck = new GoldDeck();
        goldDeck.loadCards();
    }

    @Test
    public void testLoadCards() {
        assertNotNull(goldDeck.getCards());
        assertTrue(goldDeck.deckSize() > 0);
    }

    @Test
    public void testShuffle() {
        ArrayList<GoldCard> originalCards = new ArrayList<>(goldDeck.getCards());
        goldDeck.shuffle();
        assertNotEquals(originalCards, goldDeck.getCards());
    }

    @Test
    public void testDrawCard() {
        int initialSize = goldDeck.deckSize();
        try {
            GoldCard drawnCard = goldDeck.drawCard();
            assertNotNull(drawnCard);
            assertEquals(initialSize - 1, goldDeck.deckSize());
        } catch (EmptyDeckException e) {
            fail("Unexpected EmptyDeckException");
        }
    }

    @Test
    public void testDrawCardFromEmptyDeck() {
        // Clear the deck
        goldDeck.getCards().clear();
        assertThrows(EmptyDeckException.class, () -> goldDeck.drawCard());
    }

    @Test
    public void testGetFirstCardKingdom() {
        Kingdom firstCardKingdom = goldDeck.getFirstCardKingdom();
        assertNotNull(firstCardKingdom);
    }

    @Test
    public void testGetFirstCardKingdomFromEmptyDeck() {
        goldDeck.getCards().clear();
        assertThrows(EmptyDeckException.class, () -> goldDeck.drawCard());
    }
}

