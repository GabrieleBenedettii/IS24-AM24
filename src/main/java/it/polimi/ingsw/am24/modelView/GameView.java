package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;

public class GameView implements Serializable {
    private final String current;
    private final int gameId;
    private final PlayerView playerView;
    private final PublicBoardView common;

    public GameView(String current, int gameId, PlayerView playerView, PublicBoardView common) {
        this.current = current;
        this.gameId = gameId;
        this.playerView = playerView;
        this.common = common;
    }

    public String getCurrent() {
        return current;
    }

    public int getGameId() {
        return gameId;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public PublicBoardView getCommon() {
        return common;
    }
}
