package it.polimi.ingsw.am24.network.rmi;

import it.polimi.ingsw.am24.Controller.LobbyController;
import it.polimi.ingsw.am24.listeners.GameListener;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements LobbyControllerInterface {

    private final LobbyControllerInterface controller;

    private static RMIServer server = null;

    private static Registry registry = null;

    public static RMIServer bind() {
        int port = 1234;
        String serverName = "CodexNaturalis-Server";

        try {
            server = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(port);
            getRegistry().rebind(serverName, server);
            System.out.println("Server RMI ready");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return getInstance();
    }

    public synchronized static RMIServer getInstance() {
        if(server == null) {
            try {
                server = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return server;
    }

    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }


    public RMIServer() throws RemoteException {
        super(0);
        controller = LobbyController.getInstance();
    }

    @Override
    public GameControllerInterface joinGame(String nickname, int numPlayers, GameListener listener) throws RemoteException {

        //Return the GameController already existed => not necessary to re-Export Object
        GameControllerInterface ris = server.controller.joinGame(nickname, numPlayers, listener);
        if (ris != null) {
            //ris.setPlayerIdentity((PlayerInterface) UnicastRemoteObject.exportObject(ris.getPlayerIdentity(),0));
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){
                //Already exported, due to another RMI Client running on the same machine
            }
            System.out.println("[RMI] " + nickname + " joined in first available game");
        }
        return ris;
    }

    /*@Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {

        serverObject.mainController.leaveGame(lis,nick,idGame);

        return null;
    }*/
}

