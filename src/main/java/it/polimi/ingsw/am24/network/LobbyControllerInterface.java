package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.listeners.GameListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

//server stub, remote methods that clients can call
public interface LobbyControllerInterface extends Remote {
    GameControllerInterface joinGame(String nickname, int numPlayers, GameListener listener) throws RemoteException;
}
