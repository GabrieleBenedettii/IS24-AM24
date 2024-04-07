package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void update(Message m) throws RemoteException;
}
