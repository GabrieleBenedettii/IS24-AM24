package it.polimi.ingsw.am24.model.goal;

public abstract class GoalCard {
    private String frontImage;
    private String backImage;
    private int points;

    public GoalCard(String frontImage, String backImage, Integer points) {
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.points = points;
    }

    public String getFrontImage() {
        return frontImage;
    }
    public String getBackImage() {
        return backImage;
    }
    public Integer getPoints() {
        return points;
    }

}
