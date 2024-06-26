package it.polimi.ingsw.am24.network.socket.messages.serverToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The {@code PlayerJoinedMessage} class represents a message sent from the server to the client
 * to notify that a player has joined the game.
 */
public class PlayerJoinedMessage extends SocketServerMessage {
    /**
     * The list of players in the game.
     */
    private final ArrayList<String> players;
    /**
     * The nickname of the current player.
     */
    private final String current;
    /**
     * The total number of players in the game.
     */
    private final int num;

    /**
     * Constructs a {@code PlayerJoinedMessage} with the specified list of players, current player nickname,
     * and total number of players.
     *
     * @param players the list of players in the game
     * @param current the nickname of the current player who joined
     * @param num     the total number of players in the game
     */
    public PlayerJoinedMessage(ArrayList<String> players, String current,int num) {
        this.players=players;
        this.current = current;
        this.num = num;
    }

    /**
     * Executes the player joined message on the client.
     * This method notifies the client listener that a player has joined the game, passing the list of players,
     * current player nickname, and total number of players.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.playerJoined(players,current, num);
    }
}
