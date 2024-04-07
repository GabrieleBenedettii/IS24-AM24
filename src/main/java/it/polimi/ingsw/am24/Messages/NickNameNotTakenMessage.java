package it.polimi.ingsw.am24.Messages;

public class NickNameNotTakenMessage extends Message{
    private final String message ="nickname not taken";

    public NickNameNotTakenMessage() {
    }

    @Override
    public String toString() {
        return message;
    }
}
