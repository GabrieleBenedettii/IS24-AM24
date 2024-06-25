package it.polimi.ingsw.am24.network.heartbeat;

import it.polimi.ingsw.am24.view.Flow;
import it.polimi.ingsw.am24.view.ClientActions;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * The HeartbeatSender class represents a thread that sends periodic heartbeat signals to a server,
 * indicating that the client is still active and connected.
 */
public class HeartbeatSender extends Thread {

    private Flow flow;
    private ClientActions server;

    /**
     * Constructs a HeartbeatSender with the specified Flow and ClientActions instances.
     *
     * @param flow   The Flow instance that manages game flow on the client side.
     * @param server The ClientActions instance for communication with the server.
     */
    public HeartbeatSender(Flow flow, ClientActions server) {
        this.flow=flow;
        this.server=server;
    }

    /**
     * Starts the heartbeat thread.
     */
    @Override
    public void run() {
        //For the heartbeat
        while (!Thread.interrupted()) {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(flow);
            timer.schedule(task, 3000);

            //send heartbeat so the server knows I am still online
            try {
                server.heartbeat();
            } catch (RemoteException | SocketException e) {
                //System.out.println("\nConnection to server lost! Impossible to send heartbeat...");
                this.interrupt();
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            timer.cancel();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

    }

}
