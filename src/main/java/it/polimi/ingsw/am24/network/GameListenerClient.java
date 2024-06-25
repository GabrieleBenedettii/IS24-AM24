package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.view.Flow;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The GameListenerClient class implements the GameListener interface and is Serializable.
 * It acts as a client-side listener for game-related events, forwarding these events to a provided Flow instance.
 */
public class GameListenerClient implements GameListener, Serializable {

    private final Flow flow;

    /**
     * Constructs a GameListenerClient with the specified Flow instance.
     *
     * @param flow The Flow instance that handles the game flow.
     */
    public GameListenerClient(Flow flow) {
        this.flow = flow;
    }

    /**
     * Notifies the Flow instance of an invalid number of players.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void invalidNumPlayers() throws RemoteException {
        flow.invalidNumPlayers();
    }

    /**
     * Informs the Flow instance when a player joins the game.
     *
     * @param players List of players.
     * @param current Current player joining.
     * @param num     Total number of players.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void playerJoined(ArrayList<String> players, String current, int num) throws RemoteException {
        flow.playerJoined(players, current, num);
    }

    /**
     * Alerts the Flow instance that no lobby is available.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void noLobbyAvailable() throws RemoteException {
        flow.noLobbyAvailable();
    }

    /**
     * Indicates to the Flow instance that a chosen nickname is already in use.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void nicknameAlreadyUsed() throws RemoteException {
        flow.nicknameAlreadyUsed();
    }

    /**
     * Provides the Flow instance with available colors for player selection.
     *
     * @param colors List of available colors.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void availableColors(ArrayList<String> colors) throws RemoteException {
        flow.availableColors(colors);
    }

    /**
     * Informs the Flow instance about colors that are not available for selection.
     *
     * @param colors List of unavailable colors.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void notAvailableColors(ArrayList<String> colors) throws RemoteException {
        flow.notAvailableColors(colors);
    }

    /**
     * Sends hidden goal choices to the Flow instance.
     *
     * @param cardViews List of card views for hidden goals.
     * @param gameView  Current state of the game.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) throws RemoteException {
        flow.hiddenGoalChoice(cardViews, gameView);
    }

    /**
     * Sends initial card sides to the Flow instance.
     *
     * @param front View of the front side of the initial card.
     * @param back  View of the back side of the initial card.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void initialCardSide(GameCardView front, GameCardView back) throws RemoteException {
        flow.initialCardSide(front, back);
    }

    /**
     * Informs the Flow instance to begin a turn in the game.
     *
     * @param gameView Current state of the game.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void beginTurn(GameView gameView) throws RemoteException {
        flow.beginTurn(gameView);
    }

    /**
     * Notifies the Flow instance of an invalid card positioning.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void invalidPositioning() throws RemoteException {
        flow.invalidPositioning();
    }

    /**
     * Notifies the Flow instance that game requirements are not met for an action.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void requirementsNotMet() throws RemoteException {
        flow.requirementsNotMet();
    }

    /**
     * Informs the Flow instance to begin drawing phase in the game.
     *
     * @param gameView Current state of the game.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void beginDraw(GameView gameView) throws RemoteException {
        flow.beginDraw(gameView);
    }

    /**
     * Alerts the Flow instance of an incorrect card play in the game.
     *
     * @param gameView Current state of the game.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void wrongCardPlay(GameView gameView) throws RemoteException {
        flow.wrongCardPlay(gameView);
    }

    /**
     * Informs the Flow instance that the game has ended.
     *
     * @param winner Name of the winner.
     * @param rank   Ranking of players with their scores.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void gameEnded(String winner, HashMap<String,Integer> rank) throws RemoteException {
        flow.gameEnded(winner, rank);
    }

    /**
     * Sends a message from one player to another.
     *
     * @param sender   Sender's name.
     * @param receiver Receiver's name.
     * @param message  Message content.
     * @param time     Time when the message was sent.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void sentMessage(String sender, String receiver, String message, String time) throws RemoteException {
        flow.sentMessage(sender, receiver, message, time);
    }
}

