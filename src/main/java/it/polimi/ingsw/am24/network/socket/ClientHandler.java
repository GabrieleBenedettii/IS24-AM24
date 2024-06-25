package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.controller.LobbyController;
import it.polimi.ingsw.am24.network.socket.messages.SocketClientMessage;
import it.polimi.ingsw.am24.network.GameControllerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The ClientHandler class manages communication with a single client connected to the server.
 * It handles incoming messages, processes them, and sends appropriate responses.
 */
public class ClientHandler extends Thread {
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GameControllerInterface gameController;
    private GameListenerClientSocket listener;

    private Queue<SocketClientMessage> messagesQueue = new LinkedList<SocketClientMessage>();

    /**
     * Constructs a ClientHandler instance to handle communication with a client.
     *
     * @param client The Socket representing the client connection.
     * @throws IOException if an I/O error occurs when creating the input or output streams.
     */
    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.in = new ObjectInputStream(client.getInputStream());
        this.out = new ObjectOutputStream(client.getOutputStream());
        listener = new GameListenerClientSocket(out);
    }

    /**
     * Starts the thread for executing incoming messages from the client.
     * This method reads incoming messages, adds them to a queue, and executes them sequentially.
     */
    @Override
    public void run() {
        Thread executeMessagesThread = new Thread(this::executeMessages);
        executeMessagesThread.start();
        SocketClientMessage msg;

        while(!this.isInterrupted()) {
            try {
                msg = (SocketClientMessage) in.readObject();
                messagesQueue.add(msg);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[ERROR] Error reading socket message " + e.getMessage());
                this.interrupt();
                //break;
            }
        }
    }

    /**
     * Executes queued messages from the client.
     * Depending on the type of message, it either interacts with the LobbyController or
     * the active GameControllerInterface instance.
     */
    private void executeMessages() {
        SocketClientMessage msg;

        while (!this.isInterrupted()) {
            msg = messagesQueue.poll();
            if(msg == null) continue;

            try {
                if (msg.isForLobbyController())
                    gameController = msg.execute(listener, LobbyController.getInstance());
                else
                    msg.execute(listener, gameController);

            } catch (RemoteException e) {
                System.out.println("[ERROR] error during executing message: " + e);
            }
        }
    }
}
