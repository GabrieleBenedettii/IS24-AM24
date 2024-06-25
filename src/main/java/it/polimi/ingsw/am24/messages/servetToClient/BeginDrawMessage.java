package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelView.GameView;

import java.rmi.RemoteException;

/**
 * The {@code BeginDrawMessage} class represents a message sent from the server to the client
 * to indicate the beginning of a draw phase with the provided game view.
 */
public class BeginDrawMessage extends SocketServerMessage {
    /**
     * The game view associated with the beginning draw message.
     */
    private final GameView gameView;

    /**
     * Constructs a {@code BeginDrawMessage} with the specified game view.
     *
     * @param gameView the game view indicating the state of the game
     */
    public BeginDrawMessage(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Executes the "begin draw" message on the client.
     * This method notifies the client listener about the beginning of the draw phase
     * with the provided game view.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.beginDraw(gameView);
    }
}
