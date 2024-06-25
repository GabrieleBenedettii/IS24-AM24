package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;

/**
 * The {@code NoLobbyAvailableMessage} class represents a message sent from the server to the client
 * to notify about the unavailability of any lobby to join.
 */
public class NoLobbyAvailableMessage extends SocketServerMessage {

    /**
     * Executes the no lobby available message on the client.
     * This method notifies the client listener that there are no lobbies available to join.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.noLobbyAvailable();
    }
}
