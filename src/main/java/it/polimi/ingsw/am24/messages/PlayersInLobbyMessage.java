package it.polimi.ingsw.am24.messages;

import java.util.List;

public class PlayersInLobbyMessage extends Message {
    private final List<String> players;

    public PlayersInLobbyMessage(List<String> players) {
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }
}
