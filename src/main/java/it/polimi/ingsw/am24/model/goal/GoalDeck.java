package it.polimi.ingsw.am24.model.goal;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.HelloApplication;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class GoalDeck {
    private ArrayList<GoalCard> cards;

    public GoalDeck() {
        this.loadCards();
    }

    public void loadCards(){
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/doubleGold.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, DoubleGold[].class)));

        reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/obliqueDisposition.json")));
        cards.addAll(Arrays.asList(gson.fromJson(reader, ObliqueDisposition[].class)));

        reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/tripleGold.json")));
        cards.addAll(Arrays.asList(gson.fromJson(reader, TripleGold[].class)));

        reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/tripleSymbol.json")));
        cards.addAll(Arrays.asList(gson.fromJson(reader, TripleSymbol[].class)));

        reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/verticalDisposition.json")));
        cards.addAll(Arrays.asList(gson.fromJson(reader, VerticalDisposition[].class)));
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    //public void removeCard(GameCard card){}
    //non più utile se la rimozione della carta viene fatta solo con la pescata
    public GoalCard drawCard() {
        GoalCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }
    public ArrayList<GoalCard> getCards(){
        return cards;
    }
}
