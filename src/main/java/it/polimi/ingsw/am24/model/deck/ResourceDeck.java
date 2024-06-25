package it.polimi.ingsw.am24.model.deck;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.ResourceCard;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * The {@code ResourceDeck} class represents a deck of resource cards.
 * It implements the {@code Deck} interface and provides methods to load, shuffle, and draw cards from the deck.
 */
public class ResourceDeck implements Deck{
    private ArrayList<ResourceCard> cards;

    /**
     * Constructs a new {@code ResourceDeck} and loads the cards into the deck.
     */
    public ResourceDeck() {
        this.loadCards();
    }

    /**
     * Loads the resource cards into the deck from a JSON file.
     * This method initializes the deck with resource cards by reading from the specified JSON file.
     */
    public void loadCards() {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(Root.class.getResourceAsStream("cards/resourceCards.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, ResourceCard[].class)));
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
     * @return the drawn {@code ResourceCard}
     * @throws EmptyDeckException if the deck is empty and there are no cards to draw
     */
    public ResourceCard drawCard() throws EmptyDeckException {
        if(cards.isEmpty()) throw new EmptyDeckException();
        ResourceCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    /**
     * Gets the kingdom of the first card in the deck.
     * This method returns the kingdom of the top card in the deck without removing it.
     *
     * @return the {@code Kingdom} of the first card in the deck
     */
    public Kingdom getFirstCardKingdom(){
        return cards.getFirst().getKingdom();
    }

    /**
     * Gets the list of resource cards in the deck.
     *
     * @return an {@code ArrayList} of {@code ResourceCard} objects
     */
    public ArrayList<ResourceCard> getCards() {
        return cards;
    }

    /**
     * Gets the number of cards remaining in the deck.
     *
     * @return the size of the deck
     */
    public int deckSize() {
        return cards.size();
    }
}
