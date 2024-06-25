package it.polimi.ingsw.am24.network.rmi;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.GameListenerClient;
import it.polimi.ingsw.am24.network.heartbeat.HeartbeatSender;
import it.polimi.ingsw.am24.network.GameControllerInterface;
import it.polimi.ingsw.am24.network.LobbyControllerInterface;
import it.polimi.ingsw.am24.view.Flow;
import it.polimi.ingsw.am24.view.ClientActions;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements ClientActions {

    private static LobbyControllerInterface server;

    private GameControllerInterface gameController = null;

    private String nickname;

    private static GameListener listener;

    private final GameListenerClient gameListenersHandler;

    private Registry registry;

    private final Flow flow;
    private HeartbeatSender heartbeatSender;

    public RMIClient(Flow flow) {
        super();
        gameListenersHandler = new GameListenerClient(flow);
        this.flow = flow;
        connect();

        heartbeatSender = new HeartbeatSender(flow,this);
        heartbeatSender.start();
    }

    public void connect() {
        boolean retry;

        do {
            retry = false;
            try {
                registry = LocateRegistry.getRegistry(Constants.SERVERIP, Constants.RMIPort);
                server = (LobbyControllerInterface) registry.lookup(Constants.SERVERNAME);

                listener = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

                System.out.println("Client RMI ready");
            } catch (Exception e) {
                if (!retry) {
                    System.out.println("[ERROR] CONNECTING TO RMI SERVER: \nClient RMI exception: " + e + "\n");
                }
                retry = true;

                try {
                    Thread.sleep(Constants.SECONDS_BETWEEN_ATTEMPTS *1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } while (retry);
    }

    public void createGame(String nickname, int numPlayers) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(Constants.SERVERIP, Constants.RMIPort);
        server = (LobbyControllerInterface) registry.lookup(Constants.SERVERNAME);
        gameController = server.joinGame(nickname, numPlayers, listener);
        this.nickname = nickname;
    }

    public void joinFirstGameAvailable(String nickname) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(Constants.SERVERIP, Constants.RMIPort);
        server = (LobbyControllerInterface) registry.lookup(Constants.SERVERNAME);
        gameController = server.joinGame(nickname, 1, listener);
        this.nickname = nickname;
    }

    public void chooseColor(String nickname, String color) throws RemoteException {
        gameController.chooseColor(nickname, color, listener);
    }

    public void chooseHiddenGoal(String nickname, int choice) throws RemoteException {
        gameController.chooseGoal(nickname, choice, listener);
    }

    public void chooseInitialCardSide(String nickname, int choice) throws RemoteException {
        gameController.chooseInitialCardSide(nickname, choice==0, listener);
    }

    /*@Override
    public void sendMessage(ChatMessage msg) throws RemoteException {
        gameController.sentMessage(msg);
    }*/

    @Override
    public void playCard(String nickname, int cardIndex, boolean isFront, int x, int y) throws RemoteException {
        gameController.playCard(nickname, cardIndex, isFront, x, y, listener);
    }

    @Override
    public void drawCard(String nickname, int choice) throws RemoteException {
        gameController.drawCard(nickname, choice, listener);
    }

    @Override
    public void sendPublicMessage(String sender, String message) throws RemoteException {
        gameController.sentPublicMessage(sender, message);
    }

    @Override
    public void sendPrivateMessage(String sender, String receiver, String message) throws RemoteException {
        gameController.sentPrivateMessage(sender, receiver, message);
    }

    @Override
    public void heartbeat() throws RemoteException {
        if(gameController != null)
            gameController.heartbeat(nickname, listener);
    }

    @Override
    public void stopHeartbeat() {
        heartbeatSender.interrupt();
    }
}

