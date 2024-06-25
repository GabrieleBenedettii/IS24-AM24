package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code SendPrivateMessageMessage} class represents a message sent from the client to send a private message
 * to another user.
 */
public class SendPrivateMessageMessage extends SocketClientMessage {

    private final String reciever;
    private final String message;

    /**
     * Constructs a {@code SendPrivateMessageMessage} with the specified sender, receiver, and message content.
     *
     * @param sender   the nickname of the client sending the private message
     * @param receiver the nickname of the client receiving the private message
     * @param message  the content of the private message
     */
    public SendPrivateMessageMessage(String sender, String receiver, String message) {
        this.nickname = sender;
        this.reciever = receiver;
        this.message = message;
    }

    /**
     * Executes the send private message on the server.
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
     * Executes the send private message on the client.
     * This method delegates the sending action to the game controller.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.sentPrivateMessage(nickname,reciever,message);
    }
}
