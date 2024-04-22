package it.polimi.ingsw.am24.view.flow;

import java.io.IOException;
import java.rmi.NotBoundException;

public interface CommonClientActions {
    void createGame(String nickname, int numPlayers) throws IOException, NotBoundException;
    void joinFirstGameAvailable(String nickname) throws IOException, NotBoundException;
    void chooseColor(String nickname, String color) throws IOException;
    void chooseHiddenGoal(String nickname, int choice) throws IOException;
    void chooseInitialCardSide(String nickname, int choice) throws IOException;
    void drawCard(String nickname, int choice) throws IOException;
    void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException;
    //void sendMessage(ChatMessage msg) throws RemoteException;
    //void leaveGame(String nickname, int gameId) throws IOException, NotBoundException;
    //boolean isMyTurn() throws RemoteException;
}
