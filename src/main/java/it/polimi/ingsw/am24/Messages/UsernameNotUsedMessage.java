package it.polimi.ingsw.am24.Messages;

public class UsernameNotUsedMessage extends Message{
    private final String message = "username not used";

    public UsernameNotUsedMessage() {
    }

    @Override
    public String toString() {
        return message;
    }
}
