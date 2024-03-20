package it.polimi.ingsw.am24.modelTest;

import it.polimi.ingsw.am24.model.card.InitialCard;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class InitialCardTest {
    @Test
    public void testGetters() {
        // Definisci i valori per il test
        String frontImage = "front.png";
        String backImage = "back.png";
        Symbol[] symbols = {Symbol.ANIMAL, Symbol.PLANT};
        ArrayList<Kingdom> kingdoms = new ArrayList<>();
        kingdoms.add(Kingdom.PLANT);
        kingdoms.add(Kingdom.ANIMAL);

        // Crea una nuova InitialCard
        InitialCard initialCard = new InitialCard(frontImage, backImage, symbols, kingdoms);

        // Verifica che i valori restituiti dai metodi getter corrispondano ai valori impostati nel costruttore
        assertEquals(frontImage, initialCard.getFrontImage());
        assertEquals(backImage, initialCard.getBackImage());
        //assertArrayEquals(symbols, initialCard.getCorners().toArray());
        assertEquals(kingdoms, initialCard.getKingdoms());
    }

    @Test
    public void testSetBackCardAndGetBackCard() {
        // Definisci una carta posteriore
        InitialCard backCard = new InitialCard("back_front.png", "back_back.png", new Symbol[]{Symbol.ANIMAL, Symbol.INSECT}, new ArrayList<>());

        // Crea una nuova InitialCard
        InitialCard initialCard = new InitialCard("front.png", "back.png", new Symbol[]{Symbol.ANIMAL, Symbol.PLANT}, new ArrayList<>());

        // Imposta la carta posteriore
        initialCard.setBackCard(backCard);

        // Verifica che il metodo getBackCard() restituisca la stessa carta impostata
        assertEquals(backCard, initialCard.getBackCard());
    }
}
