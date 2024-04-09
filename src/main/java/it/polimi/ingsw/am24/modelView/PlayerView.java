package it.polimi.ingsw.am24.modelView;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.card.PlayableCard;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerView implements Serializable {
    private final String playerName;
    private final int playerScore;
    private final GameCardView secretCard;
    private final ArrayList<GameCardView> playerHand;
    private final String[][] board;
    private final boolean[][] possiblePlacements;
    public PlayerView(String player, int score, boolean[][] placements, String[][] board, GameCardView secret, ArrayList<GameCardView> hand){
        this.board = board;
        this.possiblePlacements = placements;
        this.playerName = player;
        this.playerScore = score;
        this.secretCard = secret;
        this.playerHand = hand;
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
    public ArrayList<GameCardView> getPlayerHand() {
        return playerHand;
    }

    public String[][] getBoard() {
        return board;
    }

    public boolean[][] getPossiblePlacements() {
        return possiblePlacements;
    }
}
