package it.polimi.ingsw.am24.controllerTest;

import it.polimi.ingsw.am24.Controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.*;

public class controllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller(4); // Numero di giocatori per il test
    }

    @Test
    void addNewPlayer() {
        assertTrue(controller.addNewPlayer("Player1", null));
        assertTrue(controller.addNewPlayer("Player2", null));
        assertTrue(controller.addNewPlayer("Player3", null));
        assertTrue(controller.addNewPlayer("Player4", null));
        assertFalse(controller.addNewPlayer("Player5", null)); // Testa se il gioco è iniziato dopo aver aggiunto tutti i giocatori
    }

    @Test
    void chooseColor() {
        controller.addNewPlayer("Player1", null);
        assertTrue(controller.chooseColor("Player1", "BLUE", null));
        assertFalse(controller.chooseColor("Player1", "BLUE", null)); // Testa se un giocatore può scegliere lo stesso colore più di una volta
    }

    @Test
    void startGame() {
        controller.addNewPlayer("Player1", null);
        controller.addNewPlayer("Player2", null);
        controller.addNewPlayer("Player3", null);
        controller.addNewPlayer("Player4", null);
        controller.startGame();
        // Assicurati che ogni giocatore abbia ricevuto le carte iniziali
        assertEquals(1, controller.getPlayer("Player1").getInitialcard().getId());
        assertEquals(1, controller.getPlayer("Player2").getInitialcard().getId());
        assertEquals(1, controller.getPlayer("Player3").getInitialcard().getId());
        assertEquals(1, controller.getPlayer("Player4").getInitialcard().getId());
    }

//    @Test
//    void chooseGoal() {
//        controller.addNewPlayer("Player1", null);
//        controller.startGame();
//        assertTrue(controller.chooseGoal("Player1", 1, null));
//    }

    @Test
    void chooseInitialCardSide() {
        controller.addNewPlayer("Player1", null);
        controller.startGame();
        assertTrue(controller.chooseInitialCardSide("Player1", true, null));
        // Aggiungi altri test per garantire che il giocatore abbia scelto il lato iniziale della carta corretto
    }

    @Test
    void playCard() {
        controller.addNewPlayer("Player1", null);
        controller.startGame();
        // Assicurati che un giocatore possa giocare una carta correttamente
        assertTrue(controller.playCard("Player1", 0, true, 0, 0, null));
        // Aggiungi altri test per coprire altri scenari di gioco
    }

    @Test
    void drawCard() {
        controller.addNewPlayer("Player1", null);
        controller.startGame();
        // Assicurati che un giocatore possa pescare una carta correttamente
        assertTrue(controller.drawCard("Player1", 0, null));
        // Aggiungi altri test per coprire altri scenari di gioco
    }

//    @Test
//    void nextPlayer() {
//        controller.addNewPlayer("Player1", null);
//        controller.addNewPlayer("Player2", null);
//        controller.addNewPlayer("Player3", null);
//        controller.addNewPlayer("Player4", null);
//        controller.startGame();
//        // Assicurati che il prossimo giocatore sia corretto dopo ogni turno
//        controller.nextPlayer();
//        assertEquals("Player2", controller.getCurrentPlayer());
//        controller.nextPlayer();
//        assertEquals("Player3", controller.getCurrentPlayer());
//        controller.nextPlayer();
//        assertEquals("Player4", controller.getCurrentPlayer());
//        controller.nextPlayer();
//        assertEquals("Player1", controller.getCurrentPlayer());
//    }

    @Test
    void getNumOfActivePlayers() {
        controller.addNewPlayer("Player1", null);
        controller.addNewPlayer("Player2", null);
        controller.addNewPlayer("Player3", null);
        assertEquals(3, controller.getNumOfActivePlayers()); // Controlla il numero corretto di giocatori attivi
    }
}
