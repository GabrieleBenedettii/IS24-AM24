package it.polimi.ingsw.am24.network.rmi;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.GameListenersHandlerClient;
import it.polimi.ingsw.am24.view.flow.CommonClientActions;
import it.polimi.ingsw.am24.view.flow.Flow;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements CommonClientActions {

    private final int port = 1234;
    private final String serverIp = "127.0.0.1";
    private final String serverName = "CodexNaturalis-Server";

    private static LobbyControllerInterface server;

    private GameControllerInterface gameController = null;

    private static GameListener listener;

    private final GameListenersHandlerClient gameListenersHandler;

    private Registry registry;

    private Flow flow;

    public RMIClient(Flow flow) {
        super();
        gameListenersHandler = new GameListenersHandlerClient(flow);
        connect();

        this.flow=flow;
    }

    public void connect() {
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                registry = LocateRegistry.getRegistry(serverIp, port);
                server = (LobbyControllerInterface) registry.lookup(serverName);

                listener = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

                System.out.println("Client RMI ready");
                retry = false;

            } catch (Exception e) {
                if (!retry) {
                    System.out.println("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                System.out.print("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + port + "' with name: '" + serverName + "'");

                /*i = 0;
                while (i < DefaultValue.seconds_between_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printAsyncNoLine(".");
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= DefaultValue.num_of_attempt_to_connect_toServer_before_giveup) {
                    printAsyncNoLine("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;*/
            }
        } while (retry);
    }

    public void createGame(String nickname, int numPlayers) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(serverIp, port);
        server = (LobbyControllerInterface) registry.lookup(serverName);
        gameController = server.joinGame(nickname, numPlayers, listener);
    }

    public void joinFirstGameAvailable(String nickname) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(serverIp, port);
        server = (LobbyControllerInterface) registry.lookup(serverName);
        gameController = server.joinGame(nickname, 1, listener);
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
    public void leave(String nick, int idGame) throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry(serverIp, port);
        server = (LobbyControllerInterface) registry.lookup(serverName);
        server.leaveGame(nickname, idGame, listener);
        gameController = null;
        nickname = null;
    }*/

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
}

