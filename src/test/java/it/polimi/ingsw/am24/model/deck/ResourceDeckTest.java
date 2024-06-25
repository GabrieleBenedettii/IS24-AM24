package it.polimi.ingsw.am24.model.deck;

import it.polimi.ingsw.am24.exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class ResourceDeckTest {
    private ResourceDeck resourceDeck;

    @Before
    public void setUp() {
        resourceDeck = new ResourceDeck();
        resourceDeck.loadCards();
    }

    @Test
    public void testLoadCards() {
        assertNotNull(resourceDeck.getCards());
        assertTrue(resourceDeck.deckSize() > 0);
    }

    @Test
    public void testShuffle() {
        ArrayList<ResourceCard> originalCards = new ArrayList<>(resourceDeck.getCards());
        resourceDeck.shuffle();
        assertNotEquals(originalCards, resourceDeck.getCards());
    }

    @Test
    public void testDrawCard() {
        int initialSize = resourceDeck.deckSize();
        try {
            ResourceCard drawnCard = resourceDeck.drawCard();
            assertNotNull(drawnCard);
            assertEquals(initialSize - 1, resourceDeck.deckSize());
        } catch (EmptyDeckException e) {
            fail("Unexpected EmptyDeckException");
        }
    }

    @Test
    public void testDrawCardFromEmptyDeck() {
        // Clear the deck
        resourceDeck.getCards().clear();
        assertThrows(EmptyDeckException.class, () -> resourceDeck.drawCard());
    }

    @Test
    public void testGetFirstCardKingdom() {
        Kingdom firstCardKingdom = resourceDeck.getFirstCardKingdom();
        assertNotNull(firstCardKingdom);
    }

    @Test
    public void testGetFirstCardKingdomFromEmptyDeck() {
        // Clear the deck
        resourceDeck.getCards().clear();
        assertThrows(EmptyDeckException.class, () -> resourceDeck.drawCard());
    }

    @Test
    public void testGetCards() {
        assertNotNull(resourceDeck.getCards());
    }

    @Test
    public void testDeckSize() {
        assertEquals(resourceDeck.getCards().size(), resourceDeck.deckSize());
    }
}
