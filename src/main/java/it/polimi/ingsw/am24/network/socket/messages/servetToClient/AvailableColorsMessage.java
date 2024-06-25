package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The {@code AvailableColorsMessage} class represents a message sent from the server to the client
 * to provide a list of available colors for selection.
 */
public class AvailableColorsMessage extends SocketServerMessage {
    /**
     * The list of available colors provided in the message.
     */
    public final ArrayList<String> colors;

    /**
     * Constructs an {@code AvailableColorsMessage} with the specified list of colors.
     *
     * @param colors the list of available colors
     */
    public AvailableColorsMessage(ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Executes the available colors message on the client.
     * This method notifies the client listener about the available colors.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.availableColors(colors);
    }
}
