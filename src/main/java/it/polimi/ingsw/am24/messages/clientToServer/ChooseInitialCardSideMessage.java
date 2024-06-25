package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code ChooseInitialCardSideMessage} class represents a message sent from the client to the server
 * to choose the side (front or back) of an initial card.
 */
public class ChooseInitialCardSideMessage extends SocketClientMessage {
    private final int choice;

    /**
     * Constructs a {@code ChooseInitialCardSideMessage} with the specified nickname and choice.
     *
     * @param nickname the nickname of the player sending the message
     * @param choice   the choice indicating the side of the initial card (0 for front, 1 for back)
     */
    public ChooseInitialCardSideMessage(String nickname, int choice) {
        this.nickname = nickname;
        this.choice = choice;
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
     * Executes the message operation on the server by choosing the side of an initial card for the player.
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
     * Executes the message operation on the server by choosing the side of an initial card for the player.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.chooseInitialCardSide(nickname, choice==0, listener);
    }
}
