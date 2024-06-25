package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * The {@code GameEndedMessage} class represents a message sent from the server to the client
 * to indicate that the game has ended, providing information about the winner and rank.
 */
public class GameEndedMessage extends SocketServerMessage {

    /**
     * The nickname of the winner of the game.
     */
    private final String winner;

    /**
     * A map containing player nicknames as keys and their ranks as values.
     */
    private final HashMap<String,Integer> rank;

    /**
     * Constructs a {@code GameEndedMessage} with the winner's nickname and the rank of all players.
     *
     * @param winner the nickname of the winner of the game
     * @param rank   a map containing player nicknames as keys and their ranks as values
     */
    public GameEndedMessage(String winner,HashMap<String,Integer> rank) {
        this.winner = winner;
        this.rank = rank;
    }

    /**
     * Executes the game ended message on the client.
     * This method notifies the client listener that the game has ended,
     * providing the winner's nickname and the rank of all players.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.gameEnded(winner,rank);
    }
}
