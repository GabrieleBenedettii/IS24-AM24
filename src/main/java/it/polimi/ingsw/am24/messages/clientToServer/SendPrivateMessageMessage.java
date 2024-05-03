package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.network.rmi.LobbyControllerInterface;

import java.rmi.RemoteException;

public class SendPrivateMessageMessage extends SocketClientMessage {

    private final String reciever;
    private final String message;
    public SendPrivateMessageMessage(String sender, String reciever, String message) {
        this.nickname = sender;
        this.reciever = reciever;
        this.message = message;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.sentPrivateMessage(nickname,reciever,message);
    }
}
