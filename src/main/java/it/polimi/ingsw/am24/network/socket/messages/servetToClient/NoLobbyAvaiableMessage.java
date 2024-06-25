package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;

import java.rmi.RemoteException;

public class NoLobbyAvaiableMessage extends SocketServerMessage {

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.noLobbyAvailable();
    }
}
