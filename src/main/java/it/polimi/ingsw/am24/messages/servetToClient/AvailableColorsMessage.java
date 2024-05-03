package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AvailableColorsMessage extends SocketServerMessage {
    public final ArrayList<String> colors;

    public AvailableColorsMessage(ArrayList<String> colors) {
        this.colors = colors;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.availableColors(colors);
    }
}
