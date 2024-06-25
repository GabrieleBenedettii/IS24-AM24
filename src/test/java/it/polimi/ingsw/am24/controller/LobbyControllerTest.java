package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyControllerTest {
    private LobbyController lobbyController;
    private GameListener gl;

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    @DisplayName("Check get instance")
    void getInstance(){
        assertEquals(LobbyController.getInstance(), lobbyController);
    }

    @Test
    @DisplayName("Check correct lobby creation")
    void checkLobbyCreation() throws RemoteException {
        GameControllerInterface gc1 = lobbyController.joinGame("p1",2,gl);
        GameControllerInterface gc2 = lobbyController.joinGame("p2",1,gl);
        assertNotNull(gc1);
        assertNotNull(gc2);
        assertSame(gc1, gc2);
    }

    @Test
    @DisplayName("Check not existing lobbies, first request")
    void noLobbiesStart() throws RemoteException {
        assertNull(lobbyController.joinGame("p1",1,gl));
    }

    @Test
    @DisplayName("Check not existing lobbies, during run")
    void noLobbiesRun() throws RemoteException {
        lobbyController.joinGame("p1",2,gl);
        lobbyController.joinGame("p2",1,gl);
        assertNull(lobbyController.joinGame("p3",1,gl));
    }

    @Test
    @DisplayName("Invalid number of players")
    void invalidNumPlayers() throws RemoteException {
        assertNull(lobbyController.joinGame("p1",5,gl));
    }

    @Test
    @DisplayName("Nickname already used")
    void nicknameAlreadyUsed() throws RemoteException {
        lobbyController.joinGame("p1",2,gl);
        assertNull(lobbyController.joinGame("p1",1,gl));
    }

    @Test
    @DisplayName("Empty nickname")
    void emptyNickname() throws RemoteException {
        assertNull(lobbyController.joinGame("",2,gl));
    }
}
