package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;

/**
 * The {@code RequirementsNotMetMessage} class represents a message sent from the server to the client
 * to notify that the requirements for an action or condition were not met.
 */
public class RequirementsNotMetMessage extends SocketServerMessage {
    /**
     * Executes the requirements not met message on the client.
     * This method notifies the client listener that the requirements for an action or condition were not met.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.requirementsNotMet();
    }
}
