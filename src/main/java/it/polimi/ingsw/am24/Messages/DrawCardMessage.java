package it.polimi.ingsw.am24.Messages;

import it.polimi.ingsw.am24.model.Player;

public class DrawCardMessage extends Message{
    private Player player;

    public Player getPlayer() {
        return player;
    }
}
