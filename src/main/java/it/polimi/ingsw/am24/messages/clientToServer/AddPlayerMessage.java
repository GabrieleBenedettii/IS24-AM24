package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;

/**
 * The {@code AddPlayerMessage} class represents a message sent from the server to a client to notify
 * the addition of a player.
 */
public class AddPlayerMessage extends SocketServerMessage {
    private final String nickname;
    private final int numPlayers;  //num = [2;4] => first player, num = 1 => other

    /**
     * Constructs an {@code AddPlayerMessage} with the specified nickname and number of players.
     *
     * @param username   the nickname of the player being added
     * @param numPlayers the number of players currently in the game
     */
    public AddPlayerMessage(String username, int numPlayers) {
        this.nickname = username;
        this.numPlayers = numPlayers;
    }

    /**
     * Returns the nickname of the player being added.
     *
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Executes the message operation on the client, notifying the listener about the added player.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        //listener.addedPlayer(player) todo add addedPlayer method in listener
    }
}
