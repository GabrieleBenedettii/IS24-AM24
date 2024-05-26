package it.polimi.ingsw.am24.model.deck;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.Exceptions.EmptyDeckException;
import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.ResourceCard;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ResourceDeck implements Deck{
    private ArrayList<ResourceCard> cards;

    public ResourceDeck() {
        this.loadCards();
    }
    public void loadCards() {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/resourceCards.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, ResourceCard[].class)));
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }

    public ResourceCard drawCard() throws EmptyDeckException {
        if(cards.isEmpty()) throw new EmptyDeckException();
        ResourceCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    public Kingdom getFirstCardKingdom(){
        return cards.getFirst().getKingdom();
    }

    public ArrayList<ResourceCard> getCards() {
        return cards;
    }

    public int deckSize() {
        return cards.size();
    }
}
