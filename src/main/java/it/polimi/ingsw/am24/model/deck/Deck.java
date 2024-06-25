package it.polimi.ingsw.am24.model.deck;

import it.polimi.ingsw.am24.exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.model.card.GameCard;

public interface Deck {
    void loadCards();
    void shuffle();
    GameCard drawCard() throws EmptyDeckException;
}
