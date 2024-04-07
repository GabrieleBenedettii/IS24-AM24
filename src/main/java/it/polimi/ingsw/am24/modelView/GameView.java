package it.polimi.ingsw.am24.modelView;
import it.polimi.ingsw.am24.model.Game;

import java.io.Serializable;

public class GameView implements Serializable {
    private final PublicBoardView board;
    private final DecksView decks;
    public GameView(Game game, String topGold, String topRes) {
        this.board = new PublicBoardView(game);
        this.decks = new DecksView(topGold, topRes);
    }
}
