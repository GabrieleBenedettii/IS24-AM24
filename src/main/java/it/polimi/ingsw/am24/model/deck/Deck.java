package it.polimi.ingsw.am24.model.deck;

import it.polimi.ingsw.am24.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.model.card.GameCard;

public interface Deck {
    public void loadCards();
    public void shuffle();
    public GameCard drawCard() throws EmptyDeckException;
}
