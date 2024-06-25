package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.exceptions.NotExistingPlayerException;
import it.polimi.ingsw.am24.listeners.GameListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The GameControllerInterface defines methods for controlling game actions remotely.
 */
//server stub, remote methods that clients can call
public interface GameControllerInterface extends Remote {

    /**
     * Allows a player to choose a color for their character.
     *
     * @param nickname The nickname of the player choosing the color.
     * @param color    The color chosen by the player.
     * @param listener The GameListener instance to receive game-related notifications.
     * @return true if the color choice was successful, false otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    boolean chooseColor(String nickname, String color, GameListener listener) throws RemoteException;

    /**
     * Allows a player to choose a goal.
     *
     * @param nickname The nickname of the player choosing the goal.
     * @param goalId   The ID of the goal chosen by the player.
     * @param listener The GameListener instance to receive game-related notifications.
     * @return true if the goal choice was successful, false otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    boolean chooseGoal(String nickname, int goalId, GameListener listener) throws RemoteException;

    /**
     * Allows a player to choose the initial side of a card.
     *
     * @param nickname The nickname of the player choosing the card side.
     * @param front    true if the front side of the card is chosen, false for the back side.
     * @param listener The GameListener instance to receive game-related notifications.
     * @return true if the initial card side choice was successful, false otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    boolean chooseInitialCardSide(String nickname, boolean front, GameListener listener) throws RemoteException;

    /**
     * Allows a player to play a card on the game board.
     *
     * @param name     The name of the player playing the card.
     * @param cardIndex The index of the card being played.
     * @param isFront  true if the front side of the card is being played, false for the back side.
     * @param x        The x-coordinate on the game board where the card is being played.
     * @param y        The y-coordinate on the game board where the card is being played.
     * @param listener The GameListener instance to receive game-related notifications.
     * @return true if the card play was successful, false otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    boolean playCard(String name, int cardIndex, boolean isFront, int x, int y, GameListener listener) throws RemoteException;

    /**
     * Allows a player to draw a card from the deck.
     *
     * @param p        The nickname of the player drawing the card.
     * @param cardIndex The index of the card being drawn.
     * @param listener The GameListener instance to receive game-related notifications.
     * @return true if the card draw was successful, false otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    boolean drawCard(String p, int cardIndex, GameListener listener) throws RemoteException;

    /**
     * Sends a public message from a player to all players in the game.
     *
     * @param sender   The nickname of the player sending the message.
     * @param message  The message content.
     * @return true if the message was sent successfully, false otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    boolean sentPublicMessage(String sender, String message) throws RemoteException;

    /**
     * Sends a private message from one player to another player.
     *
     * @param sender   The nickname of the player sending the message.
     * @param receiver The nickname of the player receiving the message.
     * @param message  The message content.
     * @return true if the message was sent successfully, false otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    boolean sentPrivateMessage(String sender, String receiver, String message) throws RemoteException;

    /**
     * Sends a heartbeat signal to indicate the player is still active.
     *
     * @param nickname The nickname of the player sending the heartbeat.
     * @param listener The GameListener instance to receive game-related notifications.
     * @throws RemoteException if a remote communication issue occurs.
     */
    void heartbeat(String nickname, GameListener listener) throws RemoteException;

    /**
     * Disconnects a player from the game.
     *
     * @param nickname The nickname of the player to disconnect.
     * @throws RemoteException           if a remote communication issue occurs.
     * @throws NotExistingPlayerException if the player does not exist.
     */
    void disconnectPlayer(String nickname) throws RemoteException, NotExistingPlayerException;
}
