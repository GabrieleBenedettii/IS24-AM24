package it.polimi.ingsw.am24.messages;

public class ColorNotAvailableMessage extends Message{
    private final String message = "no color available";

    public ColorNotAvailableMessage() {
    }

    public String toString() {
        return message;
    }
}
