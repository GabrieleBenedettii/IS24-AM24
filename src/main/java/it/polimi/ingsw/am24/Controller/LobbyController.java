package it.polimi.ingsw.am24.Controller;

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
    private static LobbyController instance = null;

    public LobbyController() {
        this.games = new LinkedBlockingQueue<>();
    }

    public synchronized static LobbyController getInstance() {
        if (instance == null) {
            instance = new LobbyController();
        }
        return instance;
    }

    //Includes: createGame and joinFirstAvailableLobby
    public synchronized GameControllerInterface joinGame(String nickname, int numOfPlayers, GameListener listener) throws RemoteException {

        final List<String> playingNicknames = new ArrayList<>();

        System.out.println("Register request for player with nickname:  " + nickname + " and numOfPlayers: " + numOfPlayers);

        //todo set min and max num of player on a setting file
        if(numOfPlayers < 1 || numOfPlayers>4) {
            listener.invalidNumPlayers();
            return null;
        }
        //todo create message for null nickname
        if(nickname == null) {
            return null;
        }
        System.out.println("Register request for player with nickname:  " + nickname + " parameters were valid. Logging player in");

        synchronized(playingNicknames) {
            synchronized (games){
                //if the numOfPlayers is more than 1 or there is no lobby-> create a new lobby
                if(numOfPlayers != 1){
                    GameController lobby = new GameController(numOfPlayers);
                    lobby.addNewPlayer(nickname, listener);
                    playingNicknames.add(nickname);
                    games.add(lobby);
                    return lobby;
                }
                //otherwise add the player in an existing lobby
                else {
                    for(GameController g : games) {
                        if(!g.isFull()) {
                            g.addNewPlayer(nickname, listener);
                            System.out.println("Player added to an existing lobby");
                            return g;
                        }
                    }
                    System.out.println("No lobby found");
                }
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
    }

    //Todo cambiare Gamecontroller con un int
    public synchronized int deleteGame(GameController game) {
        if(game != null){
            games.remove(game);
            System.out.println("game deleted");
        }

        return games.size();
    }
*/
    public void printGames(){
        int i = 1;
        System.out.println("Active games: ");
        for (GameController g: games){
            System.out.print(i+" - players:");
            for (String s: g.getPlayers().keySet()){
                System.out.print(" "+s);
            }
            i++;
        }
    }

}
