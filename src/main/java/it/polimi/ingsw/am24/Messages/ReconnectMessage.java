package it.polimi.ingsw.am24.Messages;

public class ReconnectMessage extends Message{
    private final String username;

    public ReconnectMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
