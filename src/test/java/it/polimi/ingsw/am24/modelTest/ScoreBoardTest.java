package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.ScoreBoard;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ScoreBoardTest {
    @Test
    public void testGetCoordinates() {
        // Definisci i valori per il test
        Map<Integer, Integer> coordinates = new HashMap<>();
        coordinates.put(1, 10);
        coordinates.put(2, 20);
        coordinates.put(3, 30);

        // Crea una nuova ScoreBoard
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setCoordinates(coordinates);

        // Verifica che il metodo getCoordinates() restituisca gli stessi valori
        assertEquals(coordinates, scoreBoard.getCoordinates());
    }

    @Test
    public void testGetImage() {
        // Definisci il valore per il test
        String image = "scoreboard_image.png";

        // Crea una nuova ScoreBoard
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setImage(image);

        // Verifica che il metodo getImage() restituisca lo stesso valore
        assertEquals(image, scoreBoard.getImage());
    }

}