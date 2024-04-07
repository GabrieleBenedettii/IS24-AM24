package it.polimi.ingsw.am24.messages;

public class AddPlayerMessage extends Message {
    private final String nickname;
    private final int numPlayers;  //num = [2;4] => first player, num = 1 => other

    public AddPlayerMessage(String username, int numPlayers) {
        this.nickname = username;
        this.numPlayers = numPlayers;
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
