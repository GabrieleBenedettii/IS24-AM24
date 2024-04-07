package it.polimi.ingsw.am24.messages;

public class NumPlayersNotValid extends Message{
    private final String message ="Invalid players number";

    public NumPlayersNotValid() {
    }

    @Override
    public String toString() {
        return message;
    }
}
