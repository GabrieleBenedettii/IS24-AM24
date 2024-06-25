package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

public class PlayCardMessage extends SocketClientMessage {
    private boolean isFront;
    private int cardIndex;
    private int x;
    private int y;

    public PlayCardMessage(String nickname, int cardIndex, boolean isFront, int x, int y) {
        this.nickname = nickname;
        this.isFront = isFront;
        this.cardIndex = cardIndex;
        this.x = x;
        this.y = y;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean getFront() {
        return isFront;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.playCard(nickname, cardIndex, isFront, x, y, listener);
    }
}
