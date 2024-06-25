package it.polimi.ingsw.am24.modelview;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The PrivatePlayerView class represents the view of a player's private information.
 */
public class PrivatePlayerView implements Serializable {
    private final GameCardView hiddenGoal;
    private final ArrayList<GameCardView> playerHand;

    /**
     * Constructs a PrivatePlayerView object with the specified secret card and player hand.
     * @param hiddenGoal The player's secret card.
     * @param hand The player's hand of cards.
     */
    public PrivatePlayerView(GameCardView hiddenGoal, ArrayList<GameCardView> hand){
        this.hiddenGoal = hiddenGoal;
        this.playerHand = hand;
    }

    /**
     * Get the player's hidden goal.
     * @return The player's hidden goal.
     */
    public GameCardView getHiddenGoal() {
        return hiddenGoal;
    }

    /**
     * Get the player's hand of cards.
     * @return The player's hand of cards.
     */
    public ArrayList<GameCardView> getPlayerHand() {
        return playerHand;
    }
}
