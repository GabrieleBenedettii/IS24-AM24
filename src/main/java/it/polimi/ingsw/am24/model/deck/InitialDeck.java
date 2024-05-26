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
 * The {@code InitialDeck} class represents a deck of {@link InitialCard} objects in the game.
 * It includes methods for loading cards from a JSON file, shuffling the deck, drawing a card, and other utility functions.
 */
public class InitialDeck implements Deck{
    /**
     * The list of initial cards in the deck.
     */
    private ArrayList<InitialCard> cards;

    /**
     * Initializes an {@code InitialDeck} and loads the cards.
     */
    public InitialDeck() {
        this.loadCards();
    }

    /**
     * Loads the initial cards from a JSON file into the deck.
     */
    public void loadCards(){
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/initialCards.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, InitialCard[].class)));
    }

    /**
     * Shuffles the cards in the deck to randomize their order.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck.
     *
     * @return The drawn {@code InitialCard}.
     */
    public InitialCard drawCard(){
        InitialCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    /**
     * Retrieves the list of initial cards in the deck.
     *
     * @return An {@link ArrayList} of {@link InitialCard} objects.
     */
    public ArrayList<InitialCard> getCards(){
        return cards;
    }
}
