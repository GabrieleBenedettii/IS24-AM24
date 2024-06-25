package it.polimi.ingsw.am24.modelview;

import it.polimi.ingsw.am24.view.GameStatus;

import java.io.Serializable;

/**
 * The GameView class represents a view of the game, including information about the current player, game ID, player's private view, common board view, and game status.
 */
public class GameView implements Serializable {
    private final String current;
    private final int gameId;
    private final PrivatePlayerView playerView;
    private final PublicBoardView common;
    private final GameStatus gameStatus;

    /**
     * Constructs a GameView object with the specified current player, game ID, player's private view, common board view, and game status.
     * @param current The current player.
     * @param gameId The ID of the game.
     * @param playerView The private view of the player.
     * @param common The common board view.
     * @param gameStatus The status of the game.
     */
    public GameView(String current, int gameId, PrivatePlayerView playerView, PublicBoardView common, GameStatus gameStatus) {
        this.current = current;
        this.gameId = gameId;
        this.playerView = playerView;
        this.common = common;
        this.gameStatus = gameStatus;
    }

    /**
     * Get the current player.
     * @return The current player.
     */
    public String getCurrent() {
        return current;
    }

    /**
     * Get the ID of the game.
     * @return The ID of the game.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Get the private view of the player.
     * @return The private view of the player.
     */
    public PrivatePlayerView getPlayerView() {
        return playerView;
    }

    /**
     * Get the common board view.
     * @return The common board view.
     */
    public PublicBoardView getCommon() {
        return common;
    }

    /**
     * Get the status of the game.
     * @return The status of the game.
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
