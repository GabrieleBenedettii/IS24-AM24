package it.polimi.ingsw.am24.network.rmi;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.controller.LobbyController;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RMIServer class represents a server implementation using RMI (Remote Method Invocation).
 * It implements the LobbyControllerInterface and manages game lobby interactions remotely.
 */
public class RMIServer extends UnicastRemoteObject implements LobbyControllerInterface {

    private final LobbyControllerInterface controller;

    private static RMIServer server = null;

    private static Registry registry = null;

    /**
     * Binds the RMIServer instance to the RMI registry.
     *
     * @return The RMIServer instance that is bound to the registry.
     */
    public static RMIServer bind() {
        try {
            server = new RMIServer();
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.createRegistry(Constants.RMIPort);
            getRegistry().rebind(Constants.SERVERNAME, server);
            System.out.println("Server RMI ready");
        } catch (RemoteException e) {
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
        }
        return getInstance();
    }

    /**
     * Returns the singleton instance of RMIServer.
     *
     * @return The singleton instance of RMIServer.
     */
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

    /**
     * Retrieves the RMI registry.
     *
     * @return The RMI registry instance.
     * @throws RemoteException if a remote communication issue occurs.
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }

    /**
     * Constructs an RMIServer instance.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    public RMIServer() throws RemoteException {
        super(0);
        controller = LobbyController.getInstance();
    }

    /**
     * Allows a player to join a game lobby.
     *
     * @param nickname The nickname of the player joining the game.
     * @param numPlayers The number of players in the game.
     * @param listener The GameListener instance to receive game-related notifications.
     * @return The GameControllerInterface instance for interacting with the game.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public GameControllerInterface joinGame(String nickname, int numPlayers, GameListener listener) throws RemoteException {
        GameControllerInterface ris = server.controller.joinGame(nickname, numPlayers, listener);
        if (ris != null) {
            try {
                UnicastRemoteObject.exportObject(ris, 0);
            }catch (RemoteException e){

            }
            System.out.println("[RMI] " + nickname + (numPlayers != 1 ? " created a new lobby" : " joined in first available game"));
        }
        return ris;
    }
}

