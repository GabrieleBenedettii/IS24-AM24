package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;

public class SentMessageMessage extends SocketServerMessage {
    private final String sender;
    private final String receiver;
    private final String message;
    private final String time;

    public SentMessageMessage(String sender, String receiver, String message, String time) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.sentMessage(sender, receiver, message, time);
    }
}
