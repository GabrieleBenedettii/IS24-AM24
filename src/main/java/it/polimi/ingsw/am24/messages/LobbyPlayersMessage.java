package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.model.Player;

import java.util.ArrayList;

public class LobbyPlayersMessage extends Message{
    private final ArrayList<Player> players;

    public LobbyPlayersMessage(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
