package it.polimi.ingsw.am24.messages;

public class NickNameTakenMessage extends Message{
    private final String message ="nickname already taken";

    public NickNameTakenMessage() {
    }

    @Override
    public String toString() {
        return message;
    }
}
