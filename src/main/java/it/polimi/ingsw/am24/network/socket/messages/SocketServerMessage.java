package it.polimi.ingsw.am24.network.socket.messages;

import it.polimi.ingsw.am24.listeners.GameListener;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class SocketServerMessage implements Serializable {
    //Messages sent from server to client

    public abstract void execute(GameListener listener) throws RemoteException;
}
