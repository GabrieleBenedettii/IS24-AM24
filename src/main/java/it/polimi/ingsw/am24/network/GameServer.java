package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.Controller.GameController;
import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.*;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

public class GameServer extends UnicastRemoteObject implements Server {
    private final GameController gameController;
    private ServerImplementation server;
    private final String[] playerNicknames;
    private final Queue<Pair<Message,Client>> receivedMessages = new LinkedList<>();

    public GameServer(int numPlayers) throws RemoteException {
        super();
        this.gameController = new GameController(numPlayers);
        this.playerNicknames = new String[numPlayers];

        //thread for reading messages from the queue
        new Thread(){
            @Override
            public void run(){
            while(true){

                if( isMessagesQueueEmpty() ) continue;

                Pair<Message,Client> pair = popFromMessagesQueue();
                try {
                    effectivelyHandleMessage(pair.getKey(),pair.getValue());
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
            receivedMessages.add(new Pair<>(m,c));
        }
    }

    protected boolean addPlayer(String nickname, GameListener listener) {
        boolean res;
        synchronized (playerNicknames) {
            res = this.gameController.addNewPlayer(nickname, listener);
            for(int i = 0; i < playerNicknames.length; i++){
                if(playerNicknames[i] == null){
                    playerNicknames[i] = nickname;
                    break;
                }
            }
        }
        return res;
    }

    protected boolean chooseColor(String nickname, String color, GameListener listener) {
        boolean res;
        synchronized (playerNicknames) {
            res = this.gameController.chooseColor(nickname, color, listener);
            listener.update(new ChangeServerMessage(this));
        }
        return res;
    }

    protected boolean chooseHiddenGoal(String nickname, int cardId, GameListener listener) {
        boolean res;
        synchronized (playerNicknames) {
            res = this.gameController.chooseGoal(nickname, cardId, listener);
        }
        return res;
    }

    protected boolean chooseInitialCardSide(String nickname, boolean isFront, GameListener listener) {
        boolean res;
        synchronized (playerNicknames) {
            res = this.gameController.chooseInitialCardSide(nickname, isFront, listener);
        }
        return res;
    }

    protected boolean playCard(String nickname, int cardIndex, boolean isFront, int x, int y, GameListener listener) {
        boolean res;
        synchronized (playerNicknames) {
            res = this.gameController.playCard(nickname, cardIndex, isFront, x, y, listener);
        }
        return res;
    }

    protected boolean drawCard(String nickname, int cardIndex, GameListener listener) {
        boolean res;
        synchronized (playerNicknames) {
            res = this.gameController.drawCard(nickname, cardIndex, listener);
        }
        return res;
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

    private void effectivelyHandleMessage(Message m, Client c) throws RemoteException {
        //message for the choice of the hidden goal
        if(m instanceof SecretObjectiveChosenMessage) {
            chooseHiddenGoal(((SecretObjectiveChosenMessage) m).getNickname(), ((SecretObjectiveChosenMessage) m).getCardId(), ((message) -> {
                try {
                    c.update(message);
                } catch (RemoteException e) {
                    System.err.println("Cannot send message to client of: " + ((SecretObjectiveChosenMessage) m).getNickname());
                    System.err.println("Error: " + e.getMessage());
                }
            }));
        }
        //message
        else if(m instanceof InitialCardSideMessage) {
            //message for the choice of the initial card side
            chooseInitialCardSide(((InitialCardSideMessage) m).getNickname(), ((InitialCardSideMessage) m).getFront(), ((message) -> {
                try {
                    c.update(message);
                } catch (RemoteException e) {
                    System.err.println("Cannot send message to client of: " + ((InitialCardSideMessage) m).getNickname());
                    System.err.println("Error: " + e.getMessage());
                }
            }));
        }
        else if(m instanceof PlayCardMessage) {
            playCard(((PlayCardMessage) m).getNickname(), ((PlayCardMessage) m).getCardIndex(), ((PlayCardMessage) m).getFront(),
                    ((PlayCardMessage) m).getX(), ((PlayCardMessage) m).getY(), ((message) -> {
                try {
                    c.update(message);
                } catch (RemoteException e) {
                    System.err.println("Cannot send message to client of: " + ((PlayCardMessage) m).getNickname());
                    System.err.println("Error: " + e.getMessage());
                }
            }));
        }
        else if(m instanceof DrawCardMessage) {
            drawCard(((DrawCardMessage) m).getNickname(), ((DrawCardMessage) m).getCard(), ((message) -> {
                try {
                    c.update(message);
                } catch (RemoteException e) {
                    System.err.println("Cannot send message to client of: " + ((DrawCardMessage) m).getNickname());
                    System.err.println("Error: " + e.getMessage());
                }
            }));
        }
    }

    protected int getNumOfActivePlayers() {
        return this.gameController.getNumOfActivePlayers();
    }

    //check if a nickname is in this lobby
    protected boolean isPlayerInGame(String nickname) {
        for(int i = 0; i < playerNicknames.length; i++){
            if(playerNicknames[i] != null && playerNicknames[i].equals(nickname)) return true;
        }
        return false;
    }
}
