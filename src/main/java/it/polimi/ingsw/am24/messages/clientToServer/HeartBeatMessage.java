package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.network.rmi.LobbyControllerInterface;

import java.rmi.RemoteException;

public class HeartBeatMessage extends SocketClientMessage {
    private String nickname;

    public HeartBeatMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        if(controller != null)
            controller.heartbeat(nickname, listener);
    }
}
