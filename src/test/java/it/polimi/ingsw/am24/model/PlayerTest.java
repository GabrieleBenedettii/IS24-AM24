package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.GoldCard;
import it.polimi.ingsw.am24.model.card.InitialCard;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player = new Player("Vlahovic");

    @Test
    void gettersAndSetters() {
        assertEquals("Vlahovic",player.getNickname());

        player.setColor(PlayerColor.RED);
        assertEquals(PlayerColor.RED,player.getColor());

        InitialCard init = new InitialCard(1,null,null);
        player.setInitialCard(init);
        assertEquals(init,player.getInitialcard());
    }


    @Test
    void draw() {
        ResourceCard card = new ResourceCard(1,null, Kingdom.FUNGI,0);

        player.draw(card);

        assertTrue(player.getPlayingHand().contains(card));

    }

    @Test
    void setPlayingHand() {
        ResourceCard card1 = new ResourceCard(1,null, Kingdom.FUNGI,0);
        ResourceCard card2= new ResourceCard(1,null, Kingdom.FUNGI,0);
        GoldCard card3 = new GoldCard(1,null,Kingdom.FUNGI,0,false,null,null);

        ArrayList<GameCard> hand = new ArrayList<GameCard>();
        hand.add(card1);
        hand.add(card2);
        hand.add(card3);

        player.setPlayingHand(card1,card2,card3);

        assertTrue(player.getPlayingHand().containsAll(hand));

    }

}