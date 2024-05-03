package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;

public class SentMessageMessage extends SocketServerMessage {
    private final String message;

    public SentMessageMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.sentMessage(message);
    }
}
