package it.polimi.ingsw.am24.Messages;


import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.InitialCard;

public class InitialCardDealtMessage extends Message{
    private Player player;
    private InitialCard card;
    public InitialCardDealtMessage(Player player, InitialCard card) {
        this.player = player;
        this.card = card;
    }


}
