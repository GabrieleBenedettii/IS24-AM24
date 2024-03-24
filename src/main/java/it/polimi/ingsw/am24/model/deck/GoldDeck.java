package it.polimi.ingsw.am24.model.deck;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.model.card.GoldCard;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class GoldDeck implements Deck{
    private ArrayList<GoldCard> cards;

    public GoldDeck() {
        this.loadCards();
    }
    public void loadCards() {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/goldCards.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, GoldCard[].class)));
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public GoldCard drawCard(){
        GoldCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }
    public ArrayList<GoldCard> getCards(){
        return cards;
    }
}
