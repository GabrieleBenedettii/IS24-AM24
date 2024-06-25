package it.polimi.ingsw.am24.network.socket.messages;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.clientToServer.CreateGameMessage;
import it.polimi.ingsw.am24.network.socket.messages.clientToServer.JoinFirstGameAvailableMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * The {@code SocketClientMessage} abstract class represents a message sent from a client to a server via sockets.
 * It implements {@code Serializable} to support serialization.
 */
public abstract class SocketClientMessage implements Serializable {
    /** The nickname associated with the message. */
    protected String nickname;

    /**
     * Executes the message operation on the server, interacting with a {@code GameListener} and {@code LobbyControllerInterface}.
     *
     * @param listener the game listener interface on the server
     * @param controller the lobby controller interface on the server
     * @return a {@code GameControllerInterface} related to the executed operation
     * @throws RemoteException if there is a communication-related exception during execution
     */
    public abstract GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException;

    /**
     * Executes the message operation on the server, interacting with a {@code GameListener} and {@code GameControllerInterface}.
     *
     * @param listener the game listener interface on the server
     * @param controller the game controller interface on the server
     * @throws RemoteException if there is a communication-related exception during execution
     */
    public abstract void execute(GameListener listener, GameControllerInterface controller) throws RemoteException;

    /**
     * Checks if the message is intended for a {@code LobbyController}.
     *
     * @return {@code true} if the message is for a {@code LobbyController}; {@code false} otherwise
     */
    public boolean isForLobbyController() {
        return this instanceof CreateGameMessage || this instanceof JoinFirstGameAvailableMessage;
    }
}
