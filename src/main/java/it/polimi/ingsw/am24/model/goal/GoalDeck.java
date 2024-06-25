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
 * It manages loading, shuffling, and drawing goal cards.
 */
public class GoalDeck {
    /** The list of goal cards in the deck. */
    private ArrayList<GoalCard> cards;

    /**
     * Constructs a {@code GoalDeck} and loads goal cards from JSON files.
     */
    public GoalDeck() {
        this.loadCards();
    }

    /**
     * Loads goal cards from JSON files and initializes the deck.
     * Three types of goal cards are loaded: SymbolGoal, ObliqueDisposition, and VerticalDisposition.
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
     * Shuffles the goal cards in the deck.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Draws a goal card from the deck.
     *
     * @return the drawn {@code GoalCard}
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
     * @return an {@code ArrayList} of {@code GoalCard} objects
     */
    public ArrayList<GoalCard> getCards(){
        return cards;
    }
}
