package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code DrawCardMessage} class represents a message sent from the client to the server
 * to draw a card.
 */
public class DrawCardMessage extends SocketClientMessage {
    private final int card;

    /**
     * Constructs a {@code DrawCardMessage} with the specified nickname and card ID.
     *
     * @param nickname the nickname of the player drawing the card
     * @param card     the ID of the card being drawn
     */
    public DrawCardMessage(String nickname, int card) {
        this.nickname = nickname;
        this.card = card;
    }

    /**
     * Returns the nickname of the player sending the message.
     *
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the ID of the card being drawn.
     *
     * @return the ID of the card
     */
    public int getCard() {
        return card;
    }

    /**
     * Executes the message operation on the server by drawing a card.
     *
     * @param listener   the game listener interface on the server
     * @param controller the lobby controller interface on the server
     * @return a game controller interface for further interaction with the game
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    /**
     * Executes the message operation on the client by requesting to draw a card.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.drawCard(nickname, card, listener);
    }
}
