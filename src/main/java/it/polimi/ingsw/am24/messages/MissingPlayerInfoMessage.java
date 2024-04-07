package it.polimi.ingsw.am24.messages;

public class MissingPlayerInfoMessage extends Message{
    private final String message ="missing player information";

    public MissingPlayerInfoMessage() {
    }

    @Override
    public String toString() {
        return message;
    }
}
