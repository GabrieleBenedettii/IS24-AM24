package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MultipleGamesTest {
    private LobbyController lobbyController;
    private GameListener gl;

    @BeforeEach
    void setUp() {
        lobbyController = new LobbyController();
        gl = new GameListener() {
            @Override
            public void invalidNumPlayers() throws RemoteException {
            }

            @Override
            public void playerJoined(ArrayList<String> players, String current, int num) throws RemoteException {
            }

            @Override
            public void noLobbyAvailable() throws RemoteException {
            }

            @Override
            public void nicknameAlreadyUsed() throws RemoteException {
            }

            @Override
            public void availableColors(ArrayList<String> colors) throws RemoteException {
            }

            @Override
            public void notAvailableColors(ArrayList<String> colors) throws RemoteException {
            }

            @Override
            public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) throws RemoteException {
            }

            @Override
            public void initialCardSide(GameCardView front, GameCardView back) throws RemoteException {
            }

            @Override
            public void beginTurn(GameView gameView) throws RemoteException {
            }

            @Override
            public void invalidPositioning() throws RemoteException {
            }

            @Override
            public void requirementsNotMet() throws RemoteException {
            }

            @Override
            public void beginDraw(GameView gameView) throws RemoteException {
            }

            @Override
            public void wrongCardPlay(GameView gameView) throws RemoteException {
            }

            @Override
            public void gameEnded(String winner, HashMap<String, Integer> rank) throws RemoteException {
            }

            @Override
            public void sentMessage(String sender, String receiver, String message, String time) throws RemoteException {
            }
        };
    }

    @Test
    @DisplayName("Create different game")
    void createDifferentGame() throws RemoteException {
        assertNotEquals(lobbyController.joinGame("p1",4, gl), lobbyController.joinGame("p2",3, gl));
    }

    @Test
    @DisplayName("Check the colors in different games")
    void checkColorsInDifferentGames() throws RemoteException {
        GameController controller1 = (GameController) lobbyController.joinGame("p1",4, gl);
        controller1.chooseColor("p1", "RED", gl);
        GameController controller2 = (GameController) lobbyController.joinGame("p2",3, gl);
        assertFalse(controller2.chooseColor("p1","RED", gl));
        assertTrue(controller2.chooseColor("p2","RED", gl));
    }
}
