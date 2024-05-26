package it.polimi.ingsw.am24.model.goal;

import com.google.gson.Gson;
import it.polimi.ingsw.am24.HelloApplication;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * The {@code GoalDeck} class represents a deck of goal cards in the game.
 * It includes methods for loading cards from JSON files, shuffling the deck, drawing a card, and other utility functions.
 */
public class GoalDeck {
    /**
     * The list of goal cards in the deck.
     */
    private ArrayList<GoalCard> cards;

    /**
     * Initializes a {@code GoalDeck} and loads the cards.
     */
    public GoalDeck() {
        this.loadCards();
    }

    /**
     * Loads the goal cards from JSON files into the deck.
     */
    public void loadCards(){
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/SymbolGoal.json")));
        cards = new ArrayList<>(Arrays.asList(gson.fromJson(reader, SymbolGoal[].class)));

        reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/obliqueDisposition.json")));
        cards.addAll(Arrays.asList(gson.fromJson(reader, ObliqueDisposition[].class)));

        reader = new InputStreamReader(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("cards/verticalDisposition.json")));
        cards.addAll(Arrays.asList(gson.fromJson(reader, VerticalDisposition[].class)));
    }

    /**
     * Shuffles the cards in the deck to randomize their order.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck.
     *
     * @return The drawn {@code GoalCard}.
     */
    public GoalCard drawCard() {
        GoalCard card;
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    /**
     * Retrieves the list of goal cards in the deck.
     *
     * @return An {@link ArrayList} of {@link GoalCard} objects.
     */
    public ArrayList<GoalCard> getCards(){
        return cards;
    }
}
