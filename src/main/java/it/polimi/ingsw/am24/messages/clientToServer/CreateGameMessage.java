package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code CreateGameMessage} class represents a message sent from the client to the server
 * to create a new game.
 */
public class CreateGameMessage extends SocketClientMessage {
    int numPlayers;

    /**
     * Constructs a {@code CreateGameMessage} with the specified nickname and number of players.
     *
     * @param nickname   the nickname of the player creating the game
     * @param numPlayers the number of players to join the game
     */
    public CreateGameMessage(String nickname, int numPlayers) {
        this.nickname = nickname;
        this.numPlayers = numPlayers;
    }

    /**
     * Executes the message operation on the server by creating a new game.
     *
     * @param listener   the game listener interface on the server
     * @param controller the lobby controller interface on the server
     * @return a game controller interface for further interaction with the game
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return controller.joinGame(nickname, numPlayers, listener);
    }

    /**
     * Executes the message operation on the client (not implemented in this example).
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
    }

}
