package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.model.Player;

public class PlayerDisconnectMessage extends Message{
    Player player;

    public PlayerDisconnectMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
