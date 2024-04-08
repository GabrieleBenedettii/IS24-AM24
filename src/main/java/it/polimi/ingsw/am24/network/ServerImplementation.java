package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.*;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerImplementation extends UnicastRemoteObject implements Server {
    public static ServerImplementation instance;
    public final List<String> playingNicknames;
    public final Map<String, GameServer> disconnectedNicknames;
    public final Queue<Pair<Message,Client>> receivedMessages = new LinkedList<>();
    private final Queue<GameServer> lobbies;

    public ServerImplementation() throws RemoteException {
        super();
        this.playingNicknames = new ArrayList<>();
        this.disconnectedNicknames = new HashMap<>();
        this.lobbies = new LinkedBlockingQueue<>();

        //thread for reading messages
        new Thread(){
            @Override
            public void run(){
            while(true){
                if(isMessagesQueueEmpty() )
                    continue;
                Pair<Message,Client> pair = popFromMessagesQueue();
                try {
                    effectivelyHandleMessage(pair.getKey(), pair.getValue());
                } catch (RemoteException e){
                    System.out.println("Failed to handle message from client: " + e.getMessage());
                }
            }
            }

        }.start();
    }

    public static ServerImplementation getInstance() throws RemoteException{
        if( instance == null ) {
            instance = new ServerImplementation();
        }
        return instance;
    }

    public static void main(String[] args){
        //start rmi server on a thread
        new Thread(() -> {
            try {
                new ServerImplementation().startRMIServer();
            } catch (RemoteException e) {
                System.err.println("Cannot start RMI. This protocol will be disabled.");
            }
        }).start();
    }

    private void startRMIServer() throws RemoteException {
        //todo set port and serverName on a config or setting file
        int rmiPort = 1234;
        String serverName = "CodexNaturalis-Server";

        Registry registry = LocateRegistry.createRegistry(rmiPort);
        try {
            registry.bind(serverName,this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server ready");
    }

    @Override
    public void handleMessage(Message m, Client c) throws RemoteException {
        //System.out.println("Message: " + m + " from " + c);
        //handle messages from clients and add them to a queue
        synchronized (receivedMessages) {
            receivedMessages.add(new Pair(m, c));
        }
    }

    private void register(String nickname, int numOfPlayers, GameListener listener) throws RemoteException {
        System.out.println("Register request for player with nickname:  " + nickname + " and numOfPlayers: " + numOfPlayers);

        //todo set min and max num of player on a setting file
        if(numOfPlayers < 1 || numOfPlayers>4) {
            listener.update(new InvalidNumOfPlayersMessage());
            return;
        }
        //todo create message for null nickname
        if(nickname == null) {
            return;
        }
        System.out.println("Register request for player with nickname:  " + nickname + " parameters were valid. Logging player in");

        synchronized(playingNicknames) {
            //todo add the search of the nickname in the disconnected array
            synchronized (lobbies){
                //if the numOfPlayers is more than 1 -> create a new lobby
                if(numOfPlayers != 1){
                    GameServer lobby = new GameServer(numOfPlayers);
                    lobby.addPlayer(nickname, listener);
                    playingNicknames.add(nickname);
                    lobbies.add(lobby);
                }
                //otherwise add the player in an existing lobby
                else {
                    if(lobbies.peek() != null) {
                        GameServer lobby = lobbies.peek();
                        if(lobby.addPlayer(nickname, listener)) {
                            while (lobbies.peek() != null && lobbies.peek().getNumOfActivePlayers() == 0)
                                lobbies.poll();
                            System.out.println("Player added to an existing lobby");
                        }
                        playingNicknames.add(nickname);
                    }
                    //todo create a message for not existing lobbies
                }
            }
        }
    }

    private void chooseColor(String nickname, String color, GameListener listener) {
        //choice of the color for each player simultaneously
        //I need to find the player
        synchronized (playingNicknames) {
            Iterator<GameServer> it = lobbies.iterator();
            while (it.hasNext()) {
                if(it.next().isPlayerInGame(nickname)) break;
            }
            lobbies.peek().chooseColor(nickname,color,listener);
        }
    }

    private void reconnect(String nickname, GameListener listener) {
        synchronized (playingNicknames) {
            synchronized (disconnectedNicknames) {
                //todo check if the player exists in the disconnected array
                if(!(disconnectedNicknames.containsKey(nickname))) {
                    listener.update(new NickNameNotTakenMessage());
                    return;
                }
                playingNicknames.add(nickname);
            }
        }
    }

    private boolean isMessagesQueueEmpty() {
        synchronized (receivedMessages) {
            return receivedMessages.isEmpty();
        }
    }

    private Pair<Message,Client> popFromMessagesQueue() {
        synchronized (receivedMessages) {
            return receivedMessages.poll();
        }
    }

    private void effectivelyHandleMessage(Message m, Client client) throws RemoteException {
        //message for adding a new player
        if(m instanceof AddPlayerMessage) {
            register(((AddPlayerMessage) m).getNickname(), ((AddPlayerMessage) m).getNumPlayers(), ((message) -> {
                try {
                    client.update(message);
                } catch (RemoteException e) {
                    System.err.println("Cannot send message to client of: " + ((AddPlayerMessage) m).getNickname());
                    System.err.println("Error: " + e.getMessage());
                }
            }));
        }
        //message to try the reconnection or to check if the nickname is free
        else if(m instanceof ReconnectMessage) {
            reconnect(((ReconnectMessage) m).getNickname(), ((message) -> {
                try {
                    client.update(message);
                } catch (RemoteException e) {
                    System.err.println("Cannot send message to client of: " + ((ReconnectMessage) m).getNickname());
                    System.err.println("Error: " + e.getMessage());
                }
            }));
        }
        //message to choose a color
        else if(m instanceof ChosenColorMessage) {
            chooseColor(((ChosenColorMessage) m).getNickname(), ((ChosenColorMessage) m).getColor(), ((message) -> {
                try {
                    client.update(message);
                } catch (RemoteException e) {
                    System.err.println("Cannot send message to client of: " + ((ChosenColorMessage) m).getNickname());
                    System.err.println("Error: " + e.getMessage());
                }
            }));
        }
        else System.err.println("Message not recognized; ignoring it");
    }
}
