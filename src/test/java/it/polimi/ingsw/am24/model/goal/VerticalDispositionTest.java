package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerticalDispositionTest {
    VerticalDisposition fungi, plant,animal, insect;
    ResourceCard fungiCard, plantCard,animalCard, insectCard;
    ResourceCard[][] boardf, boardp,boardi,boarda;

    @BeforeEach
    public void setUp(){
        fungi = new VerticalDisposition(1,3, Kingdom.FUNGI, Kingdom.PLANT,3);
        plant = new VerticalDisposition(2,3, Kingdom.PLANT, Kingdom.INSECT,2);
        animal = new VerticalDisposition(3,3, Kingdom.ANIMAL, Kingdom.FUNGI,1);
        insect = new VerticalDisposition(4,3, Kingdom.INSECT, Kingdom.ANIMAL,0);

        fungiCard = new ResourceCard(5, new Symbol[]{Symbol.INK, Symbol.ANIMAL}, Kingdom.FUNGI,0);
        plantCard = new ResourceCard(6, new Symbol[]{Symbol.INK, Symbol.ANIMAL},Kingdom.PLANT,0);
        animalCard = new ResourceCard(7, new Symbol[]{Symbol.INK, Symbol.ANIMAL},Kingdom.ANIMAL,0);
        insectCard = new ResourceCard(8, new Symbol[]{Symbol.INK, Symbol.ANIMAL},Kingdom.INSECT,0);

        boardf = new ResourceCard[21][41];
        boardp = new ResourceCard[21][41];
        boardi = new ResourceCard[21][41];
        boarda = new ResourceCard[21][41];

        for (int i = 0; i < boardf.length; i++) {
            for (int j = 0; j < boardf[i].length; j++) {
                boardf[i][j] = animalCard;
                boardf[i][j] = animalCard;
            }
        }

        for (int i = 0; i < boardp.length; i++) {
            for (int j = 0; j < boardp[i].length; j++) {
                boardp[i][j] = fungiCard;
                boardp[i][j] = fungiCard;
            }
        }

        for (int i = 0; i < boardi.length; i++) {
            for (int j = 0; j < boardi[i].length; j++) {
                boardi[i][j] = fungiCard;
                boardi[i][j] = fungiCard;
            }
        }

        for (int i = 0; i < boarda.length; i++) {
            for (int j = 0; j < boarda[i].length; j++) {
                boarda[i][j] = plantCard;
                boarda[i][j] = plantCard;
            }
        }
    }
    @Test
    @DisplayName("Check correct point calculation")
    public void testCalculatePoints1() {
        Player playerf = new Player("ciao");
        Player playerp = new Player("ciao1");
        Player playera = new Player("ciao2");
        Player playeri = new Player("ciao3");

        playerf.getGameBoard()[0][2]=fungiCard;
        playerf.getGameBoard()[2][2]=fungiCard;
        playerf.getGameBoard()[3][3]=plantCard;

        playerp.getGameBoard()[0][2]=plantCard;
        playerp.getGameBoard()[2][2]=plantCard;
        playerp.getGameBoard()[3][1]=insectCard;

        playera.getGameBoard()[1][2]=animalCard;
        playera.getGameBoard()[3][2]=animalCard;
        playera.getGameBoard()[0][3]=fungiCard;

        playeri.getGameBoard()[1][2]=insectCard;
        playeri.getGameBoard()[3][2]=insectCard;
        playeri.getGameBoard()[0][1]=animalCard;

        assertEquals(3,fungi.calculatePoints(playerf));
        assertEquals(3,plant.calculatePoints(playerp));
        assertEquals(3,animal.calculatePoints(playera));
        assertEquals(3,insect.calculatePoints(playeri));
    }

    @Test
    @DisplayName("Ensure each card is counted once when calculating points")
    public void testCalculatePoints2() {

        Player playerf = new Player("ciao");
        Player playerp = new Player("ciao1");
        Player playera = new Player("ciao2");
        Player playeri = new Player("ciao3");

        playerf.getGameBoard()[0][2]=fungiCard;
        playerf.getGameBoard()[2][2]=fungiCard;
        playerf.getGameBoard()[3][3]=plantCard;
        playerf.getGameBoard()[4][2]=fungiCard;
        playerf.getGameBoard()[5][3]=plantCard;

        playerp.getGameBoard()[0][2]=plantCard;
        playerp.getGameBoard()[2][2]=plantCard;
        playerp.getGameBoard()[3][1]=insectCard;
        playerp.getGameBoard()[4][2]=plantCard;
        playerp.getGameBoard()[5][1]=insectCard;

        playera.getGameBoard()[1][2]=animalCard;
        playera.getGameBoard()[3][2]=animalCard;
        playera.getGameBoard()[0][3]=fungiCard;
        playera.getGameBoard()[5][2]=animalCard;
        playera.getGameBoard()[2][3]=fungiCard;

        playeri.getGameBoard()[1][2]=insectCard;
        playeri.getGameBoard()[3][2]=insectCard;
        playeri.getGameBoard()[0][1]=animalCard;
        playeri.getGameBoard()[5][2]=insectCard;
        playeri.getGameBoard()[2][1]=animalCard;


        assertEquals(3,fungi.calculatePoints(playerf));
        assertEquals(3,plant.calculatePoints(playerp));
        assertEquals(3,animal.calculatePoints(playera));
        assertEquals(3,insect.calculatePoints(playeri));
    }

    @Test
    @DisplayName("Ensure accurate detection of two achieved goals")
    public void testCalculatePoints3() {
        Player playerf = new Player("ciao");
        Player playerp = new Player("ciao1");
        Player playera = new Player("ciao2");
        Player playeri = new Player("ciao3");

        playerf.getGameBoard()[0][2]=fungiCard;
        playerf.getGameBoard()[2][2]=fungiCard;
        playerf.getGameBoard()[3][3]=plantCard;
        playerf.getGameBoard()[0][0]=fungiCard;
        playerf.getGameBoard()[2][0]=fungiCard;
        playerf.getGameBoard()[3][1]=plantCard;

        playerp.getGameBoard()[0][2]=plantCard;
        playerp.getGameBoard()[2][2]=plantCard;
        playerp.getGameBoard()[3][1]=insectCard;
        playerp.getGameBoard()[0][4]=plantCard;
        playerp.getGameBoard()[2][4]=plantCard;
        playerp.getGameBoard()[3][3]=insectCard;

        playera.getGameBoard()[1][2]=animalCard;
        playera.getGameBoard()[3][2]=animalCard;
        playera.getGameBoard()[0][3]=fungiCard;
        playera.getGameBoard()[1][0]=animalCard;
        playera.getGameBoard()[3][0]=animalCard;
        playera.getGameBoard()[0][1]=fungiCard;

        playeri.getGameBoard()[1][2]=insectCard;
        playeri.getGameBoard()[3][2]=insectCard;
        playeri.getGameBoard()[0][1]=animalCard;
        playeri.getGameBoard()[1][4]=insectCard;
        playeri.getGameBoard()[3][4]=insectCard;
        playeri.getGameBoard()[0][3]=animalCard;

        assertEquals(6,fungi.calculatePoints(playerf));
        assertEquals(6,plant.calculatePoints(playerp));
        assertEquals(6,animal.calculatePoints(playera));
        assertEquals(6,insect.calculatePoints(playeri));
    }
}
