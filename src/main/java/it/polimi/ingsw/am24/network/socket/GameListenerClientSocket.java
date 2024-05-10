package it.polimi.ingsw.am24.network.socket;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.messages.servetToClient.*;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameListenerClientSocket implements GameListener, Serializable {
    //todo add all game listener methods
    private final ObjectOutputStream out;

    public GameListenerClientSocket(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void invalidNumPlayers() throws RemoteException {
        try {
            out.writeObject(new InvalidNumPlayersMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void playerJoined(ArrayList<String> players) throws RemoteException {
        try {
            out.writeObject(new PlayerJoinedMessage(players));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    //@Override
    public void noLobbyAvailable() throws RemoteException {
        try {
            out.writeObject(new NoLobbyAvaiableMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void nicknameAlreadyUsed() throws RemoteException {
        try {
            out.writeObject(new NicknameAlreadyUsedMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void availableColors(ArrayList<String> colors) throws RemoteException {
        try {
            out.writeObject(new AvailableColorsMessage(colors));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, PublicBoardView publicBoardView) throws RemoteException {
        try {
            out.writeObject(new HiddenGoalChoiceMessage(cardViews, publicBoardView));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void initialCardSide(GameCardView front, GameCardView back) throws RemoteException {
        try {
            out.writeObject(new InitialCardSideMessage(front, back));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void beginTurn(GameView gameView) throws RemoteException {
        try {
            out.writeObject(new BeginTurnMessage(gameView));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void invalidPositioning() throws RemoteException {
        try {
            out.writeObject(new InvalidPositioningMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void requirementsNotMet() throws RemoteException {
        try {
            out.writeObject(new RequirementsNotMetMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void beginDraw() throws RemoteException {
        try {
            out.writeObject(new BeginDrawMessage());
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void wrongCardPlay(GameView gameView) throws RemoteException {
        try {
            out.writeObject(new WrongCardPlayMessage(gameView));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void gameEnded(String winner, HashMap<String, Integer> rank) throws RemoteException {
        try {
            out.writeObject(new GameEndedMessage(winner,rank));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }

    @Override
    public void sentMessage(String message) throws RemoteException {
        try {
            out.writeObject(new SentMessageMessage(message));
            out.flush();
            out.reset();

        } catch (IOException ignored) {}
    }
}
