package it.polimi.ingsw.am24.network.socket.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.rmi.RemoteException;

public class ChooseHiddenGoalMessage extends SocketClientMessage {
    private final int cardId;

    public ChooseHiddenGoalMessage(String nickname, int cardId) {
        this.nickname = nickname;
        this.cardId = cardId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCardId() {
        return cardId;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.chooseGoal(nickname, cardId, listener);
    }
}
