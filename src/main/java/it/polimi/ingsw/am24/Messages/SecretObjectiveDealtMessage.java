package it.polimi.ingsw.am24.Messages;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.goal.GoalCard;

import java.util.ArrayList;

public class SecretObjectiveDealtMessage extends Message{
    private Player player;

    private ArrayList<GoalCard> goals;

    public SecretObjectiveDealtMessage(Player player, ArrayList<GoalCard> goals) {
        this.player = player;
        this.goals = goals;
    }
}
