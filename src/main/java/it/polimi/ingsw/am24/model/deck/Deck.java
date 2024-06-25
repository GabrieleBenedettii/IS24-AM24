package it.polimi.ingsw.am24.model.deck;

import it.polimi.ingsw.am24.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.model.card.GameCard;

/**
 * The {@code Deck} interface represents a deck of game cards.
 * It provides methods to load, shuffle, and draw cards from the deck.
 */
public interface Deck {
    /**
     * Loads the cards into the deck.
     * This method is responsible for initializing the deck with a set of cards.
     */
    void loadCards();
    /**
     * Shuffles the deck to randomize the order of the cards.
     * This method ensures that the cards are in a random order before drawing.
     */
    void shuffle();
    /**
     * Draws a card from the deck.
     * This method removes the top card from the deck and returns it.
     *
     * @return the drawn {@code GameCard}
     * @throws EmptyDeckException if the deck is empty and there are no cards to draw
     */
    GameCard drawCard() throws EmptyDeckException;
}
