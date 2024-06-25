package it.polimi.ingsw.am24.network.socket.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.rmi.RemoteException;

/**
 * The {@code ChooseColorMessage} class represents a message sent from the client to the server
 * to choose a color for the player.
 */
public class ChooseColorMessage extends SocketClientMessage {
    private String color;

    /**
     * Constructs a {@code ChooseColorMessage} with the specified username and color.
     *
     * @param username the nickname of the player sending the message
     * @param color    the color chosen by the player
     */
    public ChooseColorMessage(String username, String color) {
        this.nickname = username;
        this.color = color;
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
     * Returns the color chosen by the player.
     *
     * @return the chosen color
     */
    public String getColor() {
        return color;
    }

    /**
     * Executes the message operation on the server by choosing the player's color.
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
     * Executes the message operation on the server by choosing the player's color.
     *
     * @param listener   the game listener interface on the client
     * @param controller the game controller interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.chooseColor(nickname, color, listener);
    }
}
