package it.polimi.ingsw.am24.Messages;

public class NumPlayersNotValid extends Message{
    private final String message ="Invalid players number";

    public NumPlayersNotValid() {
    }

    @Override
    public String toString() {
        return message;
    }
}
