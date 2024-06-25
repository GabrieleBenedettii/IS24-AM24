package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.common.GameControllerInterface;
import it.polimi.ingsw.am24.network.common.LobbyControllerInterface;

import java.rmi.RemoteException;

public class ChooseColorMessage extends SocketClientMessage {
    private String color;

    public ChooseColorMessage(String username, String color) {
        this.nickname = username;
        this.color = color;
    }

    public String getNickname() {
        return nickname;
    }

    public String getColor() {
        return color;
    }

    @Override
    public GameControllerInterface execute(GameListener listener, LobbyControllerInterface controller) throws RemoteException {
        return null;
    }

    @Override
    public void execute(GameListener listener, GameControllerInterface controller) throws RemoteException {
        controller.chooseColor(nickname, color, listener);
    }
}
