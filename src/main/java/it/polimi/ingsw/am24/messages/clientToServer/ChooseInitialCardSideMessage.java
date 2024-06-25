package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

public class ChooseInitialCardSideMessage extends SocketClientMessage {
    private final int choice;

    public ChooseInitialCardSideMessage(String nickname, int choice) {
        this.nickname = nickname;
        this.choice = choice;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.chooseInitialCardSide(nickname, choice==0, listener);
    }
}
