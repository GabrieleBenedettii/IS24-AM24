package it.polimi.ingsw.am24.model.deck;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.model.card.InitialCard;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class InitialDeck implements Deck{
    private ArrayList<InitialCard> cards;

    public InitialDeck() {
        this.loadCards();
    }
    public void loadCards(){
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(Root.class.getResourceAsStream("cards/initialCards.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, InitialCard[].class)));
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public InitialCard drawCard(){
        InitialCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }
    public ArrayList<InitialCard> getCards(){
        return cards;
    }
}
