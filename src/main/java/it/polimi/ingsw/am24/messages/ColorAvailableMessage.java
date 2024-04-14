package it.polimi.ingsw.am24.messages;

public class ColorAvailableMessage extends Message{
    private final String message = "color available";

    public ColorAvailableMessage() {
    }

    public String toString() {
        return message;
    }
}
