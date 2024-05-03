package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;

public class NicknameAlreadyUsedMessage extends SocketServerMessage {
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.nicknameAlreadyUsed();
    }
}
