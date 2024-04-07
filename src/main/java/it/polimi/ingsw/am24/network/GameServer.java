package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.Controller.Controller;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

public class GameServer extends UnicastRemoteObject implements Server {
    private final Controller controller;
    private ServerImplementation server;
    private final String[] playerUsernames;
    private final Queue<Message> receivedMessages = new LinkedList<>();

    public GameServer(int numPlayers) throws RemoteException {
        super();
        this.controller = new Controller(numPlayers);
        this.playerUsernames = new String[numPlayers];

        //thread for reading messages from the queue
        new Thread(){
            @Override
            public void run(){
                while(true){

                    if( isMessagesQueueEmpty() ) continue;

                    Message message = popFromMessagesQueue();
                    try {
                        effectivelyHandleMessage(message);
                    }catch (RemoteException ex){
                        System.out.println("Failed to handle message of client: " + ex.getMessage());
                    }
                }
            }

        }.start();
    }

    @Override
    public void handleMessage(Message m, Client c) throws RemoteException {
        synchronized (receivedMessages) {
            receivedMessages.add(m);
        }
    }

    protected boolean addPlayer(String username, GameListener listener) {
        boolean res;
        synchronized (playerUsernames) {
            res = this.controller.addNewPlayer(username, listener);
            //listener.update(new GameServerMessage(this));
            for(int i = 0; i < playerUsernames.length; i++){
                if(playerUsernames[i] == null){
                    playerUsernames[i] = username;
                    break;
                }
            }
        }
        return res;
    }

    protected boolean chooseColor(String username, String color, GameListener listener) {
        boolean res;
        synchronized (playerUsernames) {
            res = this.controller.chooseColor(username, color, listener);
        }
        return res;
    }

    private boolean isMessagesQueueEmpty() {
        synchronized (receivedMessages) {
            return receivedMessages.isEmpty();
        }
    }

    private Message popFromMessagesQueue() {
        synchronized (receivedMessages) {
            return receivedMessages.poll();
        }
    }

    private void effectivelyHandleMessage(Message m) throws RemoteException {

    }

    protected int getNumOfActivePlayers() {
        return this.controller.getNumOfActivePlayers();
    }

    protected boolean isPlayerInGame(String username) {
        for(int i = 0; i < playerUsernames.length; i++){
            if(playerUsernames[i] != null && playerUsernames[i].equals(username)) return true;
        }
        return false;
    }
}
