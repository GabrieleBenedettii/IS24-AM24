package it.polimi.ingsw.am24.messages;

public class ColorAvailable extends Message{
    private final String message = "color available";

    public ColorAvailable() {
    }

    public String toString() {
        return message;
    }
}
