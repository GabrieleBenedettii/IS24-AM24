package it.polimi.ingsw.am24.network.socket.messages.serverToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;

import java.rmi.RemoteException;

/**
 * The {@code SentMessageMessage} class represents a message sent from the server to the client
 * to notify that a message has been successfully sent from one user to another.
 */
public class SentMessageMessage extends SocketServerMessage {
    private final String sender;
    private final String receiver;
    private final String message;
    private final String time;

    /**
     * Constructs a new {@code SentMessageMessage} with the specified sender, receiver, message, and time.
     *
     * @param sender   the sender of the message
     * @param receiver the receiver of the message
     * @param message  the content of the message
     * @param time     the time when the message was sent
     */
    public SentMessageMessage(String sender, String receiver, String message, String time) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
    }

    /**
     * Executes the "sent message" notification on the client.
     * This method notifies the client listener that a message has been successfully sent from one user to another.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.sentMessage(sender, receiver, message, time);
    }
}
