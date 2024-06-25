package it.polimi.ingsw.am24.network.socket.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.rmi.RemoteException;

public class DrawCardMessage extends SocketClientMessage {
    private final int card;

    public DrawCardMessage(String nickname, int card) {
        this.nickname = nickname;
        this.card = card;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCard() {
        return card;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.drawCard(nickname, card, listener);
    }
}
