package it.polimi.ingsw.am24.messages;

public class EndGameMessage extends Message {
    private final String winner;

    public EndGameMessage(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}
