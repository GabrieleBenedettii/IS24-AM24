package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PublicBoardView implements Serializable {
    private final CommonBoardView commonBoardView;

    private final ArrayList<String> rotation;
    private final HashMap<String, PublicPlayerView> playerViews;

    public PublicBoardView(CommonBoardView commonBoardView, ArrayList<String> rotation, HashMap<String, PublicPlayerView> playerViews){
        this.commonBoardView = commonBoardView;
        this.rotation = rotation;
        this.playerViews = playerViews;
    }

    public CommonBoardView getCommonBoardView() {
        return commonBoardView;
    }

    public ArrayList<String> getRotation() {
        return rotation;
    }

    public PublicPlayerView getPlayerView(String nickname) {
        return playerViews.get(nickname);
    }
}
