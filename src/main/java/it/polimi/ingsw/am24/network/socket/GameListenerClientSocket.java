package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.network.socket.messages.serverToClient.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The GameListenerClientSocket class implements the GameListener interface
 * and handles communication with the client via socket by sending messages.
 */
public class GameListenerClientSocket implements GameListener, Serializable {
    private final ObjectOutputStream out;

    /**
     * Constructs a GameListenerClientSocket instance with the specified ObjectOutputStream.
     *
     * @param out The ObjectOutputStream used for sending messages to the client.
     */
    public GameListenerClientSocket(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * Sends a message indicating that the number of players is invalid.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void invalidNumPlayers() throws RemoteException {
        try {
            out.writeObject(new InvalidNumPlayersMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating that a player has joined the game.
     *
     * @param players The list of current players in the game.
     * @param current The nickname of the player who just joined.
     * @param num     The total number of players in the game.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void playerJoined(ArrayList<String> players, String current, int num) throws RemoteException {
        try {
            out.writeObject(new PlayerJoinedMessage(players,current,num));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating that no lobby is available.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    public void noLobbyAvailable() throws RemoteException {
        try {
            out.writeObject(new NoLobbyAvailableMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating that the nickname is already used.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void nicknameAlreadyUsed() throws RemoteException {
        try {
            out.writeObject(new NicknameAlreadyUsedMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating the available colors for selection.
     *
     * @param colors The list of available colors.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void availableColors(ArrayList<String> colors) throws RemoteException {
        try {
            out.writeObject(new AvailableColorsMessage(colors));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating the not available colors for selection.
     *
     * @param colors The list of not available colors.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void notAvailableColors(ArrayList<String> colors) throws RemoteException {
        try {
            out.writeObject(new NotAvailableColorMessage(colors));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating the hidden goal choice options.
     *
     * @param cardViews The list of GameCardView objects representing the hidden goal choices.
     * @param gameView  The current GameView.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) throws RemoteException {
        try {
            out.writeObject(new HiddenGoalChoiceMessage(cardViews, gameView));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating the initial card sides chosen.
     *
     * @param front The front GameCardView.
     * @param back  The back GameCardView.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void initialCardSide(GameCardView front, GameCardView back) throws RemoteException {
        try {
            out.writeObject(new InitialCardSideMessage(front, back));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating the beginning of a player's turn.
     *
     * @param gameView The current GameView.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void beginTurn(GameView gameView) throws RemoteException {
        try {
            out.writeObject(new BeginTurnMessage(gameView));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating invalid positioning of game elements.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void invalidPositioning() throws RemoteException {
        try {
            out.writeObject(new InvalidPositioningMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating that game requirements are not met.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void requirementsNotMet() throws RemoteException {
        try {
            out.writeObject(new RequirementsNotMetMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating the beginning of a card draw action.
     *
     * @param gameView The current GameView.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void beginDraw(GameView gameView) throws RemoteException {
        try {
            out.writeObject(new BeginDrawMessage(gameView));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating an incorrect card play.
     *
     * @param gameView The current GameView.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void wrongCardPlay(GameView gameView) throws RemoteException {
        try {
            out.writeObject(new WrongCardPlayMessage(gameView));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating the end of the game with winner and rank information.
     *
     * @param winner The nickname of the player who won the game.
     * @param rank   The map containing player nicknames and their ranks.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void gameEnded(String winner, HashMap<String, Integer> rank) throws RemoteException {
        try {
            out.writeObject(new GameEndedMessage(winner,rank));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    /**
     * Sends a message indicating a private message sent between players.
     *
     * @param sender   The nickname of the sender.
     * @param receiver The nickname of the receiver.
     * @param message  The message content.
     * @param time     The timestamp of the message.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void sentMessage(String sender, String receiver, String message, String time) throws RemoteException {
        try {
            out.writeObject(new SentMessageMessage(sender, receiver, message, time));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }
}
