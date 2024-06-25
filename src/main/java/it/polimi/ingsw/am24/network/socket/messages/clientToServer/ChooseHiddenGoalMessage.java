package it.polimi.ingsw.am24.network.socket.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code ChooseHiddenGoalMessage} class represents a message sent from the client to the server
 * to choose a hidden goal card for the player.
 */
public class ChooseHiddenGoalMessage extends SocketClientMessage {
    private final int cardId;

    /**
     * Constructs a {@code ChooseHiddenGoalMessage} with the specified nickname and card ID.
     *
     * @param nickname the nickname of the player sending the message
     * @param cardId   the ID of the hidden goal card chosen by the player
     */
    public ChooseHiddenGoalMessage(String nickname, int cardId) {
        this.nickname = nickname;
        this.cardId = cardId;
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
     * Returns the ID of the hidden goal card chosen by the player.
     *
     * @return the card ID
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * Executes the message operation on the server by choosing the hidden goal card for the player.
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
     * Executes the message operation on the server by choosing the hidden goal card for the player.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.chooseGoal(nickname, cardId, listener);
    }
}
