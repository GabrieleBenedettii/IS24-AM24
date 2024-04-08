package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.network.Server;

public class ChangeServerMessage extends Message {
    private Server server;

    public ChangeServerMessage(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
