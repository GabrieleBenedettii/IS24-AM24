package it.polimi.ingsw.am24.messages.servetToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.SocketServerMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class NotAvaiableColorMessage extends SocketServerMessage {
    public final ArrayList<String> colors;

    public NotAvaiableColorMessage(ArrayList<String> colors) {
        this.colors = colors;
    }

    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.notAvailableColors(colors);
    }
}
