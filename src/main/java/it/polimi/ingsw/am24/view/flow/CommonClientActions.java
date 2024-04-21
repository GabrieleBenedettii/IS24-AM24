package it.polimi.ingsw.am24.view.flow;

import it.polimi.ingsw.am24.messages.Message;
import it.polimi.ingsw.am24.model.card.PlayableCard;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface CommonClientActions {
    void createGame(String nickname) throws IOException, NotBoundException;
    void joinFirstGameAvailable(String nickname) throws IOException, NotBoundException;
    void joinGame(String nickname, int gameId) throws IOException, NotBoundException;
    void reconnectGame(String nickname, int gameId) throws IOException, NotBoundException;
    void leaveGame(String nickname, int gameId) throws IOException, NotBoundException;
    boolean isMyTurn() throws RemoteException;
    void drawCardfromPublicBoard(PlayableCard card);
    void positionCard(int row, int column);
    void sendMessage(Message msg) throws RemoteException;
    void heartbeat() throws RemoteException;
}
