package it.polimi.ingsw.am24.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LobbyController implements Serializable/*, LobbyControllerInterface*/ {
    private List<GameController> games;     //Lista delle lobby attive

    public LobbyController(List<GameController> games) {
        this.games = new ArrayList<>();
    }

    public synchronized int deleteGame(GameController game) {
        games.remove(game);
        System.out.println("Game deleted");
        return games.size();
    }
}