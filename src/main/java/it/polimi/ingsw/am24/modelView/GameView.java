package it.polimi.ingsw.am24.modelView;

import it.polimi.ingsw.am24.view.flow.utility.GameStatus;

import java.io.Serializable;

public class GameView implements Serializable {
    private final String current;
    private final int gameId;
    private final PrivatePlayerView playerView;
    private final PublicBoardView common;
    private final GameStatus gameStatus;

    public GameView(String current, int gameId, PrivatePlayerView playerView, PublicBoardView common, GameStatus gameStatus) {
        this.current = current;
        this.gameId = gameId;
        this.playerView = playerView;
        this.common = common;
        this.gameStatus = gameStatus;
    }

    public String getCurrent() {
        return current;
    }

    public int getGameId() {
        return gameId;
    }

    public PrivatePlayerView getPlayerView() {
        return playerView;
    }

    public PublicBoardView getCommon() {
        return common;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
