package it.polimi.ingsw.am24.messages;

public class SecretObjectiveChosenMessage extends Message{
    private final String nickname;
    private final int cardId;

    public SecretObjectiveChosenMessage(String nickname, int cardId) {
        this.nickname = nickname;
        this.cardId = cardId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCardId() {
        return cardId;
    }
}
