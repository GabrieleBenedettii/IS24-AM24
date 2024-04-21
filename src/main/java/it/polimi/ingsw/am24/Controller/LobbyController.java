package it.polimi.ingsw.am24.Controller;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.InvalidNumOfPlayersMessage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class LobbyController implements Serializable, LobbyControllerInterface {
    private Queue<GameController> games;     //Lista delle lobby attive

    public LobbyController(Queue<GameController> games) {
        this.games = new LinkedBlockingQueue<GameController>();
    }

    //Includes: createGame and joinFirstAvaiableLobby
    public synchronized GameControllerInterface joinGame(String nickname, int numOfPlayers, GameListener listener) throws RemoteException {

        final List<String> playingNicknames = new ArrayList<>();

        System.out.println("Register request for player with nickname:  " + nickname + " and numOfPlayers: " + numOfPlayers);

        //todo set min and max num of player on a setting file
        if(numOfPlayers < 1 || numOfPlayers>4) {
            listener.update(new InvalidNumOfPlayersMessage());
            return null;
        }
        //todo create message for null nickname
        if(nickname == null) {
            return null;
        }
        System.out.println("Register request for player with nickname:  " + nickname + " parameters were valid. Logging player in");

        synchronized(playingNicknames) {
            //todo add the search of the nickname in the disconnected array
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
                    if(games.peek() != null) {
                        GameController lobby = games.peek();
                        if(lobby.addNewPlayer(nickname, listener)) {
                            while (games.peek() != null && games.peek().getNumOfActivePlayers() == 0)
                                games.poll();
                            System.out.println("Player added to an existing lobby");
                        }
                        playingNicknames.add(nickname);
                        return lobby;
                    }
                    else{
                        System.out.println("No lobby found");
                    }
                }
            }
        }
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
