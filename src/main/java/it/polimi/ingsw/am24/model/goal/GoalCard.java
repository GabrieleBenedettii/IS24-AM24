package it.polimi.ingsw.am24.model.goal;

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

    public String printCard() {
        return "";
    }

}
