package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.network.rmi.LobbyControllerInterface;
import it.polimi.ingsw.am24.network.socket.GameListenerClientSocket;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class SocketClientMessage implements Serializable {
    //Messages sent from client to server
    protected String nickname;
    public abstract GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException;
    public abstract void execute(GameListener listener, GameControllerInterface controller) throws RemoteException;
}
