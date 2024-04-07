package it.polimi.ingsw.am24.Messages;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.ResourceCard;
public class DrawResourceCardMessage extends Message{
    private ResourceCard card;
    private Player player;

    public DrawResourceCardMessage(ResourceCard card, Player player) {
        this.card = card;
        this.player = player;
    }
}
