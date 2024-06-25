package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.listeners.GameListener;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * The {@code SocketServerMessage} abstract class represents a message sent from a server to a client via sockets.
 * It implements {@code Serializable} to support serialization.
 */
public abstract class SocketServerMessage implements Serializable {
    //Messages sent from server to client
    /**
     * Executes the message operation on the client, interacting with a {@code GameListener}.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    public abstract void execute(GameListener listener) throws RemoteException;
}
