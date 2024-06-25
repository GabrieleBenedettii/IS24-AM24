package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.modelview.GameCardView;

public abstract class GoalCard {
    private final int imageId;
    private final int points;

    public GoalCard(int imageId, Integer points) {
        this.imageId = imageId;
        this.points = points;
    }

    public int getImageId() {
        return imageId;
    }
    public Integer getPoints() {
        return points;
    }
    public int calculatePoints(Player p) {
        return 0;
    }

    public String printCard() {
        return "";
    }

    public GameCardView getView() {
        return new GameCardView("Goal Card", imageId, printCard());
    }
}
