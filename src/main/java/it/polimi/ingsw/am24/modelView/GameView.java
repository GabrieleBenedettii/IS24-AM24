package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;

public class GameView implements Serializable {
    private final String current;
    private final PlayerView playerView;
    private final PublicBoardView common;

    public GameView(String current, PlayerView playerView, PublicBoardView common) {
        this.current = current;
        this.playerView = playerView;
        this.common = common;
    }

    public String getCurrent() {
        return current;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public PublicBoardView getCommon() {
        return common;
    }
}
