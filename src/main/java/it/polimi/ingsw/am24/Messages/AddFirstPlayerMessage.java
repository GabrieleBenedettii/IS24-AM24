package it.polimi.ingsw.am24.Messages;

public class AddFirstPlayerMessage extends Message{
    private final String username;
    private final int numPlayers;  //num = [2;4] => first player, num = 1 => other

    public AddFirstPlayerMessage(String username, int numPlayers) {
        this.username = username;
        this.numPlayers = numPlayers;
    }

    public String getUsername() {
        return username;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
