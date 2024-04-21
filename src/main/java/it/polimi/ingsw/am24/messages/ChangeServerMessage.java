package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.network.rmi.LobbyControllerInterface;

public class ChangeServerMessage extends Message {
    private LobbyControllerInterface server;

    public ChangeServerMessage(LobbyControllerInterface server) {
        this.server = server;
    }

    public LobbyControllerInterface getServer() {
        return server;
    }
}
