package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObliqueDispositionTest {

    ObliqueDisposition topLeft = new ObliqueDisposition(1,5, Kingdom.FUNGI,-1);
    ObliqueDisposition topRight = new ObliqueDisposition(4,1, Kingdom.FUNGI,1);

    ResourceCard fungiCard = new ResourceCard(1, null,Kingdom.FUNGI,0);
    ResourceCard insectCard = new ResourceCard(2, null,Kingdom.INSECT,0);
    @Test
    void calculatePointsOnce() {
        ResourceCard boardTL[][] = new ResourceCard[5][5];
        ResourceCard boardTR[][] = new ResourceCard[5][5];
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(i==j){
                    boardTL[i][j] = fungiCard;
                    if(i+j!=4)
                        boardTR[i][j] = insectCard;
                }

                if(i+j==4){
                    boardTR[i][j] = fungiCard;
                    if (i!=j)
                        boardTL[i][j] = insectCard;
                }

                else{
                    boardTL[i][j] = insectCard;
                    boardTR[i][j] = insectCard;
                }
            }
        }

        assertEquals(5,topLeft.calculatePoints(boardTL));
        assertEquals(1,topRight.calculatePoints(boardTR));
    }
    @Test
    void calculatePointsTwice() {
        ResourceCard boardTL[][] = new ResourceCard[6][6];
        ResourceCard boardTR[][] = new ResourceCard[6][6];
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                if(i==j){
                    boardTL[i][j] = fungiCard;
                    if(i+j!=5)
                        boardTR[i][j] = insectCard;
                }

                if(i+j==5){
                    boardTR[i][j] = fungiCard;
                    if (i!=j)
                        boardTL[i][j] = insectCard;
                }

                else{
                    boardTL[i][j] = insectCard;
                    boardTR[i][j] = insectCard;
                }
            }
        }

        assertEquals(10,topLeft.calculatePoints(boardTL));
        assertEquals(2,topRight.calculatePoints(boardTR));
    }
}
