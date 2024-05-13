package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        boardTL = new ResourceCard[21][41];
        boardTR = new ResourceCard[21][41];

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
        for (int i = 0; i < 3; i++)
            boardTL[i][i] = fungiCard;

        for (int i = 0; i < 3; i++)
            boardTR[i][2 - i] = fungiCard;

        assertEquals(5,topLeft.calculatePoints(boardTL));
        assertEquals(1,topRight.calculatePoints(boardTR));
    }

    @Test
    @DisplayName("Ensure each card is counted once when calculating points")
    public void testCalculatePoints2() {
        for (int i = 0; i < 5; i++)
            boardTL[i][i] = fungiCard;

        for (int i = 0; i < 5; i++)
            boardTR[i][4 - i] = fungiCard;

        assertEquals(5,topLeft.calculatePoints(boardTL));
        assertEquals(1,topRight.calculatePoints(boardTR));
    }
    @Test
    @DisplayName("Ensure accurate detection of two achieved goals")
    void testCalculatePoints3() {
        for (int i = 0; i < 6; i++)
            boardTL[i][i] = fungiCard;

        for (int i = 0; i < 6; i++)
            boardTR[i][5 - i] = fungiCard;

        assertEquals(10,topLeft.calculatePoints(boardTL));
        assertEquals(2,topRight.calculatePoints(boardTR));
    }
}
