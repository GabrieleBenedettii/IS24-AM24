package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.messages.servetToClient.NoLobbyAvaiableMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameListenerClientSocket implements /*GameListener,*/ Serializable {
    //todo add all game listener methods
    private final ObjectOutputStream out;

    public GameListenerClientSocket(ObjectOutputStream out) {
        this.out = out;
    }

    //@Override
    public void noLobbyAvailable() throws RemoteException {
        try {
            out.writeObject(new NoLobbyAvaiableMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {

        }
    }
}
