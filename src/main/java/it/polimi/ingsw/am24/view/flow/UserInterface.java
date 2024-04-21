package it.polimi.ingsw.am24.view.flow;

import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.listeners.ViewListener;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;
import it.polimi.ingsw.am24.network.Client;
import it.polimi.ingsw.am24.network.ClientImplementation;
import it.polimi.ingsw.am24.network.Server;
import it.polimi.ingsw.am24.view.View;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public abstract class UserInterface implements Runnable, View {
    private Client client;
    private final List<ViewListener> listeners;

    public UserInterface() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addListener(ViewListener vl) {
        synchronized (listeners) {
           listeners.add(vl);
        }
    }

    public void removeListeners(){
        synchronized (listeners) {
            listeners.clear();
        }
    }

    @Override
    public void notifyListeners(Message m) {
        synchronized (listeners) {
            for(ViewListener listener : listeners) {
                listener.handleMessage(m);
            }
        }
    }

    protected void setUpRMIClient(String ip, int port) throws RemoteException, NotBoundException {
        //todo check if ip and port are correct
        System.out.println("Connecting to RMI server : " + ip + " on port " + port + "...");

        Registry registry = LocateRegistry.getRegistry(ip, port);
        Server server = (Server) registry.lookup("CodexNaturalis-Server");

        this.client = ClientImplementation.getInstance(this, server);
    }

    //connect a player to a new or existing lobby
    protected void doConnect(String nickname, int numPlayers) {
        notifyListeners(new AddPlayerMessage(nickname, numPlayers));
    }

    //reconnect a player or check if the nickname is already taken
    protected void doReconnect(String nickname) {
        notifyListeners(new ReconnectMessage(nickname));
    }

    //choice of the color from a player
    protected void doChooseColor(String nickname, String color) {
        notifyListeners(new ChosenColorMessage(nickname, color));
    }

    //choice of the hidden goal
    protected void doChooseHiddenGoal(String nickname, int cardId) {
        notifyListeners(new SecretObjectiveChosenMessage(nickname, cardId));
    }

    //choice of the initial card side
    protected void doChooseInitialCardSide(String nickname, int isFront) {
        notifyListeners(new InitialCardSideMessage(nickname, isFront == 0 ? true : false));
    }
    protected abstract void showLogo();
    protected abstract void showNewGameMessage(String firstPlayer);
    protected abstract void showJoinFirstAvailableGameMessage(String player);
    protected abstract void show_noConnectionError();
}
