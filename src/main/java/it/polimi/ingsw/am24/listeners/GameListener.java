package it.polimi.ingsw.am24.listeners;

import it.polimi.ingsw.am24.messages.Message;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface GameListener extends Remote {

    void invalidNumPlayers() throws RemoteException;

    void playerJoined(ArrayList<String> players) throws RemoteException;

    void noLobbyAvailable() throws RemoteException;

    void nicknameAlreadyUsed() throws RemoteException;

   // void genericErrorWhenEnteringGame(String why) throws RemoteException;

    void availableColors(ArrayList<String> colors) throws RemoteException;

    void hiddenGoalChoice(ArrayList<GameCardView> cardViews) throws RemoteException;

    void initialCardSide(GameCardView front, GameCardView back) throws RemoteException;

    void beginTurn(GameView gameView) throws RemoteException;

    void invalidPositioning() throws RemoteException;

    void requirementsNotMet() throws RemoteException;

    void beginDraw() throws RemoteException;

    void wrongCardPlay(GameView gameView) throws RemoteException;

    void gameEnded(String winner, HashMap<String,Integer> rank) throws RemoteException;

    //void sentMessage(ChatMessage msg) throws RemoteException;
}
