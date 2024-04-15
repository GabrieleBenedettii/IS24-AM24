package it.polimi.ingsw.am24.modelTest.deckTest;

import it.polimi.ingsw.am24.model.deck.ResourceDeck;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import org.junit.Before;
import org.junit.Test;

public class ResourceDeckTest {
    private ResourceDeck resourceDeck;
    @Before
    public void setUp() {
        resourceDeck = new ResourceDeck();
        resourceDeck.loadCards();
    }
    @Test
    public void testLoadCards(){
        assert(!resourceDeck.getCards().isEmpty());
        assert(!resourceDeck.getCards().contains(null));
    }

    @Test
    public void testShuffle() {
        ResourceCard firstCardBeforeShuffle = resourceDeck.getCards().get(0);
        resourceDeck.shuffle();
        ResourceCard firstCardAfterShuffle = resourceDeck.getCards().get(0);
        assert(!firstCardBeforeShuffle.equals(firstCardAfterShuffle));
    }
    @Test
    public void testDrawCard() {
        int initialSize = resourceDeck.getCards().size();
        ResourceCard drawnCard = resourceDeck.drawCard();
        assert(!drawnCard.equals(null));
        assert(initialSize - 1 == resourceDeck.getCards().size());
    }
}
