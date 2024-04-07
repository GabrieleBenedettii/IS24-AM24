package it.polimi.ingsw.am24.Messages;

public class SecretObjectiveChosenMessage extends Message{
    private final String message = "secrete objective chosen";

    public SecretObjectiveChosenMessage() {
    }

    @Override
    public String toString() {
        return message;
    }
}
