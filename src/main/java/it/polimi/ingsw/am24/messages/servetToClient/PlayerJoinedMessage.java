package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class PlayerJoinedMessage extends SocketServerMessage {
    private final ArrayList<String> players;

    public PlayerJoinedMessage(ArrayList<String> players) {
        this.players=players;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.playerJoined(players);
    }
}
