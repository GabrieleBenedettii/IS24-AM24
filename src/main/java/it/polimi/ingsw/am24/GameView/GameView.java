package it.polimi.ingsw.am24.GameView;
import it.polimi.ingsw.am24.model.Game;

public class GameView {
    private final PublicBoardView board;
    private final DecksView decks;
    public GameView(Game game, String topGold, String topRes) {
        this.board = new PublicBoardView(game);
        this.decks = new DecksView(topGold, topRes);
    }
}
