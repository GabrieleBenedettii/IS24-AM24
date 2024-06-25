package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

public class SendPublicMessageMessage extends SocketClientMessage {

    private final String message;
    public SendPublicMessageMessage(String sender, String message) {
        this.nickname = sender;
        this.message = message;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.sentPublicMessage(nickname,message);
    }
}
