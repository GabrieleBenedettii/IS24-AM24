package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.deck.InitialDeck;
import it.polimi.ingsw.am24.model.card.InitialCard;
import org.junit.Before;
import org.junit.Test;

public class InitialDeckTest {
    private InitialDeck initialDeck;
    @Before
    public void setUp() {
        initialDeck = new InitialDeck();
        initialDeck.loadCards();
    }
    @Test
    public void testLoadCards(){
        assert(!initialDeck.getCards().isEmpty());
        assert(!initialDeck.getCards().contains(null));
    }

    @Test
    public void testShuffle() {
        InitialCard firstCardBeforeShuffle = initialDeck.getCards().get(0);
        initialDeck.shuffle();
        InitialCard firstCardAfterShuffle = initialDeck.getCards().get(0);
        assert(!firstCardBeforeShuffle.equals(firstCardAfterShuffle));
    }
    @Test
    public void testDrawCard() {
        int initialSize = initialDeck.getCards().size();
        InitialCard drawnCard = initialDeck.drawCard();
        assert(!drawnCard.equals(null));
        assert(initialSize - 1 == initialDeck.getCards().size());
    }
}

