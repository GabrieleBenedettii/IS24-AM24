package it.polimi.ingsw.am24.model.deck;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.GoldCard;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * The {@code GoldDeck} class represents a deck of {@link GoldCard} objects in the game.
 * It includes methods for loading cards from a JSON file, shuffling the deck, drawing a card, and other utility functions.
 */
public class GoldDeck implements Deck{
    /**
     * The list of gold cards in the deck.
     */
    private ArrayList<GoldCard> cards;

    /**
     * Initializes a {@code GoldDeck} and loads the cards.
     */
    public GoldDeck() {
        this.loadCards();
    }

    /**
     * Loads the gold cards from a JSON file into the deck.
     */
    public void loadCards() {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/goldCards.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, GoldCard[].class)));
    }

    /**
     * Shuffles the cards in the deck to randomize their order.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck.
     * If the deck is empty, an {@code EmptyDeckException} is thrown.
     *
     * @return The drawn {@code GoldCard}.
     * @throws EmptyDeckException if the deck is empty.
     */
    public GoldCard drawCard() throws EmptyDeckException {
        if(cards.isEmpty()) throw new EmptyDeckException();
        GoldCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    /**
     * Retrieves the kingdom of the first card in the deck.
     *
     * @return The {@link Kingdom} of the first card.
     */
    public Kingdom getFirstCardKingdom(){
        return cards.getFirst().getKingdom();
    }

    /**
     * Retrieves the list of gold cards in the deck.
     *
     * @return An {@link ArrayList} of {@link GoldCard} objects.
     */
    public ArrayList<GoldCard> getCards() {
        return cards;
    }

    /**
     * Retrieves the size of the deck.
     *
     * @return The number of cards in the deck.
     */
    public int deckSize() {
        return cards.size();
    }
}
