package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.messages.clientToServer.AddPlayerMessage;
import it.polimi.ingsw.am24.messages.SocketServerMessage;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ClientHandler extends Thread {
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GameControllerInterface gameController;
    private GameListenerClientSocket listener;

    private Queue<SocketServerMessage> messagesQueue = new LinkedList<SocketServerMessage>();

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.in = new ObjectInputStream(client.getInputStream());
        this.out = new ObjectOutputStream(client.getOutputStream());
        listener = new GameListenerClientSocket(out);
    }

    @Override
    public void run() {
        Thread executeMessagesThread = new Thread(this::executeMessages);
        executeMessagesThread.start();
        SocketServerMessage msg;

        while(!this.isInterrupted()) {
            try {
                msg =(SocketServerMessage) in.readObject();
                messagesQueue.add(msg);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[ERROR] Error reading socket message " + e.getMessage());
            }
        }
    }

    private void executeMessages() {
        SocketServerMessage msg;

        //try {
            while (!this.isInterrupted()) {
                msg = messagesQueue.poll();
                //todo add an execute method in all message classes
                if (msg instanceof AddPlayerMessage) {
                    //gameController = msg.execute(LobbyController.getInstance(), listener);
                } else {
                    //msg.execute(gameController, listener);
                }
            }
        /*} catch (RemoteException e) {
            throw new RuntimeException();
        }*/
    }
}
