package it.polimi.ingsw.am24.messages;


import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.InitialCard;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.List;

public class InitialCardDealtMessage extends Message{
    private GameCardView[] views = new GameCardView[2];

    public InitialCardDealtMessage(GameCardView front, GameCardView back) {
        this.views[0] = front;
        this.views[1] = back;
    }

    public GameCardView[] getViews() {
        return views;
    }
}
