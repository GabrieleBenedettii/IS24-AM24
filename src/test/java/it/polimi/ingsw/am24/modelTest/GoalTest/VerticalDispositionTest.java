package it.polimi.ingsw.am24.modelTest.GoalTest;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import it.polimi.ingsw.am24.model.goal.VerticalDisposition;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class VerticalDispositionTest {

    VerticalDisposition fungi = new VerticalDisposition(1,3, Kingdom.FUNGI, Kingdom.PLANT,3);
    VerticalDisposition plant = new VerticalDisposition(1,3, Kingdom.PLANT, Kingdom.INSECT,2);
    VerticalDisposition animal = new VerticalDisposition(1,3, Kingdom.ANIMAL, Kingdom.FUNGI,1);
    VerticalDisposition insect = new VerticalDisposition(1,3, Kingdom.INSECT, Kingdom.ANIMAL,0);


    ResourceCard fungiCard = new ResourceCard(1, null, Kingdom.FUNGI,0);
    ResourceCard plantCard = new ResourceCard(2, null,Kingdom.PLANT,0);
    ResourceCard animalCard = new ResourceCard(2, null,Kingdom.ANIMAL,0);
    ResourceCard insectCard = new ResourceCard(2, null,Kingdom.INSECT,0);
    @Test
    public void calculatePointsTest() {
        ResourceCard boardf[][] = new ResourceCard[5][5];
        ResourceCard boardp[][] = new ResourceCard[5][5];
        ResourceCard boardi[][] = new ResourceCard[5][5];
        ResourceCard boarda[][] = new ResourceCard[5][5];


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
}
