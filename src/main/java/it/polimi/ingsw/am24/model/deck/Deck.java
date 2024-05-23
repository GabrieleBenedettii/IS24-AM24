package it.polimi.ingsw.am24.model.deck;

import it.polimi.ingsw.am24.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.model.card.GameCard;

/**
 * The {@code Deck} interface defines the structure and behavior of a deck of cards in the game.
 * It includes methods for loading cards, shuffling the deck, and drawing a card.
 */
public interface Deck {

    /**
     * Loads the cards into the deck.
     * This method should initialize and populate the deck with the necessary cards.
     */
    public void loadCards();

    /**
     * Shuffles the cards in the deck to randomize their order.
     * This method ensures that the cards are in a random sequence.
     */
    public void shuffle();

    /**
     * Draws a card from the top of the deck.
     * If the deck is empty, an {@code EmptyDeckException} is thrown.
     *
     * @return The drawn {@code GameCard}.
     * @throws EmptyDeckException if the deck is empty.
     */
    public GameCard drawCard() throws EmptyDeckException;
}
