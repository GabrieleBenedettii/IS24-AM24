package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.PlayableCard;

public class Cell {
    private String text;
    private int cardPosition;
    private PlayableCard card;
    private boolean available;

    public Cell() {
        this.text = "";
        this.cardPosition = -1;
        this.available = false;
    }

    public PlayableCard getCard() {
        return card;
    }

    public void setCard(PlayableCard card) {
        this.card = card;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getCardPosition() {
        return cardPosition;
    }
    public void setCardPosition(int cardPosition) {
        this.cardPosition = cardPosition;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

}
