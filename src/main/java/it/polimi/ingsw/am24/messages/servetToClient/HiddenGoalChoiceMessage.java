package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;
import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class HiddenGoalChoiceMessage extends SocketServerMessage {
    private final ArrayList<GameCardView> views = new ArrayList<>();
    private final GameView gameView;

    public HiddenGoalChoiceMessage(List<GameCardView> views, GameView gameView) {
        this.views.add(views.get(0));
        this.views.add(views.get(1));
        this.gameView = gameView;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.hiddenGoalChoice(views, gameView);
    }
}
