package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.exceptions.FullLobbyException;
import it.polimi.ingsw.am24.exceptions.NotExistingPlayerException;
import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The LobbyController class manages the creation and joining of game lobbies.
 * It handles player registration, lobby creation, and lobby management.
 */
public class LobbyController implements Serializable, LobbyControllerInterface {
    private final Queue<GameController> games; //active lobbies
    private final List<String> playingNicknames;
    private static LobbyController instance = null;
    private final Object lock = new Object();

    /**
     * Constructor for the LobbyController class.
     * Initializes the queues and lists for managing games and player nicknames.
     */
    public LobbyController() {
        this.games = new LinkedBlockingQueue<>();
        this.playingNicknames = new ArrayList<>();
        instance = this;
    }

    /**
     * Returns an instance of the LobbyController.
     * @return an instance.
     */
    public synchronized static LobbyController getInstance() {
        if (instance == null) {
            instance = new LobbyController();
        }
        return instance;
    }
    /**
     * Allows a player to join a game or creates a new game if necessary.
     * @param nickname the player's nickname.
     * @param numOfPlayers the number of players in the game.
     * @param listener the GameListener for the player.
     * @return the GameControllerInterface for the game the joined player.
     * @throws RemoteException if there is a network error.
     */
    //Includes: createGame and joinFirstAvailableLobby
    public synchronized GameControllerInterface joinGame(String nickname, int numOfPlayers, GameListener listener) throws RemoteException {

        System.out.println("Register request for player with nickname:  " + nickname + " and numOfPlayers: " + numOfPlayers);

        if(numOfPlayers < 1 || numOfPlayers > Constants.MAX_PLAYERS) {
            listener.invalidNumPlayers();
            return null;
        }
        if(nickname == null || nickname.isEmpty()) {
            //listener.nicknameNotValid();
            return null;
        }
        if(playingNicknames.contains(nickname)) {
            listener.nicknameAlreadyUsed();
            return null;
        }
        System.out.println("Register request for player with nickname:  " + nickname + " parameters were valid. Logging player in");

        synchronized(lock) {
            //if the numOfPlayers is more than 1 -> create a new lobby
            if(numOfPlayers != 1){
                GameController lobby = new GameController(numOfPlayers);
                try {
                    lobby.addPlayer(nickname, listener);
                    playingNicknames.add(nickname);
                    games.add(lobby);
                    return lobby;
                } catch (FullLobbyException ignored) {
                    //it can't happen
                }
            }
            //otherwise add the player in an existing lobby (the first not full)
            else {
                for(GameController g : games) {
                    try {
                        g.addPlayer(nickname, listener);
                        playingNicknames.add(nickname);
                        System.out.println("Player added to an existing lobby");
                        return g;
                    } catch (FullLobbyException e) {
                        //I need to continue
                    }
                }
                listener.noLobbyAvailable();
            }

        }
        return null;
    }

    /**
     * Remove a player from a game.
     * @param nickname nickname of the player to delete.
     */
    public void disconnectPlayer(String nickname) throws NotExistingPlayerException {
        if(!playingNicknames.contains(nickname)) throw new NotExistingPlayerException();
        synchronized (lock) {
            playingNicknames.remove(nickname);
        }
    }

    /**
     * Deletes a game based on its ID.
     * @param gameId the ID of the game to delete.
     */
    public synchronized void deleteGame(int gameId) {
        List<GameController> game = games.stream().filter(g -> g.getGameId() == gameId).toList();
        if(!game.isEmpty()) {
            games.remove(game.getFirst());
            System.out.println("Game " + gameId + " deleted");
        }
    }
}
