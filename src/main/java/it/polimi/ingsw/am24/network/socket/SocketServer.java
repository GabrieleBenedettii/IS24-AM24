package it.polimi.ingsw.am24.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer extends Thread {
    private ServerSocket server;
    private List<ClientHandler> clients;

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
