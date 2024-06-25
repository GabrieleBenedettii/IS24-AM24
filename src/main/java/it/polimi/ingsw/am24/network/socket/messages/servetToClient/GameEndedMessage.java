package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;

import java.rmi.RemoteException;
import java.util.HashMap;

public class GameEndedMessage extends SocketServerMessage {
    private final String winner;
    private final HashMap<String,Integer> rank;

    public GameEndedMessage(String winner,HashMap<String,Integer> rank) {
        this.winner = winner;
        this.rank = rank;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.gameEnded(winner,rank);
    }
}
