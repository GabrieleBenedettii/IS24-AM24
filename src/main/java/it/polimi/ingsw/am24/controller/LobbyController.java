package it.polimi.ingsw.am24.controller;

import it.polimi.ingsw.am24.Exceptions.FullLobbyException;
import it.polimi.ingsw.am24.Exceptions.NotExistingPlayerException;
import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.network.rmi.LobbyControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class LobbyController implements Serializable, LobbyControllerInterface {
    private final Queue<GameController> games; //active lobbies
    private final List<String> playingNicknames;
    private static LobbyController instance = null;
    private final Object lock = new Object();

    public LobbyController() {
        this.games = new LinkedBlockingQueue<>();
        this.playingNicknames = new ArrayList<>();
        instance = this;
    }

    public synchronized static LobbyController getInstance() {
        if (instance == null) {
            instance = new LobbyController();
        }
        return instance;
    }

    //Includes: createGame and joinFirstAvailableLobby
    public synchronized GameControllerInterface joinGame(String nickname, int numOfPlayers, GameListener listener) throws RemoteException {

        System.out.println("Register request for player with nickname:  " + nickname + " and numOfPlayers: " + numOfPlayers);

        //todo set min and max num of player on a setting file
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
            //if the numOfPlayers is more than 1 or there is no lobby-> create a new lobby
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
            //otherwise add the player in an existing lobby
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

/*
    //Todo cambiare Gamecontroller con un int
    public synchronized GameControllerInterface leaveGame(String nickname, GameController game){
        game.removePlayer(nickname);
        if (game.getNumOfActivePlayers()<1){
            System.out.println("the are not enough players, game deleted");
            deleteGame(game);
        }
        else
            System.out.println(nickname+" has left the game");

        return null;
    }*/

    public void disconnectPlayer(String nickname) throws NotExistingPlayerException {
        if(!playingNicknames.contains(nickname)) throw new NotExistingPlayerException();
        synchronized (lock) {
            playingNicknames.remove(nickname);
        }
    }

    public synchronized void deleteGame(int gameId) {
        List<GameController> game = games.stream().filter(g -> g.getGameId() == gameId).toList();
        if(!game.isEmpty()) {
            games.remove(game.getFirst());
            System.out.println("Game " + gameId + " deleted");
        }
    }

    /*public void printGames(){
        int i = 1;
        System.out.println("Active games: ");
        for (GameController g: games){
            System.out.print(i+" - players:");
            for (String s: g.getPlayers().keySet()){
                System.out.print(" "+s);
            }
            i++;
        }
    }*/
}
