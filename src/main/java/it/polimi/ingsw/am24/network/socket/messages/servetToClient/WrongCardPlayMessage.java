package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelview.GameView;

import java.rmi.RemoteException;

/**
 * The {@code WrongCardPlayMessage} class represents a message sent from the server to the client
 * to notify that a player attempted an invalid card play.
 */
public class WrongCardPlayMessage extends SocketServerMessage {

    private final GameView gameView;

    /**
     * Constructs a new {@code WrongCardPlayMessage} with the specified game view.
     *
     * @param gameView the game view associated with the wrong card play
     */
    public WrongCardPlayMessage(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Executes the wrong card play notification on the client.
     * This method notifies the client listener that a player attempted an invalid card play
     * by invoking the {@code wrongCardPlay} method on the listener interface.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.wrongCardPlay(gameView);
    }
}
