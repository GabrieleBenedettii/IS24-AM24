package it.polimi.ingsw.am24.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * The SocketServer class represents a server that listens for incoming socket connections
 * from clients and manages multiple client connections using multithreading.
 */
public class SocketServer extends Thread {
    private ServerSocket server;
    private List<ClientHandler> clients;

    /**
     * Starts the socket server on the specified port.
     *
     * @param port The port number on which the server will listen for incoming connections.
     */
    public void start(int port) {
        try {
            server = new ServerSocket(port);
            clients = new ArrayList<>();
            this.start();
            System.out.println("Server socket ready");
        } catch (IOException e) {
            System.err.println("[ERROR] starting socket server: " + e);
        }
    }

    /**
     * Continuously accepts incoming client connections and starts a new ClientHandler thread
     * for each client.
     */
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                clients.add(new ClientHandler(server.accept()));
                clients.get(clients.size() - 1).start();
                System.out.println("[SOCKET] New client connected");
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Error accepting socket connections");
        }
    }
}
