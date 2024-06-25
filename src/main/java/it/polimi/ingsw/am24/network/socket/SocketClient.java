package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.network.GameListenerClient;
import it.polimi.ingsw.am24.network.heartbeat.HeartbeatSender;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import it.polimi.ingsw.am24.network.socket.messages.clientToServer.*;
import it.polimi.ingsw.am24.view.Flow;
import it.polimi.ingsw.am24.view.ClientActions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


/**
 * The SocketClient class represents a client that connects to a server using sockets
 * and interacts with the server by sending and receiving messages.
 */
public class SocketClient extends Thread implements ClientActions {
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private GameListenerClient listener;
    private final Flow flow;
    private final HeartbeatSender heartbeatSender;
    private String nickname;

    /**
     * Constructs a SocketClient instance and connects to the server using the specified flow.
     *
     * @param flow The Flow instance that manages the client's game flow.
     */
    public SocketClient(Flow flow) {
        this.flow = flow;
        connect(Constants.SERVERIP, Constants.SOCKETPort);
        listener = new GameListenerClient(flow);
        this.start();
        System.out.println("Client SOCKET ready");

        heartbeatSender = new HeartbeatSender(flow,this);
        heartbeatSender.start();
    }

    /**
     * Establishes a connection to the server at the specified IP address and port.
     *
     * @param ip   The IP address of the server.
     * @param port The port number of the server.
     */
    private void connect(String ip, int port) {
        boolean retry;
        do {
            retry = false;
            try {
                client = new Socket(ip, port);
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                System.out.println("[ERROR] connecting to socket server: \nClient SOCKET exception: " + e + "\n");
                retry = true;

                try {
                    Thread.sleep(Constants.SECONDS_BETWEEN_ATTEMPTS *1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } while(retry);
    }

    /**
     * Continuously listens for incoming messages from the server and processes them.
     */
    @Override
    public void run() {
        while(true) {
            try {
                SocketServerMessage message = (SocketServerMessage) in.readObject();
                message.execute(listener);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[ERROR] Connection lost: " + e + "\n");
                break;
            }
        }
    }

    /**
     * Sends a message to the server to create a new game lobby with the specified nickname and number of players.
     *
     * @param nickname   The nickname of the player creating the game.
     * @param numPlayers The number of players in the game.
     * @throws IOException if an I/O error occurs while sending the message.
     */
    @Override
    public void createGame(String nickname, int numPlayers) throws IOException {
        out.writeObject(new CreateGameMessage(nickname, numPlayers));
        out.flush();
        out.reset();
        this.nickname=nickname;
    }

    /**
     * Sends a message to the server to join the first available game lobby with the specified nickname.
     *
     * @param nickname The nickname of the player joining the game.
     * @throws IOException      if an I/O error occurs while sending the message.
     * @throws NotBoundException if the game lobby is not bound.
     */
    @Override
    public void joinFirstGameAvailable(String nickname) throws IOException, NotBoundException {
        out.writeObject(new JoinFirstGameAvailableMessage(nickname));
        out.flush();
        out.reset();
        this.nickname=nickname;
    }

    /**
     * Sends a message to the server for the player to choose a color.
     *
     * @param nickname The nickname of the player choosing the color.
     * @param color    The color chosen by the player.
     * @throws IOException if an I/O error occurs while sending the message.
     */
    @Override
    public void chooseColor(String nickname, String color) throws IOException {
        out.writeObject(new ChooseColorMessage(nickname, color));
        out.flush();
        out.reset();
    }

    /**
     * Sends a message to the server for the player to choose a hidden goal.
     *
     * @param nickname The nickname of the player choosing the hidden goal.
     * @param choice   The choice of the hidden goal.
     * @throws IOException if an I/O error occurs while sending the message.
     */
    @Override
    public void chooseHiddenGoal(String nickname, int choice) throws IOException {
        out.writeObject(new ChooseHiddenGoalMessage(nickname, choice));
        out.flush();
        out.reset();
    }

    /**
     * Sends a message to the server for the player to choose the initial card side.
     *
     * @param nickname The nickname of the player choosing the initial card side.
     * @param choice   The choice of the initial card side.
     * @throws IOException if an I/O error occurs while sending the message.
     */
    @Override
    public void chooseInitialCardSide(String nickname, int choice) throws IOException {
        out.writeObject(new ChooseInitialCardSideMessage(nickname, choice));
        out.flush();
        out.reset();
    }

    /**
     * Sends a message to the server for the player to draw a card.
     *
     * @param nickname The nickname of the player drawing the card.
     * @param choice   The choice related to drawing a card.
     * @throws IOException if an I/O error occurs while sending the message.
     */
    @Override
    public void drawCard(String nickname, int choice) throws IOException {
        out.writeObject(new DrawCardMessage(nickname, choice));
        out.flush();
        out.reset();
    }

    /**
     * Sends a message to the server for the player to play a card.
     *
     * @param nickname  The nickname of the player playing the card.
     * @param cardIndex The index of the card being played.
     * @param front     true if the front side of the card is being played, false for the back side.
     * @param x         The x-coordinate on the game board where the card is being played.
     * @param y         The y-coordinate on the game board where the card is being played.
     * @throws IOException if an I/O error occurs while sending the message.
     */
    @Override
    public void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException {
        out.writeObject(new PlayCardMessage(nickname, cardIndex, front, x, y));
        out.flush();
        out.reset();
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
        try {
            out.writeObject(new SendPublicMessageMessage(sender, message));
            out.flush();
            out.reset();
        }
        catch (IOException ignored){}

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
        try{
            out.writeObject(new SendPrivateMessageMessage(sender, receiver, message));
            out.flush();
            out.reset();
        }
        catch (IOException ignored){}

    }

    /**
     * Sends a heartbeat message to the server to indicate that the client is still active.
     *
     * @throws IOException if an I/O error occurs while sending the message.
     */
    @Override
    public void heartbeat() throws IOException {
        out.writeObject(new HeartBeatMessage(nickname));
        out.flush();
        out.reset();
    }

    /**
     * Stops the heartbeat sender thread.
     */
    @Override
    public void stopHeartbeat() {
        heartbeatSender.interrupt();
    }
}
