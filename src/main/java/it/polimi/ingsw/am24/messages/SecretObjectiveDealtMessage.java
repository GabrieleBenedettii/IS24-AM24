package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;

public class SecretObjectiveDealtMessage extends Message {
    private GameCardView[] views;

    public SecretObjectiveDealtMessage(GameCardView goal1, GameCardView goal2) {
        this.views = new GameCardView[]{goal1, goal2};
    }

    public GameCardView[] getViews() {
        return views;
    }
}
