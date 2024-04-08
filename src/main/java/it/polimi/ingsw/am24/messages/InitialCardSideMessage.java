package it.polimi.ingsw.am24.messages;

public class InitialCardSideMessage extends Message{
    private String nickname;
    private boolean isFront;

    public InitialCardSideMessage(String nickname, boolean isFront) {
        this.nickname = nickname;
        this.isFront = isFront;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean getFront() {
        return isFront;
    }
}
