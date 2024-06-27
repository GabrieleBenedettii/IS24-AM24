package it.polimi.ingsw.am24.view;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Interface representing the actions that a client can perform in the game.
 */
public interface ClientActions {
    /**
     * Creates a new game with the specified nickname and number of players.
     *
     * @param nickname   the nickname of the player creating the game
     * @param numPlayers the number of players for the game
     * @throws IOException        if an I/O error occurs
     * @throws NotBoundException  if the game service is not bound
     */
    void createGame(String nickname, int numPlayers) throws IOException, NotBoundException;

    /**
     * Joins the first game available with the specified nickname.
     *
     * @param nickname the nickname of the player joining the game
     * @throws IOException        if an I/O error occurs
     * @throws NotBoundException  if the game service is not bound
     */
    void joinFirstGameAvailable(String nickname) throws IOException, NotBoundException;

    /**
     * Chooses a color for the player with the specified nickname.
     *
     * @param nickname the nickname of the player choosing the color
     * @param color    the color chosen by the player
     * @throws IOException if an I/O error occurs
     */
    void chooseColor(String nickname, String color) throws IOException;

    /**
     * Chooses a hidden goal for the player with the specified nickname.
     *
     * @param nickname the nickname of the player choosing the hidden goal
     * @param choice   the index of the hidden goal chosen by the player
     * @throws IOException if an I/O error occurs
     */
    void chooseHiddenGoal(String nickname, int choice) throws IOException;

    /**
     * Chooses the initial card side for the player with the specified nickname.
     *
     * @param nickname the nickname of the player choosing the card side
     * @param choice   the index of the card side chosen by the player
     * @throws IOException if an I/O error occurs
     */
    void chooseInitialCardSide(String nickname, int choice) throws IOException;

    /**
     * Draws a card for the player with the specified nickname.
     *
     * @param nickname the nickname of the player drawing the card
     * @param choice   the index of the card to draw
     * @throws IOException if an I/O error occurs
     */
    void drawCard(String nickname, int choice) throws IOException;

    /**
     * Plays a card for the player with the specified nickname.
     *
     * @param nickname the nickname of the player playing the card
     * @param cardIndex the index of the card to play
     * @param front     whether to play the front side of the card
     * @param x         the x-coordinate to place the card
     * @param y         the y-coordinate to place the card
     * @throws IOException if an I/O error occurs
     */
    void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException;

    /**
     * Sends a public message from the sender.
     *
     * @param sender  the nickname of the player sending the message
     * @param message the message content
     * @throws RemoteException if a remote error occurs
     */
    void sendPublicMessage(String sender, String message) throws RemoteException;

    /**
     * Sends a private message from the sender to the receiver.
     *
     * @param sender   the nickname of the player sending the message
     * @param receiver the nickname of the player receiving the message
     * @param message  the message content
     * @throws RemoteException if a remote error occurs
     */
    void sendPrivateMessage(String sender, String receiver, String message) throws RemoteException;

    /**
     * Sends a heartbeat signal to indicate the client is still active.
     *
     * @throws IOException if an I/O error occurs
     */
    void heartbeat() throws IOException;

    /**
     * Stops sending heartbeat signals.
     */
    void stopHeartbeat();
}
