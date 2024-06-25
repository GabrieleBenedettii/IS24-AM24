package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;

import java.rmi.RemoteException;

/**
 * The {@code NicknameAlreadyUsedMessage} class represents a message sent from the server to the client
 * to notify about a nickname that is already in use.
 */
public class NicknameAlreadyUsedMessage extends SocketServerMessage {

    /**
     * Executes the nickname already used message on the client.
     * This method notifies the client listener that the chosen nickname is already in use.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.nicknameAlreadyUsed();
    }
}
