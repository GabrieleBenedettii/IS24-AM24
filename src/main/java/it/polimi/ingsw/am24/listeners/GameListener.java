package it.polimi.ingsw.am24.listeners;

import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * GameListener is an interface for receiving game events from the server.
 * All methods throw RemoteException as they are intended to be used in a remote RMI context.
 */
public interface GameListener extends Remote {

    /**
     * Notifies that the number of players is invalid.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    void invalidNumPlayers() throws RemoteException;

    /**
     * Notifies that a player has joined the game.
     *
     * @param players List of player names.
     * @param current The name of the current player.
     * @param num The number of players.
     * @throws RemoteException if a remote communication error occurs.
     */
    void playerJoined(ArrayList<String> players, String current, int num) throws RemoteException;

    /**
     * Notifies that no lobby is available.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    void noLobbyAvailable() throws RemoteException;

    /**
     * Notifies that the chosen nickname is already in use.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    void nicknameAlreadyUsed() throws RemoteException;

    /**
     * Provides the available colors for players to choose.
     *
     * @param colors List of available colors.
     * @throws RemoteException if a remote communication error occurs.
     */
    void availableColors(ArrayList<String> colors) throws RemoteException;

    /**
     * Notifies that the chosen colors are not available.
     *
     * @param colors List of not available colors.
     * @throws RemoteException if a remote communication error occurs.
     */
    void notAvailableColors(ArrayList<String> colors) throws RemoteException;

    /**
     * Prompts the player to choose a hidden goal card.
     *
     * @param cardViews List of game card views for the hidden goal choice.
     * @param gameView The current game view.
     * @throws RemoteException if a remote communication error occurs.
     */
    void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) throws RemoteException;

    /**
     * Prompts the player to choose the initial side of a card.
     *
     * @param front The front side of the card.
     * @param back The back side of the card.
     * @throws RemoteException if a remote communication error occurs.
     */
    void initialCardSide(GameCardView front, GameCardView back) throws RemoteException;

    /**
     * Notifies the player to begin their turn.
     *
     * @param gameView The current game view.
     * @throws RemoteException if a remote communication error occurs.
     */
    void beginTurn(GameView gameView) throws RemoteException;

    /**
     * Notifies that the attempted positioning is invalid.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    void invalidPositioning() throws RemoteException;

    /**
     * Notifies that the requirements for a certain action are not met.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    void requirementsNotMet() throws RemoteException;

    /**
     * Notifies the player to begin the draw phase.
     *
     * @param gameView The current game view.
     * @throws RemoteException if a remote communication error occurs.
     */
    void beginDraw(GameView gameView) throws RemoteException;

    /**
     * Notifies that the card play was incorrect.
     *
     * @param gameView The current game view.
     * @throws RemoteException if a remote communication error occurs.
     */
    void wrongCardPlay(GameView gameView) throws RemoteException;

    /**
     * Notifies that the game has ended and provides the final rankings.
     *
     * @param winner The name of the winning player.
     * @param rank The final rankings of the players.
     * @throws RemoteException if a remote communication error occurs.
     */
    void gameEnded(String winner, HashMap<String,Integer> rank) throws RemoteException;

    /**
     * Sends a message to the players.
     *
     * @param sender The sender of the message.
     * @param receiver The receiver of the message.
     * @param message The content of the message.
     * @param time The time the message was sent.
     * @throws RemoteException if a remote communication error occurs.
     */
    void sentMessage(String sender, String receiver, String message, String time) throws RemoteException;
}
