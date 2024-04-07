package it.polimi.ingsw.am24.messages;

public class ReconnectMessage extends Message{
    private final String nickname;

    public ReconnectMessage(String username) {
        this.nickname = username;
    }

    public String getNickname() {
        return nickname;
    }
}
