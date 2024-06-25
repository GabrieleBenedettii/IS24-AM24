package it.polimi.ingsw.am24.network.heartbeat;

import it.polimi.ingsw.am24.view.Flow;

import java.util.TimerTask;

public class TaskOnNetworkDisconnection extends TimerTask {
    private Flow flow;
    public TaskOnNetworkDisconnection(Flow flow) {
        this.flow=flow;
    }

    @Override
    public void run() {
        flow.noConnectionError();
    }
}
