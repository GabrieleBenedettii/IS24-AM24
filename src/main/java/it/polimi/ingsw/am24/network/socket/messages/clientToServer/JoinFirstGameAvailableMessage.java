package it.polimi.ingsw.am24.network.socket.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code JoinFirstGameAvailableMessage} class represents a message sent from the client to join the first available game.
 * It is used when a client wants to join the first game available in the lobby.
 */
public class JoinFirstGameAvailableMessage extends SocketClientMessage {
    /**
     * Constructs a {@code JoinFirstGameAvailableMessage} with the specified nickname.
     *
     * @param nickname the nickname of the client wishing to join the first available game
     */
    public JoinFirstGameAvailableMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Executes the join first game available message on the server.
     * This method attempts to join the first available game in the lobby.
     *
     * @param listener   the game listener interface on the server
     * @param controller the lobby controller interface on the server
     * @return a game controller interface for further interaction with the game
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return controller.joinGame(nickname, 1, listener);
    }

    /**
     * Executes the join first game available message on the client.
     * This method is not implemented in this example.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {

    }
}
