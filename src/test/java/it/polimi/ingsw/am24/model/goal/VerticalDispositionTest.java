package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
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
        boardf[0][2]=fungiCard;
        boardf[2][2]=fungiCard;
        boardf[3][3]=plantCard;

        boardp[0][2]=plantCard;
        boardp[2][2]=plantCard;
        boardp[3][1]=insectCard;

        boarda[1][2]=animalCard;
        boarda[3][2]=animalCard;
        boarda[0][3]=fungiCard;

        boardi[1][2]=insectCard;
        boardi[3][2]=insectCard;
        boardi[0][1]=animalCard;

        assertEquals(3,fungi.calculatePoints(boardf));
        assertEquals(3,plant.calculatePoints(boardp));
        assertEquals(3,animal.calculatePoints(boarda));
        assertEquals(3,insect.calculatePoints(boardi));
    }

    @Test
    @DisplayName("Ensure each card is counted once when calculating points")
    public void testCalculatePoints2() {

        boardf[0][2]=fungiCard;
        boardf[2][2]=fungiCard;
        boardf[3][3]=plantCard;
        boardf[4][2]=fungiCard;
        boardf[5][3]=plantCard;

        boardp[0][2]=plantCard;
        boardp[2][2]=plantCard;
        boardp[3][1]=insectCard;
        boardp[4][2]=plantCard;
        boardp[5][1]=insectCard;

        boarda[1][2]=animalCard;
        boarda[3][2]=animalCard;
        boarda[0][3]=fungiCard;
        boarda[5][2]=animalCard;
        boarda[2][3]=fungiCard;

        boardi[1][2]=insectCard;
        boardi[3][2]=insectCard;
        boardi[0][1]=animalCard;
        boardi[5][2]=insectCard;
        boardi[2][1]=animalCard;


        assertEquals(3,fungi.calculatePoints(boardf));
        assertEquals(3,plant.calculatePoints(boardp));
        assertEquals(3,animal.calculatePoints(boarda));
        assertEquals(3,insect.calculatePoints(boardi));
    }

    @Test
    @DisplayName("Ensure accurate detection of two achieved goals")
    public void testCalculatePoints3() {

        boardf[0][2]=fungiCard;
        boardf[2][2]=fungiCard;
        boardf[3][3]=plantCard;
        boardf[0][0]=fungiCard;
        boardf[2][0]=fungiCard;
        boardf[3][1]=plantCard;

        boardp[0][2]=plantCard;
        boardp[2][2]=plantCard;
        boardp[3][1]=insectCard;
        boardp[0][4]=plantCard;
        boardp[2][4]=plantCard;
        boardp[3][3]=insectCard;

        boarda[1][2]=animalCard;
        boarda[3][2]=animalCard;
        boarda[0][3]=fungiCard;
        boarda[1][0]=animalCard;
        boarda[3][0]=animalCard;
        boarda[0][1]=fungiCard;

        boardi[1][2]=insectCard;
        boardi[3][2]=insectCard;
        boardi[0][1]=animalCard;
        boardi[1][4]=insectCard;
        boardi[3][4]=insectCard;
        boardi[0][3]=animalCard;

        assertEquals(6,fungi.calculatePoints(boardf));
        assertEquals(6,plant.calculatePoints(boardp));
        assertEquals(6,animal.calculatePoints(boarda));
        assertEquals(6,insect.calculatePoints(boardi));
    }
}
