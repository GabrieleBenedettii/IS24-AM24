package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.messages.AddPlayerMessage;
import it.polimi.ingsw.am24.messages.Message;
import it.polimi.ingsw.am24.network.rmi.GameControllerInterface;
import it.polimi.ingsw.am24.Controller.LobbyController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;

public class ClientHandler extends Thread {
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private GameControllerInterface gameController;
    private GameListenerClientSocket listener;

    private Queue<Message> messagesQueue = new LinkedList<Message>();

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
        Message msg;

        while(!this.isInterrupted()) {
            try {
                msg =(Message) in.readObject();
                messagesQueue.add(msg);

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[ERROR] Error reading socket message " + e.getMessage());
            }
        }
    }

    private void executeMessages() {
        Message msg;

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
