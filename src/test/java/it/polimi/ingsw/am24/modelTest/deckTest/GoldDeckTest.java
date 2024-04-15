package it.polimi.ingsw.am24.modelTest.deckTest;

import it.polimi.ingsw.am24.model.deck.GoldDeck;
import it.polimi.ingsw.am24.model.card.GoldCard;
import org.junit.Before;
import org.junit.Test;

public class GoldDeckTest {
    private GoldDeck goldDeck;
    @Before
    public void setUp() {
        goldDeck = new GoldDeck();
        goldDeck.loadCards();
    }
    @Test
    public void testLoadCards(){
        assert(!goldDeck.getCards().isEmpty());
        assert(!goldDeck.getCards().contains(null));
    }

    @Test
    public void testShuffle() {
        GoldCard firstCardBeforeShuffle = goldDeck.getCards().get(0);
        goldDeck.shuffle();
        GoldCard firstCardAfterShuffle = goldDeck.getCards().get(0);
        assert(!firstCardBeforeShuffle.equals(firstCardAfterShuffle));
    }
    @Test
    public void testDrawCard() {
        int initialSize = goldDeck.getCards().size();
        GoldCard drawnCard = goldDeck.drawCard();
        assert(!drawnCard.equals(null));
        assert(initialSize - 1 == goldDeck.getCards().size());
    }

}

