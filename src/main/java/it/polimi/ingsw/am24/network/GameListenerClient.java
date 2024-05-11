package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;
import it.polimi.ingsw.am24.view.flow.Flow;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameListenerClient implements GameListener, Serializable {

    private final Flow flow;

    public GameListenerClient(Flow flow) {
        this.flow = flow;
    }

    @Override
    public void invalidNumPlayers() throws RemoteException {
        flow.invalidNumPlayers();
    }

    @Override
    public void playerJoined(ArrayList<String> players) throws RemoteException {
        flow.playerJoined(players);
    }

    @Override
    public void noLobbyAvailable() throws RemoteException {
        flow.noLobbyAvailable();
    }

    @Override
    public void nicknameAlreadyUsed() throws RemoteException {
        flow.nicknameAlreadyUsed();
    }

    @Override
    public void availableColors(ArrayList<String> colors) throws RemoteException {
        flow.availableColors(colors);
    }

    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, PublicBoardView publicBoardView) throws RemoteException {
        flow.hiddenGoalChoice(cardViews, publicBoardView);
    }

    @Override
    public void initialCardSide(GameCardView front, GameCardView back) throws RemoteException {
        flow.initialCardSide(front, back);
    }

    @Override
    public void beginTurn(GameView gameView) throws RemoteException {
        flow.beginTurn(gameView);
    }

    @Override
    public void invalidPositioning() throws RemoteException {
        flow.invalidPositioning();
    }

    @Override
    public void requirementsNotMet() throws RemoteException {
        flow.requirementsNotMet();
    }

    @Override
    public void beginDraw() throws RemoteException {
        flow.beginDraw();
    }

    @Override
    public void wrongCardPlay(GameView gameView) throws RemoteException {
        flow.wrongCardPlay(gameView);
    }

    @Override
    public void gameEnded(String winner, HashMap<String,Integer> rank) throws RemoteException {
        flow.gameEnded(winner, rank);
    }

    @Override
    public void sentMessage(String message) throws RemoteException {
        flow.sentMessage(message);
    }
}

