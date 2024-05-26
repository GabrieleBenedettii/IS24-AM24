package it.polimi.ingsw.am24.modelView;

import java.io.Serializable;
import java.util.ArrayList;

public class PrivatePlayerView implements Serializable {
    private final GameCardView secretCard;
    private final ArrayList<GameCardView> playerHand;

    public PrivatePlayerView(GameCardView secret, ArrayList<GameCardView> hand){
        this.secretCard = secret;
        this.playerHand = hand;
    }

    public GameCardView getSecretCard() {
        return secretCard;
    }

    public ArrayList<GameCardView> getPlayerHand() {
        return playerHand;
    }
}
