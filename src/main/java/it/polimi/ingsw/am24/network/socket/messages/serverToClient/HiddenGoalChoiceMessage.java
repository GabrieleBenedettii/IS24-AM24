package it.polimi.ingsw.am24.network.socket.messages.serverToClient;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.network.socket.messages.SocketServerMessage;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code HiddenGoalChoiceMessage} class represents a message sent from the server to the client
 * to convey the choices for hidden goals during gameplay.
 */
public class HiddenGoalChoiceMessage extends SocketServerMessage {
    /**
     * The list of views representing the choices for hidden goals.
     */
    private final ArrayList<GameCardView> views = new ArrayList<>();
    /**
     * The game view associated with the hidden goal choices.
     */
    private final GameView gameView;

    /**
     * Constructs a {@code HiddenGoalChoiceMessage} with the views of hidden goal choices and the associated game view.
     *
     * @param views    the list of game card views representing the choices for hidden goals
     * @param gameView the game view associated with the hidden goal choices
     */
    public HiddenGoalChoiceMessage(List<GameCardView> views, GameView gameView) {
        this.views.add(views.get(0));
        this.views.add(views.get(1));
        this.gameView = gameView;
    }

    /**
     * Executes the hidden goal choice message on the client.
     * This method notifies the client listener about the hidden goal choices and the associated game view.
     *
     * @param listener the game listener interface on the client
     * @throws RemoteException if there is a communication-related exception during execution
     */
    @Override
    public void execute(GameListener listener) throws RemoteException {
        listener.hiddenGoalChoice(views, gameView);
    }
}
