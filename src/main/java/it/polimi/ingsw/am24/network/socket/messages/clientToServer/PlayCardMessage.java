package it.polimi.ingsw.am24.network.socket.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code PlayCardMessage} class represents a message sent from the client to play a card on the game board.
 * It contains information about the card to be played, its orientation, and the position on the game board.
 */
public class PlayCardMessage extends SocketClientMessage {
    private final boolean isFront;
    private final int cardIndex;
    private final int x;
    private final int y;

    /**
     * Constructs a {@code PlayCardMessage} with the specified nickname, card index, front status,
     * and position coordinates.
     *
     * @param nickname  the nickname of the client playing the card
     * @param cardIndex the index of the card to be played
     * @param isFront   {@code true} if playing the front side of the card, {@code false} if playing the back side
     * @param x         the x-coordinate of the position on the game board to play the card
     * @param y         the y-coordinate of the position on the game board to play the card
     */
    public PlayCardMessage(String nickname, int cardIndex, boolean isFront, int x, int y) {
        this.nickname = nickname;
        this.isFront = isFront;
        this.cardIndex = cardIndex;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the nickname of the client playing the card.
     *
     * @return the nickname of the client
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns whether the front side of the card is being played.
     *
     * @return {@code true} if playing the front side of the card, {@code false} if playing the back side
     */
    public boolean getFront() {
        return isFront;
    }

    /**
     * Returns the x-coordinate of the position on the game board to play the card.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the position on the game board to play the card.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Executes the play card message on the server.
     * This method delegates the play action to the game controller.
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
     * Executes the play card message on the client.
     * This method delegates the play action to the game controller.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.playCard(nickname, cardIndex, isFront, x, y, listener);
    }
}
