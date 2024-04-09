package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;

public abstract class GameCard {
    private boolean front;
    private final ArrayList<CardCorner> corners;
    private final int imageId;
    private final Kingdom kingdom;

    public GameCard(int imageId, Symbol[] symbols, Kingdom kingdom) {
        this.front = false;
        this.corners = new ArrayList<>();
        for (Symbol s: symbols) {
            this.corners.add(new CardCorner(s,true));
        }
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }
    public Kingdom getKingdom() {
        return kingdom;
    }

    public String printCard() {
        return "";
    }

    public GameCardView getView() {
        return new GameCardView("GameCard", imageId, printCard());
    }

    public String getStringForCard(){
        return Costants.getText(getCornerByIndex(0).getCornerText()) + "*" + Costants.getText(getCornerByIndex(1).getCornerText()) +
                Costants.getText(getCornerByIndex(2).getCornerText()) + "*" + Costants.getText(getCornerByIndex(3).getCornerText());
    }
}

