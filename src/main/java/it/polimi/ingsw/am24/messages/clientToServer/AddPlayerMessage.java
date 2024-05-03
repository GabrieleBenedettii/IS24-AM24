package it.polimi.ingsw.am24.messages.clientToServer;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;

public class AddPlayerMessage extends SocketServerMessage {
    private final String nickname;
    private final int numPlayers;  //num = [2;4] => first player, num = 1 => other

    public AddPlayerMessage(String username, int numPlayers) {
        this.nickname = username;
        this.numPlayers = numPlayers;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        //listener.addedPlayer(player) todo add addedPlayer method in listener
    }
}
