package it.polimi.ingsw.am24.model.goal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GoalDeckTest {
    private GoalDeck goalDeck;
    @BeforeEach
    public void setUp() {
        goalDeck = new GoalDeck();
        goalDeck.loadCards();
    }
    @Test
    @DisplayName("Correct goal cards loading")
    public void testLoadCards(){
        assertFalse(goalDeck.getCards().isEmpty());
        assertFalse(goalDeck.getCards().contains(null));
        assertEquals(16,goalDeck.getCards().size()); //4 oblique, 4 vertical, 8 symbols
    }

    @Test
    @DisplayName("Correct goal cards shuffle")
    public void testShuffle() {
        int sizeBeforeShuffle = goalDeck.getCards().size();

        goalDeck.shuffle();

        int sizeAfterShuffle = goalDeck.getCards().size();

        assertEquals(sizeBeforeShuffle,sizeAfterShuffle);
    }
    @Test
    @DisplayName("Correct goal cards draw")
    public void testDrawCard() {
        int initialSize = goalDeck.getCards().size();

        GoalCard drawnCard = goalDeck.drawCard();

        assertNotNull(drawnCard);
        assertEquals(initialSize-1,goalDeck.getCards().size());
    }
}
