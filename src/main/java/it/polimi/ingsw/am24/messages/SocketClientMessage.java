package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.clientToServer.CreateGameMessage;
import it.polimi.ingsw.am24.messages.clientToServer.JoinFirstGameAvailableMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class SocketClientMessage implements Serializable {
    //Messages sent from client to server
    protected String nickname;
    public abstract GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException;
    public abstract void execute(GameListener listener, GameControllerInterface controller) throws RemoteException;

    public boolean isForLobbyController() {
        return this instanceof CreateGameMessage || this instanceof JoinFirstGameAvailableMessage;
    }
}
