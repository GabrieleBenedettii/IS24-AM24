package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;

public class Placement implements Serializable {
    private int x;
    private int y;
    private GameCardView card;
    private boolean front;

    public Placement(int x, int y, GameCardView card, boolean front) {
        this.x = x;
        this.y = y;
        this.card = card;
        this.front = front;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameCardView getCard() {
        return card;
    }

    public boolean getFront() {
        return front;
    }
}
