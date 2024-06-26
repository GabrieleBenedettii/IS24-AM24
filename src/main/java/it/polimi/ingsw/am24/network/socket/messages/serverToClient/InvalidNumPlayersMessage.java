package it.polimi.ingsw.am24.network.socket.messages.serverToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;

import java.rmi.RemoteException;

/**
 * The {@code InvalidNumPlayersMessage} class represents a message sent from the server to the client
 * to notify about an invalid number of players.
 */
public class InvalidNumPlayersMessage extends SocketServerMessage {

    /**
     * Executes the invalid number of players message on the client.
     * This method notifies the client listener about the invalid number of players.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.invalidNumPlayers();
    }
}
