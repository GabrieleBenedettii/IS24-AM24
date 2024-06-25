package it.polimi.ingsw.am24.network.socket.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelview.GameView;

import java.rmi.RemoteException;

public class BeginDrawMessage extends SocketServerMessage {
    private GameView gameView;

    public BeginDrawMessage(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.beginDraw(gameView);
    }
}
