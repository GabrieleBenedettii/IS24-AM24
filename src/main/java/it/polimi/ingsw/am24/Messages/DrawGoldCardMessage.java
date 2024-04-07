package it.polimi.ingsw.am24.Messages;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.goal.GoalCard;

public class DrawGoldCardMessage extends Message{
    private GoalCard card;
    private Player player;

    public DrawGoldCardMessage(GoalCard card, Player player) {
        this.card = card;
        this.player = player;
    }
}
