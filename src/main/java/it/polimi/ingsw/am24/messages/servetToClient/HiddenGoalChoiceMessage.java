package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class HiddenGoalChoiceMessage extends SocketServerMessage {
    private final ArrayList<GameCardView> views = new ArrayList<>();

    public HiddenGoalChoiceMessage(List<GameCardView> views) {
        this.views.set(0, views.get(0));
        this.views.set(1, views.get(1));
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.hiddenGoalChoice(views);
    }
}
