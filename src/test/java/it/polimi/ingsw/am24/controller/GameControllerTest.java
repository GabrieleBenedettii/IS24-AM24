package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.Exceptions.*;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;
import it.polimi.ingsw.am24.view.flow.utility.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    private GameController controller;
    private GameListener gl;

    @BeforeEach
    void setUp() throws FullLobbyException, RemoteException {
        controller = new GameController(4);
        gl = new GameListener() {
            @Override
            public void invalidNumPlayers() throws RemoteException {}

            @Override
            public void playerJoined(ArrayList<String> players) throws RemoteException {}

            @Override
            public void noLobbyAvailable() throws RemoteException {}

            @Override
            public void nicknameAlreadyUsed() throws RemoteException {}

            @Override
            public void availableColors(ArrayList<String> colors) throws RemoteException {}

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
            public void sentMessage(String message) throws RemoteException {}
        };
        controller.addPlayer("p1",gl);
        controller.addPlayer("p2",gl);
        controller.addPlayer("p3",gl);
        controller.addPlayer("p4",gl);
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
        assertFalse(controller.chooseInitialCardSide("p5", true, gl));
        assertTrue(controller.chooseInitialCardSide("p1", true, gl));
    }

    @Test
    @DisplayName("Check the playing card phase")
    void playCard() throws RemoteException {
        controller.startGame();
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
        assertFalse(controller.playCard("p1", 2, true, 9, 19, gl));
        assertTrue(controller.playCard("p1", 0, true, 9, 19, gl));
        assertFalse(controller.playCard("p5", 0, true, 0, 0, gl));
    }

    @Test
    @DisplayName("Check the drawing card phase")
    void drawCard() throws RemoteException {
        controller.startGame();
        ArrayList<GoalCard> cards1 = controller.getGame().drawGoalCards();
        ArrayList<GoalCard> cards2 = controller.getGame().drawGoalCards();
        controller.getPlayer("p1").setHiddenGoal(cards1.get(0));
        controller.getPlayer("p2").setHiddenGoal(cards1.get(1));
        controller.getPlayer("p3").setHiddenGoal(cards2.get(0));
        controller.getPlayer("p4").setHiddenGoal(cards2.get(1));
        assertFalse(controller.drawCard("p5", 0, gl));
        assertTrue(controller.drawCard("p1", 0, gl));
        assertTrue(controller.drawCard("p2", 1, gl));
        assertTrue(controller.drawCard("p3", 2, gl));
        assertTrue(controller.drawCard("p4", 3, gl));
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
    void calculateMultipleWinner(){
        controller.getPlayer("p1").addPoints(20);
        controller.getPlayer("p2").addPoints(20);
        controller.getPlayer("p3").addPoints(20);

        assertEquals("p1,p2,p3",controller.calculateWinner());
    }

    @Test
    @DisplayName("Final phase")
    void finalPhase() throws RemoteException {
        controller.startGame();
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
        controller.playCard("p1",0, true, 9, 19, gl);
        assertEquals(GameStatus.LAST_LAST_ROUND, controller.getStatus());
        controller.drawCard("p1", 0, gl);

        while(controller.getStatus() == GameStatus.LAST_LAST_ROUND) {
            controller.playCard(controller.getCurrentPlayer(), 0, true, 9, 19, gl);
            controller.drawCard(controller.getCurrentPlayer(), 0, gl);
        }
        assertEquals(GameStatus.LAST_ROUND, controller.getStatus());

        while(controller.getStatus() == GameStatus.LAST_ROUND) {
            controller.playCard(controller.getCurrentPlayer(), 0, true, 11, 19, gl);
            controller.drawCard(controller.getCurrentPlayer(), 0, gl);
        }
        assertEquals(GameStatus.ENDED,controller.getStatus());
    }
}
