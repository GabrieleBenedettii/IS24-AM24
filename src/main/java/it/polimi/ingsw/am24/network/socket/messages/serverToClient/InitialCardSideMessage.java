package it.polimi.ingsw.am24.network.socket.messages.serverToClient;


import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelview.GameCardView;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The {@code InitialCardSideMessage} class represents a message sent from the server to the client
 * to convey the views of an initial card's front and back sides.
 */
public class InitialCardSideMessage extends SocketServerMessage {
    /**
     * The list of game card views representing the front and back sides of the initial card.
     */
    private final ArrayList<GameCardView> views = new ArrayList<>();

    /**
     * Constructs an {@code InitialCardSideMessage} with the views of the front and back sides of an initial card.
     *
     * @param front the game card view representing the front side of the initial card
     * @param back  the game card view representing the back side of the initial card
     */
    public InitialCardSideMessage(GameCardView front, GameCardView back) {
        this.views.add(front);
        this.views.add(back);
    }

    /**
     * Executes the initial card side message on the client.
     * This method notifies the client listener about the views of the initial card's front and back sides.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.initialCardSide(views.get(0),views.get(1));
    }
}
