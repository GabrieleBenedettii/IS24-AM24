package it.polimi.ingsw.am24.messages;

public class NoLobbyMessage extends Message{
    private final String message = "no lobby available";

    public String getMessage() {
        return message;
    }
}