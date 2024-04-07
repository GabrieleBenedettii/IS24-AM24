package it.polimi.ingsw.am24.messages;

public class InvalidNumOfPlayersMessage extends Message {
    private final String message = "Invalid number of players";

    public InvalidNumOfPlayersMessage() {}

    public String getMessage() {
        return message;
    }
}
