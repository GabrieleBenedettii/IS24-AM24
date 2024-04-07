package it.polimi.ingsw.am24.messages;

public class InvalidUsernameMessage extends Message{
    private final String message ="Invalid username";

    public InvalidUsernameMessage() {
    }

    @Override
    public String toString() {
        return message;
    }
}
