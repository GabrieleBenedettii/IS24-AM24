package it.polimi.ingsw.am24.modelTest.GoalTest;

import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.model.goal.GoalDeck;
import org.junit.Before;
import org.junit.Test;

public class GoalDeckTest {
    private GoalDeck goalDeck;
    @Before
    public void setUp() {
        goalDeck = new GoalDeck();
        goalDeck.loadCards();
    }
    @Test
    public void testLoadCards(){
        assert(!goalDeck.getCards().isEmpty());
        assert(!goalDeck.getCards().contains(null));
    }

    @Test
    public void testShuffle() {
        GoalCard firstCardBeforeShuffle = goalDeck.getCards().get(0);
        goalDeck.shuffle();
        GoalCard firstCardAfterShuffle = goalDeck.getCards().get(0);
        assert(!firstCardBeforeShuffle.equals(firstCardAfterShuffle));
    }
    @Test
    public void testDrawCard() {
        int initialSize = goalDeck.getCards().size();
        GoalCard drawnCard = goalDeck.drawCard();
        assert(!drawnCard.equals(null));
        assert(initialSize - 1 == goalDeck.getCards().size());
    }
}
