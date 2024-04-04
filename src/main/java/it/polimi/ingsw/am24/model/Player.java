package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalCard;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private final String nickname;
    private final PlayerColor color;
    private int score;
    private ArrayList<GameCard> playingHand;
    private InitialCard initialcard;
    private GoalCard hiddenGoal;
    private HashMap<Symbol,Integer> visibleSymbols;
    private Cell[][] gameboard;
    public Player(String nickname, PlayerColor color){
        this.nickname = nickname;
        this.color = color;
        this.playingHand = new ArrayList<>();
        this.visibleSymbols = new HashMap<>();
        for (Symbol s: Symbol.values()) {
            visibleSymbols.put(s,0);
        }
        gameboard = new Cell[81][81];
        for(int i = 0; i < 81; i++) {
            for(int j = 0; j < 81; j++) {
                gameboard[i][j] = new Cell();
            }
        }
    }
    public Cell[][] getGameboard() {
        return gameboard;
    }

//    public void play(GameCard gameCard) {
//        playingHand.remove(gameCard);
//    }
//    public void draw(GameCard gameCard) {
//        playingHand.add(gameCard);
//    }

    public void setPlayingHand(ResourceCard card1, ResourceCard card2, GoldCard card3) {
        playingHand.add(card1);
        playingHand.add(card2);
        playingHand.add(card3);
    }
    public GoalCard getHiddenGoal() {
        return hiddenGoal;
    }
    public void setHiddenGoal(GoalCard obj){
        this.hiddenGoal = obj;
    }
    public void setInitialCard(InitialCard card) {
        this.initialcard = card;
    }
    public String getNickname() {
        return nickname;
    }
    public PlayerColor getColor() {
        return color;
    }
    public int getScore() {
        return score;
    }
    public void addPoints(int points) {
        this.score += points;
    }
    public ArrayList<GameCard> getPlayingHand() {
        return playingHand;
    }
    public InitialCard getInitialcard() {
        return initialcard;
    }
    public void addVisibleSymbols(ArrayList<Symbol> symbols) {
        for (Symbol s: symbols) {
            if(visibleSymbols.get(s) != null)
                visibleSymbols.merge(s,1,Integer::sum);
            else
                visibleSymbols.put(s,1);
        }
    }
    public HashMap<Symbol,Integer> getVisibleSymbols() {
        return visibleSymbols;
    }
    //todo rimettere al plurale
    public void removeVisibleSymbol(Symbol s) {
        if(visibleSymbols.get(s) != null)
            visibleSymbols.merge(s,-1,Integer::sum);
    }
}
