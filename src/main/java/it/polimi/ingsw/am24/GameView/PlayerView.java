package it.polimi.ingsw.am24.GameView;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.PlayableCard;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import javafx.util.Pair;

import java.util.ArrayList;

public class PlayerView {
    private final String playerName;
    private final int playerScore;
    private final GameCardView secretCard;
    private final ArrayList<GameCardView> playerHand;
    private final BoardView board;
    public PlayerView(Player player, Pair<Integer, Integer> placements, String[][] board){
        this.board = new BoardView(board, placements);
        this.playerName = player.getNickname();
        this.playerScore = player.getScore();
        this.secretCard = new GameCardView("goal", player.getHiddenGoal().getImageId(), player.getHiddenGoal().printCard());
        this.playerHand = new ArrayList<GameCardView>();
        for(PlayableCard p : player.getPlayingHand()){
            playerHand.add(new GameCardView(p.getType(), p.getImageId(), p.printCard()));
        };
    }
    public String getPlayerName() {
        return playerName;
    }
    public int getPlayerScore() {
        return playerScore;
    }
    public GameCardView getSecretCard() {
        return secretCard;
    }
    public BoardView getBoard() {
        return board;
    }
    public ArrayList<GameCardView> getPlayerHand() {
        return playerHand;
    }
}
