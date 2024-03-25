package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.ArrayList;

public abstract class GameCard {
    private boolean front;
    private final ArrayList<CardCorner> corners;
    private final String frontImage;
    private final String backImage;
    private final Kingdom kingdom;

    public GameCard(String frontImage, String backImage, Symbol[] symbols, Kingdom kingdom) {
        this.front = false;
        this.corners = new ArrayList<>();
        for (Symbol s: symbols) {
            this.corners.add(new CardCorner(s,true));
        }
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.kingdom = kingdom;
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

    public String getFrontImage() {
        return frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public String printCard() {
        return "";
    }
}

