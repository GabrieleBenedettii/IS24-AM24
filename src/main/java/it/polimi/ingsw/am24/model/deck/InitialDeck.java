package it.polimi.ingsw.am24.model.deck;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.model.card.InitialCard;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * The {@code InitialDeck} class represents a deck of initial cards.
 * It implements the {@code Deck} interface and provides methods to load, shuffle, and draw cards from the deck.
 */
public class InitialDeck implements Deck{
    private ArrayList<InitialCard> cards;

    /**
     * Constructs a new {@code InitialDeck} and loads the cards into the deck.
     */
    public InitialDeck() {
        this.loadCards();
    }

    /**
     * Loads the initial cards into the deck from a JSON file.
     * This method initializes the deck with initial cards by reading from the specified JSON file.
     */
    public void loadCards(){
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/initialCards.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, InitialCard[].class)));
    }

    /**
     * Shuffles the deck to randomize the order of the cards.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the deck.
     * This method removes the top card from the deck and returns it.
     *
     * @return the drawn {@code InitialCard}
     */
    public InitialCard drawCard(){
        InitialCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    /**
     * Gets the list of initial cards in the deck.
     *
     * @return an {@code ArrayList} of {@code InitialCard} objects
     */
    public ArrayList<InitialCard> getCards(){
        return cards;
    }
}
