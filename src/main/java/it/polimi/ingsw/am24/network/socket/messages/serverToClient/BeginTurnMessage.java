package it.polimi.ingsw.am24.network.socket.messages.serverToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelview.GameView;

import java.rmi.RemoteException;

/**
 * The {@code BeginTurnMessage} class represents a message sent from the server to the client
 * to indicate the beginning of a turn with the provided game view.
 */
public class BeginTurnMessage extends SocketServerMessage {
    /**
     * The game view associated with the beginning turn message.
     */
    private GameView gameView;

    /**
     * Constructs a {@code BeginTurnMessage} with the specified game view.
     *
     * @param gameView the game view indicating the state of the game
     */
    public BeginTurnMessage(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Executes the "begin turn" message on the client.
     * This method notifies the client listener about the beginning of a turn
     * with the provided game view.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.beginTurn(gameView);
    }
}
