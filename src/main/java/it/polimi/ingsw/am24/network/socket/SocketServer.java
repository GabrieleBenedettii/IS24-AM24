package it.polimi.ingsw.am24.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class SocketServer extends Thread {
    private int port = 8888;
    private ServerSocket server;
    private List<ClientHandler> clients;

    public void start() {
        try {
            server = new ServerSocket(port);
            this.start();
            System.out.println("Server socket ready");
        } catch (IOException e) {
            System.out.println("[ERROR] Error starting socket server: " + e);
        }
    }

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
