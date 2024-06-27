package it.polimi.ingsw.am24.main;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.network.rmi.RMIServer;
import it.polimi.ingsw.am24.network.socket.SocketServer;
/**
 * The Server class initializes and starts the game server components.
 * It binds the RMI server and starts the socket server to listen for incoming connections.
 */
public class Server {

    /**
     * The main method is the entry point of the server application.
     * It binds the RMI server and starts the socket server to listen on the specified port.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        RMIServer.bind();
        SocketServer socketServer = new SocketServer();
        socketServer.start(Constants.SOCKETPort);
    }
}

