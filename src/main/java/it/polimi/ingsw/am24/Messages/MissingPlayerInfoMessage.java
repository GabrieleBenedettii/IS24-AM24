package it.polimi.ingsw.am24.Messages;

public class MissingPlayerInfoMessage extends Message{
    private final String message ="missing player information";

    public MissingPlayerInfoMessage() {
    }

    @Override
    public String toString() {
        return message;
    }
}
