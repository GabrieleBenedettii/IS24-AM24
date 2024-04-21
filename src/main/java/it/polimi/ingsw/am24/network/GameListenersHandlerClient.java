package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.view.flow.Flow;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameListenersHandlerClient implements GameListener, Serializable {

    private Flow flow;

    public GameListenersHandlerClient(Flow flow) {
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
    public void nicknameAlreadyUsed() throws RemoteException {
        flow.nicknameAlreadyUsed();
    }

    @Override
    public void availableColors(ArrayList<String> colors) throws RemoteException {
        flow.availableColors(colors);
    }

    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews) throws RemoteException {
        flow.hiddenGoalChoice(cardViews);
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
}

