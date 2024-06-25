package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The {@code NotAvailableColorMessage} class represents a message sent from the server to the client
 * to notify about unavailable color choices.
 */
public class NotAvailableColorMessage extends SocketServerMessage {
    /**
     * The list of unavailable color choices.
     */
    public final ArrayList<String> colors;

    /**
     * Constructs a {@code NotAvailableColorMessage} with the specified list of unavailable colors.
     *
     * @param colors the list of unavailable color choices
     */
    public NotAvailableColorMessage(ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Executes the not available colors message on the client.
     * This method notifies the client listener about the colors that are not available for selection.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.notAvailableColors(colors);
    }
}
