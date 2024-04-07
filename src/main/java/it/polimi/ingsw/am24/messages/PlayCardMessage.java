package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.PlayableCard;

public class PlayCardMessage extends Message{
    private Player player;
    private PlayableCard card;
    private int posX;
    private int posY;

    public PlayCardMessage(Player player, PlayableCard card, int posX, int posY) {
        this.player = player;
        this.card = card;
        this.posX = posX;
        this.posY = posY;
    }
}
