package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Symbol;

import java.util.ArrayList;

public abstract class GameCard {
    private boolean front;
    private final ArrayList<CardCorner> corners;
    private final int imageId;

    public GameCard(int imageId, Symbol[] symbols) {
        this.front = false;
        this.corners = new ArrayList<>();
        for (Symbol s: symbols) {
            this.corners.add(new CardCorner(s,true));
        }
        this.imageId = imageId;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public ArrayList<CardCorner> getCorners() {
        return corners;
    }

    public CardCorner getCornerByIndex(int index) {
        return corners.get(index);
    }

    public int getImageId() {
        return imageId;
    }

    public String printCard() {
        return "";
    }
}

