package it.polimi.ingsw.am24.messages;

public class PlayCardMessage extends Message{
    private String nickname;
    private boolean isFront;
    private int cardIndex;
    private int x;
    private int y;

    public PlayCardMessage(String nickname, int cardIndex, boolean isFront, int x, int y) {
        this.nickname = nickname;
        this.isFront = isFront;
        this.cardIndex = cardIndex;
        this.x = x;
        this.y = y;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean getFront() {
        return isFront;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
