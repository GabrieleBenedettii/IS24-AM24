package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;
import java.util.List;

public class SecretObjectiveDealtMessage extends Message {
    private GameCardView[] views = new GameCardView[2];

    public SecretObjectiveDealtMessage(List<GameCardView> views) {
        this.views[0] = views.get(0);
        this.views[1] = views.get(1);
    }

    public GameCardView[] getViews() {
        return views;
    }
}
