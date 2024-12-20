package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.exceptions.*;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.view.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    private LobbyController lobbyController;
    private GameController controller;
    private GameListener gl;

    @BeforeEach
    void setUp() throws FullLobbyException, RemoteException {
        lobbyController = new LobbyController();
        gl = new GameListener() {
            @Override
            public void invalidNumPlayers() throws RemoteException {}

            @Override
            public void playerJoined(ArrayList<String> players, String current, int num) throws RemoteException {}

            @Override
            public void noLobbyAvailable() throws RemoteException {}

            @Override
            public void nicknameAlreadyUsed() throws RemoteException {}

            @Override
            public void availableColors(ArrayList<String> colors) throws RemoteException {}

            @Override
            public void notAvailableColors(ArrayList<String> colors) throws RemoteException {}

            @Override
            public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) throws RemoteException {}

            @Override
            public void initialCardSide(GameCardView front, GameCardView back) throws RemoteException {}

            @Override
            public void beginTurn(GameView gameView) throws RemoteException {}

            @Override
            public void invalidPositioning() throws RemoteException {}

            @Override
            public void requirementsNotMet() throws RemoteException {}

            @Override
            public void beginDraw(GameView gameView) throws RemoteException {}

            @Override
            public void wrongCardPlay(GameView gameView) throws RemoteException {}

            @Override
            public void gameEnded(String winner, HashMap<String, Integer> rank) throws RemoteException {}

            @Override
            public void sentMessage(String sender, String receiver, String message, String time) throws RemoteException {}
        };
        controller = (GameController) lobbyController.joinGame("p1",4,gl);
        controller = (GameController) lobbyController.joinGame("p2",1,gl);
        controller = (GameController) lobbyController.joinGame("p3",1,gl);
        controller = (GameController) lobbyController.joinGame("p4",1,gl);
    }

    @Test
    @DisplayName("Try to add a new player in a full lobby")
    void addNewPlayer() {
        assertThrows(FullLobbyException.class, () -> controller.addPlayer("p5", gl));
    }

    @Test
    @DisplayName("Two players choose the same color")
    void chooseColor() throws RemoteException {
        assertTrue(controller.chooseColor("p1", "BLUE", gl));
        assertFalse(controller.chooseColor("p2", "BLUE", gl));
    }

    @Test
    @DisplayName("Choose a color with a wrong name")
    void chooseColorWrongName() throws RemoteException {
        assertFalse(controller.chooseColor("p5", "BLUE", gl));
    }

    @Test
    @DisplayName("Check if all the players get all the initial cards")
    void startGame() {
        controller.startGame();
        assertNotNull(controller.getPlayer("p1").getInitialcard());
        assertNotNull(controller.getPlayer("p2").getInitialcard());
        assertNotNull(controller.getPlayer("p3").getInitialcard());
        assertNotNull(controller.getPlayer("p4").getInitialcard());

        assertEquals(3, controller.getPlayer("p1").getPlayingHand().size());
        assertEquals(3, controller.getPlayer("p2").getPlayingHand().size());
        assertEquals(3, controller.getPlayer("p3").getPlayingHand().size());
        assertEquals(3, controller.getPlayer("p4").getPlayingHand().size());
    }

    @Test
    @DisplayName("A player tries to choose a wrong hidden goal")
    void chooseGoal() throws RemoteException {
        controller.startGame();
        controller.getGame().drawGoalCards();
        assertFalse(controller.chooseGoal("p5", 50, gl));
        assertFalse(controller.chooseGoal("p1", 50, gl));
    }

    @Test
    @DisplayName("A player chooses the initial card side")
    void chooseInitialCardSide() throws RemoteException {
        controller.startGame();
        controller.chooseColor("p1", "BLUE", gl);
        assertFalse(controller.chooseInitialCardSide("p5", true, gl));
        assertTrue(controller.chooseInitialCardSide("p1", true, gl));
    }

    @Test
    @DisplayName("Check the playing card phase")
    void playCard() throws RemoteException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);
        assertFalse(controller.playCard("p1", 0, true, 0, 0, gl));
        assertFalse(controller.playCard("p1", 2, true, 49, 49, gl));
        assertTrue(controller.playCard("p1", 0, true, 49, 49, gl));
        assertEquals(2, controller.getPlayer("p1").getPlayingHand().size());
        assertFalse(controller.playCard("p5", 0, true, 0, 0, gl));
    }

    @Test
    @DisplayName("Check the drawing card phase")
    void drawCard() throws RemoteException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);
        controller.playCard("p1", 0, true, 49, 49, gl);
        controller.playCard("p2", 0, true, 49, 49, gl);
        controller.playCard("p3", 0, true, 49, 49, gl);
        controller.playCard("p4", 0, true, 49, 49, gl);
        assertFalse(controller.drawCard("p5", 0, gl));
        assertTrue(controller.drawCard("p1", 0, gl));
        assertTrue(controller.drawCard("p2", 1, gl));
        assertTrue(controller.drawCard("p3", 2, gl));
        assertTrue(controller.drawCard("p4", 3, gl));
    }

    @Test
    @DisplayName("Check that a player can't draw twice")
    void drawCardTwice() throws RemoteException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);
        controller.playCard("p1", 0, true, 49, 49, gl);
        controller.drawCard("p1",5, gl);
        assertFalse(controller.drawCard("p1",5, gl));
    }

    @Test
    @DisplayName("Check the drawing card phase if the deck is empty")
    void drawCardEmptyDeck() throws EmptyDeckException, RemoteException {
        controller.startGame();
        while(controller.getGame().resDeckSize() > 0) controller.getGame().drawResourceCard();
        assertFalse(controller.drawCard("p1", 4, gl));
    }

    @Test
    @DisplayName("Check the players rotation")
    void rotationTest() throws RemoteException {
        String first = "p1";
        controller.setCurrentPlayer(first);
        controller.nextPlayer();
        String second = controller.getCurrentPlayer();
        assertNotEquals(first, second);
        controller.nextPlayer();
        String third = controller.getCurrentPlayer();
        assertNotEquals(first, third);
        assertNotEquals(second, third);
        controller.nextPlayer();
        String fourth = controller.getCurrentPlayer();
        assertNotEquals(first, fourth);
        assertNotEquals(second, fourth);
        assertNotEquals(third, fourth);
        controller.nextPlayer();
        assertEquals(first,controller.getCurrentPlayer());
    }

    @Test
    @DisplayName("Check the num of active players is correct")
    void getNumOfActivePlayers() {
        assertEquals(4, controller.getNumOfPlayers());
    }

    @Test
    @DisplayName("Check public chat messages")
    void publicChatMessages() throws RemoteException {
        assertFalse(controller.sentPublicMessage("p5", "Hello"));
        assertTrue(controller.sentPublicMessage("p1", "Hello"));
    }

    @Test
    @DisplayName("Check private chat messages")
    void privateChatMessages() throws RemoteException {
        assertFalse(controller.sentPrivateMessage("p5", "p1","Hello"));
        assertTrue(controller.sentPrivateMessage("p1", "p2", "Hello"));
    }

    @Test
    @DisplayName("Check game id")
    void checkGameId(){
        assertNotEquals(0,controller.getGameId());
    }

    @Test
    @DisplayName("Check single winner")
    void calculateSingleWinner(){
        controller.getPlayer("p1").addPoints(5);
        controller.getPlayer("p2").addPoints(6);
        controller.getPlayer("p3").addPoints(7);

        assertEquals("p3",controller.calculateWinner());
    }

    @Test
    @DisplayName("Check multiple winners")
    void calculateMultipleWinner() throws RemoteException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.getPlayer("p1").addPoints(20);
        controller.getPlayer("p2").addPoints(20);
        controller.getPlayer("p3").addPoints(20);

        assertEquals("p1,p2,p3",controller.calculateWinner());
    }

    @Test
    @DisplayName("Final phase")
    void finalPhase() throws RemoteException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);

        controller.setCurrentPlayer("p1");
        controller.getPlayer("p1").addPoints(20);
        controller.playCard("p1",0, true, 49, 49, gl);
        assertEquals(GameStatus.LAST_LAST_ROUND, controller.getStatus());
        controller.drawCard("p1", 0, gl);

        while(controller.getStatus() == GameStatus.LAST_LAST_ROUND) {
            controller.playCard(controller.getCurrentPlayer(), 0, true, 49, 49, gl);
            controller.drawCard(controller.getCurrentPlayer(), 0, gl);
        }
        assertEquals(GameStatus.LAST_ROUND, controller.getStatus());
        while(controller.getStatus() == GameStatus.LAST_ROUND) {
            controller.playCard(controller.getCurrentPlayer(), 0, true, 51, 49, gl);
            controller.drawCard(controller.getCurrentPlayer(), 0, gl);
        }
        assertEquals(GameStatus.ENDED,controller.getStatus());
    }

    @Test
    @DisplayName("Disconnect a player, remains more than 1 player")
    void disconnectAPlayer() throws RemoteException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);

        assertDoesNotThrow(() -> controller.disconnectPlayer("p1"));
        assertNull(controller.getPlayer("p1"));
    }

    @Test
    @DisplayName("Disconnection of the current player")
    void disconnectCurrentPlayer() throws RemoteException, NotExistingPlayerException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);

        controller.setCurrentPlayer("p1");
        controller.disconnectPlayer("p1");

        assertNotEquals(controller.getCurrentPlayer(),"p1");
    }

    @Test
    @DisplayName("Check that next player method doesn't work when there aren't players left")
    void NoPlayerNext() throws RemoteException, NotExistingPlayerException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.disconnectPlayer("p1");
        controller.disconnectPlayer("p2");
        controller.disconnectPlayer("p3");
        controller.disconnectPlayer("p4");

        assertDoesNotThrow(() -> controller.nextPlayer());
    }

    @Test
    @DisplayName("Remains only 1 player left in first phase")
    void disconnectAllPlayersFirstPhase() throws RemoteException, NotExistingPlayerException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.disconnectPlayer("p1");
        controller.disconnectPlayer("p2");
        controller.disconnectPlayer("p3");

        assertEquals(GameStatus.ENDED,controller.getStatus());
    }

    @Test
    @DisplayName("Remains only 1 player left in running phase")
    void disconnectAllPlayersRunningPhase() throws RemoteException, NotExistingPlayerException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);

        controller.disconnectPlayer("p1");
        controller.disconnectPlayer("p2");
        controller.disconnectPlayer("p3");

        assertEquals(GameStatus.ENDED,controller.getStatus());
    }

    @Test
    @DisplayName("Remains only 1 player left in running phase in the next to last round")
    void disconnectAllPlayersNextToLastRound() throws RemoteException, NotExistingPlayerException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);
        controller.setCurrentPlayer("p1");
        controller.getPlayer("p1").addPoints(20);
        controller.playCard("p1",0, true, 49, 49, gl);

        controller.disconnectPlayer("p1");
        controller.disconnectPlayer("p2");
        controller.disconnectPlayer("p3");

        assertEquals(GameStatus.ENDED,controller.getStatus());
    }

    @Test
    @DisplayName("Remains only 1 player left in running phase in the last round")
    void disconnectAllPlayersLastRound() throws RemoteException, NotExistingPlayerException {
        controller.chooseColor("p1", "BLUE", gl);
        controller.chooseColor("p2", "RED", gl);
        controller.chooseColor("p3", "YELLOW", gl);
        controller.chooseColor("p4", "GREEN", gl);
        controller.chooseInitialCardSide("p1", true, gl);
        controller.chooseInitialCardSide("p2", true, gl);
        controller.chooseInitialCardSide("p3", true, gl);
        controller.chooseInitialCardSide("p4", true, gl);
        List<Integer> goalsIds = controller.getGame().getDrawnGoalCardsIds();
        controller.chooseGoal("p1",goalsIds.get(0),gl);
        controller.chooseGoal("p2",goalsIds.get(1),gl);
        controller.chooseGoal("p3",goalsIds.get(2),gl);
        controller.chooseGoal("p4",goalsIds.get(3),gl);
        controller.setCurrentPlayer("p1");
        controller.getPlayer("p1").addPoints(20);
        controller.playCard("p1",0, true, 49, 49, gl);
        controller.drawCard("p1", 0, gl);

        while(controller.getStatus() == GameStatus.LAST_LAST_ROUND) {
            controller.playCard(controller.getCurrentPlayer(), 0, true, 49, 49, gl);
            controller.drawCard(controller.getCurrentPlayer(), 0, gl);
        }

        controller.disconnectPlayer("p1");
        controller.disconnectPlayer("p2");
        controller.disconnectPlayer("p3");

        assertEquals(GameStatus.ENDED,controller.getStatus());
    }

    @Test
    @DisplayName("HeartBeat adding")
    void heartbeatAdding() throws RemoteException {
        controller.heartbeat("p1",gl);
    }
}
