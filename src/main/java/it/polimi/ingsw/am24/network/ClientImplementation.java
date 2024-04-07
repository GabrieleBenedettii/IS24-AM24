package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.view.View;
import it.polimi.ingsw.am24.messages.Message;
import it.polimi.ingsw.am24.view.CLI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementation extends UnicastRemoteObject implements Client {
    private static ClientImplementation instance;
    private Server server;
    private final View view;

    protected ClientImplementation(View view, Server server) throws RemoteException {
        super();
        this.view = view;
        this.server = server;
        view.addListener((message) -> {
            try {
                server.handleMessage(message, this);
            } catch (RemoteException e) {
                System.out.println("RemoteException occurred while sending message to server");
            }
        });
    }

    public static ClientImplementation getInstance(View view, Server server) throws RemoteException {
        if(instance == null)
            instance = new ClientImplementation(view, server);
        return instance;
    }

    public static void main(String[] args)
    {
        //todo chose between cli or gui
        View ui = new CLI();
        ui.run();
    }

    @Override
    public void update(Message m) throws RemoteException {
        this.view.update(m);
    }
}
