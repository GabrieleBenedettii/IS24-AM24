package it.polimi.ingsw.am24.view.flow;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface CommonClientActions {
    void createGame(String nickname, int numPlayers) throws IOException, NotBoundException;
    void joinFirstGameAvailable(String nickname) throws IOException, NotBoundException;
    void chooseColor(String nickname, String color) throws IOException;
    void chooseHiddenGoal(String nickname, int choice) throws IOException;
    void chooseInitialCardSide(String nickname, int choice) throws IOException;
    void drawCard(String nickname, int choice) throws IOException;
    void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException;
    void sendPublicMessage(String sender, String message) throws RemoteException;
    void sendPrivateMessage(String sender, String receiver, String message) throws RemoteException;
    void heartbeat() throws IOException;
    //void leaveGame(String nickname, int gameId) throws IOException, NotBoundException;
    //boolean isMyTurn() throws RemoteException;
}
