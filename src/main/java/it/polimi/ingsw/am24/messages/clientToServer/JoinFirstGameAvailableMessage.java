package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.network.rmi.LobbyControllerInterface;

import java.rmi.RemoteException;

public class JoinFirstGameAvailableMessage extends SocketClientMessage {
    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return controller.joinGame(nickname, 1, listener);
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {

    }
}
