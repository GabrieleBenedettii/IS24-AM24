package it.polimi.ingsw.am24.main;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.network.rmi.RMIServer;
import it.polimi.ingsw.am24.network.socket.SocketServer;

public class Server {

    public static void main(String[] args) {
        RMIServer.bind();
        SocketServer socketServer = new SocketServer();
        socketServer.start(Constants.SOCKETPort);
    }
}

