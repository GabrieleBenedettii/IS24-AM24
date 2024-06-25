package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.exceptions.WrongHiddenGoalException;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class GameTest {

    Game game = new Game();
    @Test
    public void testStart() {
        // Inizia il gioco
        game.start();
        assertEquals(38, game.resDeckSize());
        assertEquals(38, game.goldDeckSize());

    }

    @Test
    public void ColorTest(){                      //controllo i metodi chooseColor e IsAvailable
        game.chooseColor(PlayerColor.RED);
        assertEquals(3, game.getAvailableColors().size());
        assertFalse(game.isAvailable(PlayerColor.RED));
        assertTrue(game.isAvailable(PlayerColor.BLUE));
    }

    @Test
    public void drawTest() throws EmptyDeckException, WrongHiddenGoalException { //controllo che tutte le pescate e le aggiunte alle carte visibili siano correte
        game.start();
        assertNotNull(game.drawInitialCard());
        assertNotNull(game.drawResourceCard());
        assertNotNull(game.drawGoldCard());
        assertNotNull(game.drawnResCard(0));
        assertNotNull(game.drawnGoldCard(0));
        assertNotNull(game.drawGoalCards());
        game.addGoldCard();
        game.addResourceCard();
        assertNotNull(game.drawnResCard(0));
        assertNotNull(game.drawnGoldCard(0));
        ArrayList<GoalCard> cards = game.drawGoalCards();
        assertNotNull(cards.get(0));
        assertNotNull(cards.get(1));
        assertEquals(cards.get(0),game.chosenGoalCard(cards.get(0).getImageId()));
        assertEquals(cards.get(1),game.chosenGoalCard(cards.get(1).getImageId()));
        assertThrows(WrongHiddenGoalException.class, () -> game.chosenGoalCard(2));

    }

    @Test
    public  void emptyDrawTest() throws EmptyDeckException {
        game.start();
        while(game.resDeckSize()>0 && game.goldDeckSize()>0){
            game.drawResourceCard();
            game.drawGoldCard();
        }
        assertThrows(EmptyDeckException.class, () -> game.drawResourceCard());
        assertThrows(EmptyDeckException.class, () -> game.drawGoldCard());
    }

    @Test
    public void getPublicBoardViewTest() {
        game.start();
        assertNotNull(game.getCommonBoardView());
    }

}
