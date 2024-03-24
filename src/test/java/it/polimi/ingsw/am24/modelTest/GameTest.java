package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game game = new Game();

//    @Test
//    public void testStart() { //da rivedere in seguito alla divisione del goaldeck
//        // Inizia il gioco
//        game.start();
//
//        // Verifica che i mazzi siano stati inizializzati correttamente e le carte visibili siano state pescate
//        assertNotNull(game.getResourceDeck());
//        assertNotNull(game.getGoldDeck());
//        assertNotNull(game.getInitialDeck());
//        //assertNotNull(game.getGoalDeck());
//        assertEquals(2, game.getVisibleResCard().size());
//        assertEquals(2, game.getVisibleGoldCard().size());
//        assertEquals(2, game.getCommonGoal().size());
//
//        // Verifica che ogni giocatore abbia ricevuto le carte iniziali, gli obiettivi e la mano iniziale
//        ArrayList<Player> players = game.getPlayers();
//        for (Player player : players) {
//            assertNotNull(player.getInitialcard());
//            assertNotNull(player.getGoals());
//            assertEquals(3, player.getPlayingHand().size());
//        }
//    }

    @Test
    public void testAddFirstPlayer() {
        Player player = new Player("Player1", PlayerColor.RED);
        game.addFirstPlayer(player, 2);

        // Verifica che il giocatore sia stato aggiunto alla lista dei giocatori e il numero di giocatori sia stato impostato correttamente
        assertEquals(1, game.getPlayers().size());
        assertEquals(2, game.getNumPlayers());
    }

    @Test
    public void testAddNewPlayer() {
        // Aggiungi il primo giocatore
        Player player1 = new Player("Player1", PlayerColor.RED);
        game.addFirstPlayer(player1, 3);

        // Aggiungi un nuovo giocatore
        Player player2 = new Player("Player2", PlayerColor.BLUE);
        game.addNewPlayer(player2);

        // Verifica che il nuovo giocatore sia stato aggiunto alla lista dei giocatori
        assertEquals(2, game.getPlayers().size());

        // Prova ad aggiungere un terzo giocatore (il numero massimo di giocatori è 2)
        Player player3 = new Player("Player3", PlayerColor.GREEN);
        game.addNewPlayer(player3);

        // Verifica che il terzo giocatore non sia stato aggiunto
        assertEquals(3, game.getPlayers().size());
    }

    @Test
    public void testNextPlayer() {
        // Aggiungi alcuni giocatori
        Player player1 = new Player("Player1", PlayerColor.RED);
        Player player2 = new Player("Player2", PlayerColor.BLUE);
        Player player3 = new Player("Player3", PlayerColor.GREEN);
        game.addFirstPlayer(player1, 3);
        game.addNewPlayer(player2);
        game.addNewPlayer(player3);

        // Imposta il giocatore corrente al primo giocatore aggiunto
        game.setCurrentPlayer(player1);

        // Verifica che il prossimo giocatore sia correttamente selezionato
        assertEquals(player2, game.nextPlayer());

        // Imposta il giocatore corrente all'ultimo giocatore aggiunto
        game.setCurrentPlayer(player3);

        // Verifica che il prossimo giocatore sia il primo giocatore aggiunto
        assertEquals(player1, game.nextPlayer());
    }
}
