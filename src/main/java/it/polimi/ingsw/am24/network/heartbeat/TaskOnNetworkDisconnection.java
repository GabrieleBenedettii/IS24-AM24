package it.polimi.ingsw.am24.network.heartbeat;

import it.polimi.ingsw.am24.view.Flow;

import java.util.TimerTask;

/**
 * The TaskOnNetworkDisconnection class represents a task to handle actions when a network disconnection occurs.
 * It extends TimerTask and is used by Timer to schedule actions on a network disconnection event.
 */
public class TaskOnNetworkDisconnection extends TimerTask {
    private Flow flow;

    /**
     * Constructs a TaskOnNetworkDisconnection with the specified Flow instance.
     *
     * @param flow The Flow instance that manages game flow on the client side.
     */
    public TaskOnNetworkDisconnection(Flow flow) {
        this.flow=flow;
    }

    /**
     * Executes the action defined for network disconnection in the Flow instance.
     */
    @Override
    public void run() {
        flow.noConnectionError();
    }
}
