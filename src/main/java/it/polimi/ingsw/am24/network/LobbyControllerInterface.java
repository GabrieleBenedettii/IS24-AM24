package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.listeners.GameListener;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The LobbyControllerInterface defines methods for managing game lobbies remotely.
 */
//server stub, remote methods that clients can call
public interface LobbyControllerInterface extends Remote {
    /**
     * Attempts to join a game lobby with the specified nickname and number of players.
     *
     * @param nickname The nickname chosen by the player.
     * @param numPlayers The number of players the player wishes to join the game with.
     * @param listener The GameListener instance to receive game-related notifications.
     * @return A GameControllerInterface instance if the join was successful, null otherwise.
     * @throws RemoteException if a remote communication issue occurs.
     */
    GameControllerInterface joinGame(String nickname, int numPlayers, GameListener listener) throws RemoteException;
}
