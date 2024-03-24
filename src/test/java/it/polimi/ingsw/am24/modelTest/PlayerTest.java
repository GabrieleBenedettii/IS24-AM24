package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.card.GoldCard;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer", PlayerColor.RED);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("TestPlayer", player.getNickname());
        assertEquals(PlayerColor.RED, player.getColor());
        assertEquals(0, player.getScore());
        assertNotNull(player.getPlayingHand());
        assertNotNull(player.getVisibleSymbols());
        assertNull(player.getInitialcard());
        assertNull(player.getHiddenGoal());
        assertArrayEquals(new GoalCard[2], player.getGoals());
    }

    @Test
    public void testSetPlayingHand() {
        ResourceCard card1 = new ResourceCard("front1", "back1", new Symbol[]{Symbol.ANIMAL}, Kingdom.PLANT, 3);
        ResourceCard card2 = new ResourceCard("front2", "back2", new Symbol[]{Symbol.PLANT}, Kingdom.ANIMAL, 5);
        GoldCard card3 = new GoldCard("front3", "back3", new Symbol[]{Symbol.INSECT}, Kingdom.PLANT, 7, true, Symbol.ANIMAL, new HashMap<>());

        player.setPlayingHand(card1, card2, card3);

        assertEquals(3, player.getPlayingHand().size());
        assertTrue(player.getPlayingHand().contains(card1));
        assertTrue(player.getPlayingHand().contains(card2));
        assertTrue(player.getPlayingHand().contains(card3));
    }
    @Test
    public void testAddPointsAndGetScore() {
        player.addPoints(10);
        assertEquals(10, player.getScore());

        player.addPoints(20);
        assertEquals(30, player.getScore());
    }

    @Test
    public void testAddVisibleSymbols() {
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.PLANT);
        symbols.add(Symbol.PLANT);
        symbols.add(Symbol.ANIMAL);

        player.addVisibleSymbols(symbols);

        assertEquals(2, player.getVisibleSymbols().get(Symbol.PLANT));
        assertEquals(1, player.getVisibleSymbols().get(Symbol.ANIMAL));
    }

    @Test
    public void testRemoveVisibleSymbol() {
        player.addVisibleSymbols(new ArrayList<Symbol>() {{
            add(Symbol.PLANT);
            add(Symbol.PLANT);
            add(Symbol.ANIMAL);
        }});

        player.removeVisibleSymbol(Symbol.PLANT);
        assertEquals(1, player.getVisibleSymbols().get(Symbol.PLANT));

        player.removeVisibleSymbol(Symbol.PLANT);
        assertEquals(0, player.getVisibleSymbols().get(Symbol.PLANT));

        player.removeVisibleSymbol(Symbol.ANIMAL);
        assertEquals(0, player.getVisibleSymbols().get(Symbol.ANIMAL));
    }
}
