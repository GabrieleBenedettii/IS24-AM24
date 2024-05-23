package it.polimi.ingsw.am24.listeners;

import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface GameListener extends Remote {

    void invalidNumPlayers() throws RemoteException;

    void playerJoined(ArrayList<String> players, String current, int num) throws RemoteException;

    void noLobbyAvailable() throws RemoteException;

    void nicknameAlreadyUsed() throws RemoteException;

    void availableColors(ArrayList<String> colors) throws RemoteException;
    void notAvailableColors(ArrayList<String> colors) throws RemoteException;

    void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) throws RemoteException;

    void initialCardSide(GameCardView front, GameCardView back) throws RemoteException;

    void beginTurn(GameView gameView) throws RemoteException;

    void invalidPositioning() throws RemoteException;

    void requirementsNotMet() throws RemoteException;

    void beginDraw(GameView gameView) throws RemoteException;

    void wrongCardPlay(GameView gameView) throws RemoteException;

    void gameEnded(String winner, HashMap<String,Integer> rank) throws RemoteException;

    void sentMessage(String message) throws RemoteException;
}
