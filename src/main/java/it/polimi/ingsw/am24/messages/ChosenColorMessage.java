package it.polimi.ingsw.am24.messages;

public class ChosenColorMessage extends Message {
    private String nickname;
    private String color;

    public ChosenColorMessage(String username, String color) {
        this.nickname = username;
        this.color = color;
    }

    public String getNickname() {
        return nickname;
    }

    public String getColor() {
        return color;
    }
}
