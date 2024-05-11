package it.polimi.ingsw.am24.network.rmi;

import it.polimi.ingsw.am24.listeners.GameListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

//server stub, remote methods that clients can call
public interface GameControllerInterface extends Remote {

    boolean chooseColor(String nickname, String color, GameListener listener) throws RemoteException;

    boolean chooseGoal(String nickname, int goalId, GameListener listener) throws RemoteException;

    boolean chooseInitialCardSide(String nickname, boolean front, GameListener listener) throws RemoteException;

    boolean playCard(String name, int cardIndex, boolean isFront, int x, int y, GameListener listener) throws RemoteException;

    boolean drawCard(String p, int cardIndex, GameListener listener) throws RemoteException;

    boolean sentPublicMessage(String sender, String message) throws RemoteException;

    boolean sentPrivateMessage(String sender, String receiver, String message) throws RemoteException;

    //int getNumOnlinePlayers() throws RemoteException;

    //void leave(String nickname, GameListener listener) throws RemoteException;
}
