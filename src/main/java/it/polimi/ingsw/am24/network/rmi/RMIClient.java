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

/**
 * The RMIClient class represents a client that interacts with a remote server using RMI (Remote Method Invocation).
 * It implements the ClientActions interface and manages game-related actions and communications.
 */
public class RMIClient implements ClientActions {

    private static LobbyControllerInterface server;

    private GameControllerInterface gameController = null;

    private String nickname;

    private static GameListener listener;

    private final GameListenerClient gameListenersHandler;

    private Registry registry;

    private final Flow flow;
    private HeartbeatSender heartbeatSender;

    /**
     * Constructs an RMIClient with the specified Flow instance.
     *
     * @param flow The Flow instance that manages game flow on the client side.
     */
    public RMIClient(Flow flow) {
        super();
        gameListenersHandler = new GameListenerClient(flow);
        this.flow = flow;
        connect();

        heartbeatSender = new HeartbeatSender(flow,this);
        heartbeatSender.start();
    }

    /**
     * Establishes connection to the RMI server.
     */
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

    /**
     * Creates a new game lobby with the specified nickname and number of players.
     *
     * @param nickname   The nickname of the player creating the game.
     * @param numPlayers The number of players in the game.
     * @throws RemoteException  if a remote communication issue occurs.
     * @throws NotBoundException if the server is not bound.
     */
    public void createGame(String nickname, int numPlayers) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(Constants.SERVERIP, Constants.RMIPort);
        server = (LobbyControllerInterface) registry.lookup(Constants.SERVERNAME);
        gameController = server.joinGame(nickname, numPlayers, listener);
        this.nickname = nickname;
    }

    /**
     * Joins the first available game lobby with the specified nickname.
     *
     * @param nickname The nickname of the player joining the game.
     * @throws RemoteException  if a remote communication issue occurs.
     * @throws NotBoundException if the server is not bound.
     */
    public void joinFirstGameAvailable(String nickname) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(Constants.SERVERIP, Constants.RMIPort);
        server = (LobbyControllerInterface) registry.lookup(Constants.SERVERNAME);
        gameController = server.joinGame(nickname, 1, listener);
        this.nickname = nickname;
    }

    /**
     * Allows the player to choose a color for their character in the game.
     *
     * @param nickname The nickname of the player choosing the color.
     * @param color    The color chosen by the player.
     * @throws RemoteException if a remote communication issue occurs.
     */
    public void chooseColor(String nickname, String color) throws RemoteException {
        gameController.chooseColor(nickname, color, listener);
    }

    /**
     * Allows the player to choose a hidden goal in the game.
     *
     * @param nickname The nickname of the player choosing the goal.
     * @param choice   The ID of the goal chosen by the player.
     * @throws RemoteException if a remote communication issue occurs.
     */
    public void chooseHiddenGoal(String nickname, int choice) throws RemoteException {
        gameController.chooseGoal(nickname, choice, listener);
    }

    /**
     * Allows the player to choose the initial side of a card in the game.
     *
     * @param nickname The nickname of the player choosing the card side.
     * @param choice   The choice indicating the initial side of the card (0 for front, 1 for back).
     * @throws RemoteException if a remote communication issue occurs.
     */
    public void chooseInitialCardSide(String nickname, int choice) throws RemoteException {
        gameController.chooseInitialCardSide(nickname, choice==0, listener);
    }

    /*@Override
    public void sendMessage(ChatMessage msg) throws RemoteException {
        gameController.sentMessage(msg);
    }*/

    /**
     * Allows the player to play a card on the game board.
     *
     * @param nickname  The nickname of the player playing the card.
     * @param cardIndex The index of the card being played.
     * @param isFront   true if the front side of the card is being played, false for the back side.
     * @param x         The x-coordinate on the game board where the card is being played.
     * @param y         The y-coordinate on the game board where the card is being played.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void playCard(String nickname, int cardIndex, boolean isFront, int x, int y) throws RemoteException {
        gameController.playCard(nickname, cardIndex, isFront, x, y, listener);
    }

    /**
     * Allows the player to draw a card.
     *
     * @param nickname The nickname of the player drawing the card.
     * @param choice   The choice related to drawing a card.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void drawCard(String nickname, int choice) throws RemoteException {
        gameController.drawCard(nickname, choice, listener);
    }

    /**
     * Sends a public message from a player to all players in the game.
     *
     * @param sender  The nickname of the player sending the message.
     * @param message The message content.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void sendPublicMessage(String sender, String message) throws RemoteException {
        gameController.sentPublicMessage(sender, message);
    }

    /**
     * Sends a private message from one player to another player.
     *
     * @param sender   The nickname of the player sending the message.
     * @param receiver The nickname of the player receiving the message.
     * @param message  The message content.
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void sendPrivateMessage(String sender, String receiver, String message) throws RemoteException {
        gameController.sentPrivateMessage(sender, receiver, message);
    }

    /**
     * Sends a heartbeat signal to the server to indicate that the client is still active.
     *
     * @throws RemoteException if a remote communication issue occurs.
     */
    @Override
    public void heartbeat() throws RemoteException {
        if(gameController != null)
            gameController.heartbeat(nickname, listener);
    }

    /**
     * Stops the heartbeat sender thread.
     */
    @Override
    public void stopHeartbeat() {
        heartbeatSender.interrupt();
    }
}

