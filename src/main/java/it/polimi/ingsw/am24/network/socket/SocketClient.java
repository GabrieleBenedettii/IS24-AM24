package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.messages.clientToServer.*;
import it.polimi.ingsw.am24.network.GameListenerClient;
import it.polimi.ingsw.am24.view.flow.CommonClientActions;
import it.polimi.ingsw.am24.view.flow.Flow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SocketClient extends Thread implements CommonClientActions {
    //todo implement all common client actions methods
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private GameListenerClient listener;
    private final Flow flow;

    public SocketClient(Flow flow) {
        this.flow = flow;
        connect("127.0.0.1", 8888);
        listener = new GameListenerClient(flow);
        this.start();
        System.out.println("Client SOCKET ready");
    }

    private void connect(String ip, int port) {
        boolean retry = true;
        do {
            try {
                client = new Socket(ip, port);
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                retry = false;
            } catch (IOException e) {
                System.out.println("[ERROR] connecting to socket server: \nClient SOCKET exception: " + e + "\n");
            }
        } while(retry);
    }

    @Override
    public void run() {
        while(true) {
            try {
                SocketServerMessage message = (SocketServerMessage) in.readObject();
                message.execute(listener);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[ERROR] Connection lost: " + e + "\n");
            }
        }
    }

    @Override
    public void createGame(String nickname, int numPlayers) throws IOException {
        out.writeObject(new CreateGameMessage(nickname, numPlayers));
        out.flush();
        out.reset();
    }

    @Override
    public void joinFirstGameAvailable(String nickname) throws IOException, NotBoundException {
        out.writeObject(new JoinFirstGameAvailableMessage(nickname));
        out.flush();
        out.reset();
    }

    @Override
    public void chooseColor(String nickname, String color) throws IOException {
        out.writeObject(new ChooseColorMessage(nickname, color));
        out.flush();
        out.reset();
    }

    @Override
    public void chooseHiddenGoal(String nickname, int choice) throws IOException {
        out.writeObject(new ChooseHiddenGoalMessage(nickname, choice));
        out.flush();
        out.reset();
    }

    @Override
    public void chooseInitialCardSide(String nickname, int choice) throws IOException {
        out.writeObject(new ChooseInitialCardSideMessage(nickname, choice));
        out.flush();
        out.reset();
    }

    @Override
    public void drawCard(String nickname, int choice) throws IOException {
        out.writeObject(new DrawCardMessage(nickname, choice));
        out.flush();
        out.reset();
    }

    @Override
    public void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException {
        out.writeObject(new PlayCardMessage(nickname, cardIndex, front, x, y));
        out.flush();
        out.reset();
    }

    @Override
    public void sendPublicMessage(String sender, String message) throws RemoteException {
        try {
            out.writeObject(new SendPublicMessageMessage(sender, message));
            out.flush();
            out.reset();
        }
        catch (IOException ignored){}

    }

    @Override
    public void sendPrivateMessage(String sender, String receiver, String message) throws RemoteException {
        try{
            out.writeObject(new SendPrivateMessageMessage(sender, receiver, message));
            out.flush();
            out.reset();
        }
        catch (IOException ignored){}

    }
}
