package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.network.GameListenerClient;
import it.polimi.ingsw.am24.view.flow.Flow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient extends Thread /*implements CommonClientActions*/ {
    //todo implement all common client actions methods
    private final String ip = "127.0.0.1";
    private final int port = 8888;
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private GameListenerClient listener;
    private Flow flow;

    public SocketClient(Flow flow) {
        this.flow = flow;
        connect();
        listener = new GameListenerClient(flow);
        this.start();
    }

    private void connect() {
        do {
            try {
                client = new Socket(ip, port);
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                break;
            } catch(IOException e) {
                System.out.println("[ERROR] CONNECTING TO SOCKET SERVER: \nClient SOCKET exception: " + e + "\n");
            }
        } while(true);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Message message = (Message) in.readObject();
                //todo add an execute method in all message classes
                //message.execute(listener);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[ERROR] Connection lost: " + e + "\n");
            }
        }
    }

    //@Override
    public void createGame(String nickname, int numPlayers) throws IOException {
        out.writeObject(new AddPlayerMessage(nickname, numPlayers));
        out.flush();
        out.reset();
    }
}
