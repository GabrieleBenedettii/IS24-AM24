package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code HeartBeatMessage} class represents a heartbeat message sent from the client to the server.
 * It is used to indicate the client's presence and maintain the connection.
 */
public class HeartBeatMessage extends SocketClientMessage {
    private final String nickname;

    /**
     * Constructs a {@code HeartBeatMessage} with the specified nickname.
     *
     * @param nickname the nickname of the client sending the heartbeat message
     */
    public HeartBeatMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Executes the heartbeat message on the server.
     * This method is typically used to handle the client's presence and maintain the connection.
     *
     * @param listener   the game listener interface on the server
     * @param controller the lobby controller interface on the server
     * @return a game controller interface for further interaction with the game
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    /**
     * Executes the heartbeat message on the client.
     * This method is typically used to notify the server of the client's presence.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        if(controller != null)
            controller.heartbeat(nickname, listener);
    }
}
