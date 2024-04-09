package it.polimi.ingsw.am24.modelView;
import it.polimi.ingsw.am24.model.Game;

import java.io.Serializable;

public class GameView implements Serializable {
    private final PlayerView current;
    private final PublicBoardView common;
    public GameView(PlayerView current, PublicBoardView common) {
        this.current = current;
        this.common = common;
    }

    public PlayerView getCurrent() {
        return current;
    }

    public PublicBoardView getCommon() {
        return common;
    }
}
