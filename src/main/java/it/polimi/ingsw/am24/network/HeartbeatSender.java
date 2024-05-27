package it.polimi.ingsw.am24.network;

import it.polimi.ingsw.am24.view.flow.CommonClientActions;
import it.polimi.ingsw.am24.view.flow.Flow;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;



public class HeartbeatSender extends Thread {

    private Flow flow;
    private CommonClientActions server;

    public HeartbeatSender(Flow flow, CommonClientActions server) {
        this.flow=flow;
        this.server=server;
    }


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
