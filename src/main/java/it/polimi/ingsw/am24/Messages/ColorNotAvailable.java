package it.polimi.ingsw.am24.Messages;

public class ColorNotAvailable extends Message{
    private final String message = "no color available";

    public ColorNotAvailable() {
    }

    public String toString() {
        return message;
    }
}
