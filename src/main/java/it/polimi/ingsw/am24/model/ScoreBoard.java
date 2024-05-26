package it.polimi.ingsw.am24.model;

import java.util.Map;

public class ScoreBoard {
    private Map<Integer,Integer> coordinates;
    private String image;
    public Map<Integer, Integer> getCoordinates() {
        return coordinates;
    }
    public String getImage() {
        return image;
    }

    public void setCoordinates(Map<Integer, Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
