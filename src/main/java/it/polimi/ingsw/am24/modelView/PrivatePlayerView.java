package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The PrivatePlayerView class represents the view of a player's private information.
 */
public class PrivatePlayerView implements Serializable {
    private final GameCardView secretCard;
    private final ArrayList<GameCardView> playerHand;

    /**
     * Constructs a PrivatePlayerView object with the specified secret card and player hand.
     * @param secret The player's secret card.
     * @param hand The player's hand of cards.
     */
    public PrivatePlayerView(GameCardView secret, ArrayList<GameCardView> hand){
        this.secretCard = secret;
        this.playerHand = hand;
    }

    /**
     * Get the player's secret card.
     * @return The player's secret card.
     */
    public GameCardView getSecretCard() {
        return secretCard;
    }

    /**
     * Get the player's hand of cards.
     * @return The player's hand of cards.
     */
    public ArrayList<GameCardView> getPlayerHand() {
        return playerHand;
    }
}
