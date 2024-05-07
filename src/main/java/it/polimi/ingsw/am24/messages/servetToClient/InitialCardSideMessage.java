package it.polimi.ingsw.am24.messages.servetToClient;


import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class InitialCardSideMessage extends SocketServerMessage {
    private final ArrayList<GameCardView> views = new ArrayList<>();

    public InitialCardSideMessage(GameCardView front, GameCardView back) {
        this.views.add(front);
        this.views.add(back);
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.initialCardSide(views.get(0),views.get(1));
    }
}
