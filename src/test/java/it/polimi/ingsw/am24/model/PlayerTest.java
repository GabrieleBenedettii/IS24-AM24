package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.Exceptions.InvalidPositioningException;
import it.polimi.ingsw.am24.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.GoldCard;
import it.polimi.ingsw.am24.model.card.InitialCard;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.model.goal.ObliqueDisposition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    ResourceCard resCard1;
    ResourceCard resCard2;
    GoldCard goldCard1;
    GoldCard goldCard2;
    GoldCard goldCard3;
    InitialCard init;
    GoalCard obj;
    ArrayList<Kingdom> kingdom;

    @BeforeEach
    public void setUp(){
        player = new Player("Vlahovic");

        Symbol[] symbol = new Symbol[4];
        symbol[0] = Symbol.QUILL;
        symbol[1] = null;
        symbol[2] = Symbol.QUILL;
        symbol[3] = Symbol.FUNGI;

        Symbol[] symbol1 = new Symbol[4];
        symbol1[0] = Symbol.FUNGI;
        symbol1[1] = Symbol.FUNGI;
        symbol1[2] = Symbol.FUNGI;
        symbol1[3] = Symbol.FUNGI;

        Symbol[] symbolG = new Symbol[4];
        symbolG[0] = null;
        symbolG[1] = null;
        symbolG[2] = null;
        symbolG[3] = null;

        HashMap<Symbol,Integer> req1 = new HashMap<>();
        req1.put(Symbol.FUNGI, 5);

        ArrayList<Kingdom> kingdom =new ArrayList<>();

        init = new InitialCard(1,symbol,kingdom);
        resCard1 = new ResourceCard(2,symbol1, Kingdom.FUNGI,0);
        resCard2 = new ResourceCard(3,symbol, Kingdom.FUNGI,1);
        goldCard1 = new GoldCard(4,symbolG, Kingdom.FUNGI,5,false,null,req1);
        goldCard2 = new GoldCard(5,symbolG, Kingdom.FUNGI,1,false,Symbol.QUILL,req1);
        goldCard3 = new GoldCard(6,symbolG, Kingdom.FUNGI,2,true,null,req1);
        obj = new ObliqueDisposition(87, 2, Kingdom.FUNGI, 1);

    }
    @Test
    public void Tester() {
        assertEquals("Vlahovic",player.getNickname());

        player.setColor(PlayerColor.RED);
        assertEquals(PlayerColor.RED,player.getColor());

        player.setHiddenGoal(obj);
        assertNotNull(player.getPublicPlayerView());
    }


    @Test
    public void draw() {
        player.draw(resCard1);
        assertTrue(player.getPlayingHand().contains(resCard1));

    }

    @Test
     public void setPlayingHand() {
        ArrayList<GameCard> hand = new ArrayList<>();
        hand.add(resCard1);
        hand.add(resCard2);
        hand.add(goldCard1);

        player.setPlayingHand(resCard1,resCard2,goldCard1);

        assertTrue(player.getPlayingHand().containsAll(hand));

    }

    @Test
    public void hidenGoalTest(){

        player.setHiddenGoal(obj);
        assertEquals(obj, player.getHiddenGoal());
    }

    @Test
    public void playInitialTest(){
        player.setInitialCard(init);
        player.playInitialCard(true);
        assertEquals(2, player.getVisibleSymbols().get(Symbol.QUILL));
        assertEquals(1, player.getVisibleSymbols().get(Symbol.FUNGI));

    }


    @Test
    public void playTest() throws RequirementsNotMetException, InvalidPositioningException {
        player.setInitialCard(init);
        player.playInitialCard(true);
        player.setPlayingHand(resCard1,resCard2,goldCard1);
        assertThrows( InvalidPositioningException.class,() -> player.play(0,true,0,0));
        assertThrows( RequirementsNotMetException.class,() -> player.play(2,true,9,19));
        player.play(0,true,9,19);
        assertEquals(5,player.getVisibleSymbols().get(Symbol.FUNGI));
        player.play(1,true,9,21);
        assertEquals(5, player.getScore());
        player.play(0,true,11,19);
        assertEquals(6, player.getScore());
        assertEquals(2,player.getVisibleSymbols().get(Symbol.QUILL));
        player.setPlayingHand(resCard1,resCard2,goldCard2);
        player.play(2,true,11,21);
        assertEquals(8, player.getScore());
        player.setPlayingHand(resCard1,resCard2,goldCard3);
        player.play(2,true,10,18);
        assertEquals(12,player.getScore());
        assertEquals(4,player.getVisibleSymbols().get(Symbol.FUNGI));
        player.play(0,false,8,22);
        assertEquals(5,player.getVisibleSymbols().get(Symbol.FUNGI));
        player.setPlayingHand(resCard1,resCard2,goldCard3);
        player.play(2,false,7,23);
        assertEquals(12,player.getScore());
        assertEquals(6,player.getVisibleSymbols().get(Symbol.FUNGI));
        player.addPoints(20);
        assertEquals(32,player.getScore());
    }
}