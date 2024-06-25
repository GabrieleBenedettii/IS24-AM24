package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class PlayerJoinedMessage extends SocketServerMessage {
    private final ArrayList<String> players;
    private final String current;
    private final int num;

    public PlayerJoinedMessage(ArrayList<String> players, String current,int num) {
        this.players=players;
        this.current = current;
        this.num = num;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.playerJoined(players,current, num);
    }
}
