package it.polimi.ingsw.am24.modelview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The PublicBoardView class represents the public view of the game board.
 */
public class PublicBoardView implements Serializable {
    private final CommonBoardView commonBoardView;
    private final ArrayList<String> rotation;
    private final HashMap<String, PublicPlayerView> playerViews;

    /**
     * Constructs a PublicBoardView object with the specified common board view, rotation, and player views.
     * @param commonBoardView The common board view.
     * @param rotation The rotation of players.
     * @param playerViews The views of each player.
     */
    public PublicBoardView(CommonBoardView commonBoardView, ArrayList<String> rotation, HashMap<String, PublicPlayerView> playerViews){
        this.commonBoardView = commonBoardView;
        this.rotation = rotation;
        this.playerViews = playerViews;
    }

    /**
     * Get the common board view.
     * @return The common board view.
     */
    public CommonBoardView getCommonBoardView() {
        return commonBoardView;
    }

    /**
     * Get the rotation of players.
     * @return The rotation of players.
     */
    public ArrayList<String> getRotation() {
        return rotation;
    }

    /**
     * Get the public view of a specific player.
     * @param nickname The nickname of the player.
     * @return The public view of the player.
     */
    public PublicPlayerView getPlayerView(String nickname) {
        return playerViews.get(nickname);
    }
}
