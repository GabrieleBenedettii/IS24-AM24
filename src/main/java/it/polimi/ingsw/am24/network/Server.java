package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void handleMessage(Message m, Client c) throws RemoteException;
}
