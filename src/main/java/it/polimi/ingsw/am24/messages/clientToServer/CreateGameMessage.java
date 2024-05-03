package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.network.rmi.LobbyControllerInterface;

import java.rmi.RemoteException;

public class CreateGameMessage extends SocketClientMessage {
    int numPlayers;

    public CreateGameMessage(String nickname, int numPlayers) {
        this.nickname = nickname;
        this.numPlayers = numPlayers;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return controller.joinGame(nickname, numPlayers, listener);
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
    }

}
