package it.polimi.ingsw.am24.messages;

import it.polimi.ingsw.am24.modelView.GameView;

public class GameViewMessage extends Message {
    private GameView gameView;

    public GameViewMessage(GameView gameView) {
        this.gameView = gameView;
    }

    public GameView getGameView() {
        return gameView;
    }
}
