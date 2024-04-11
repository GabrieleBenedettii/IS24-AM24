package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.model.Player;

public class DrawCardMessage extends Message{
    private String nickname;
    private int card;

    public DrawCardMessage(String nickname, int card) {
        this.nickname = nickname;
        this.card = card;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCard() {
        return card;
    }
}
