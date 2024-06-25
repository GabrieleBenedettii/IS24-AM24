package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code SendPublicMessageMessage} class represents a message sent from the client to send a public message
 * to all users in the lobby or game.
 */
public class SendPublicMessageMessage extends SocketClientMessage {

    private final String message;

    /**
     * Constructs a {@code SendPublicMessageMessage} with the specified sender and message content.
     *
     * @param sender  the nickname of the client sending the public message
     * @param message the content of the public message
     */
    public SendPublicMessageMessage(String sender, String message) {
        this.nickname = sender;
        this.message = message;
    }

    /**
     * Executes the send public message on the server.
     * This method delegates the sending action to the game controller.
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
     * Executes the send public message on the client.
     * This method delegates the sending action to the game controller.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.sentPublicMessage(nickname,message);
    }
}
