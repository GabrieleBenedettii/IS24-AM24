package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.constant.Constable;

import static org.junit.jupiter.api.Assertions.*;

public class ObliqueDispositionTest {

    ObliqueDisposition topLeft,topRight;
    ResourceCard fungiCard,genericCard;
    ResourceCard[][] boardTL,boardTR;
    @BeforeEach
    public void setup(){
        topLeft = new ObliqueDisposition(1,5, Kingdom.FUNGI,1);
        topRight = new ObliqueDisposition(2,1, Kingdom.FUNGI,-1);

        fungiCard = new ResourceCard(3, new Symbol[]{Symbol.INK, Symbol.ANIMAL},Kingdom.FUNGI,0);
        genericCard = new ResourceCard(5,new Symbol[]{Symbol.INK, Symbol.ANIMAL},null,0);

        boardTL = new ResourceCard[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];
        boardTR = new ResourceCard[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];

        for (int i = 0; i < boardTL.length; i++) {
            for (int j = 0; j < boardTL[i].length; j++) {
                boardTL[i][j] = genericCard;
                boardTR[i][j] = genericCard;
            }
        }

        for (int i = 0; i < boardTL.length; i++) {
            for (int j = 0; j < boardTL[i].length; j++) {
                boardTL[i][j] = genericCard;
                boardTR[i][j] = genericCard;
            }
        }
    }

    @Test
    @DisplayName("Check correct point calculation")
    public void testCalculatePoints1() {

        Player playerTL = new Player("ciao");
        Player playerTR = new Player("ciao1");

        for (int i = 0; i < 3; i++)
            playerTL.getGameBoard()[i][i] = fungiCard;

        for (int i = 0; i < 3; i++)
            playerTR.getGameBoard()[i][2 - i] = fungiCard;

        assertEquals(5,topLeft.calculatePoints(playerTL));
        assertEquals(1,topRight.calculatePoints(playerTR));
    }

    @Test
    @DisplayName("Ensure each card is counted once when calculating points")
    public void testCalculatePoints2() {

        Player playerTL = new Player("ciao");
        Player playerTR = new Player("ciao1");

        for (int i = 0; i < 5; i++)
            playerTL.getGameBoard()[i][i] = fungiCard;

        for (int i = 0; i < 5; i++)
            playerTR.getGameBoard()[i][4 - i] = fungiCard;

        assertEquals(5,topLeft.calculatePoints(playerTL));
        assertEquals(1,topRight.calculatePoints(playerTR));
    }
    @Test
    @DisplayName("Ensure accurate detection of two achieved goals")
    void testCalculatePoints3() {

        Player playerTL = new Player("ciao");
        Player playerTR = new Player("ciao1");

        for (int i = 0; i < 6; i++)
            playerTL.getGameBoard()[i][i] = fungiCard;

        for (int i = 0; i < 6; i++)
            playerTR.getGameBoard()[i][5 - i] = fungiCard;

        assertEquals(10,topLeft.calculatePoints(playerTL));
        assertEquals(2,topRight.calculatePoints(playerTR));
    }
}
