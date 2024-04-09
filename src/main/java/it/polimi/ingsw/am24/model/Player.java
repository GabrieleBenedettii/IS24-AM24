package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.PlayerView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.am24.costants.Costants.getText;

public class Player {
    private final String nickname;
    private PlayerColor color;
    private int score;
    private ArrayList<PlayableCard> playingHand;
    private InitialCard initialcard;
    private GoalCard hiddenGoal;
    private HashMap<Symbol,Integer> visibleSymbols;
    private GameCard[][] gameboard;

    public Player(String nickname){
        this.nickname = nickname;
        this.playingHand = new ArrayList<>();
        this.visibleSymbols = new HashMap<>();
        for (Symbol s: Symbol.values()) {
            visibleSymbols.put(s,0);
        }
        this.gameboard = new GameCard[21][41];
        this.score = 0;
    }

    public GameCard[][] getGameboard() {
        return gameboard;
    }

    public boolean play(int cardIndex, boolean front, int x, int y) {
        //todo controllo se è possibile giocare la carta alla posizione x,y
        //controllare che ci sia almeno una carta nei 4 angoli e che gli eventuali angoli coperti non siano hidden
        //aggiornare visible symbols (solo se è possibile piazzare la carta)
        playingHand.remove(playingHand.get(cardIndex));
        return true;    //ritornare un valore booleano per comunicare al client se è possibile il piazzamento
    }

    public void playInitialCard(boolean front) {
        gameboard[10][20] = front ? initialcard : initialcard.getBackCard();
    }

    public void draw() {
        //todo bisogna capire quali parametri abbiamo bisogno perché un player può:
        //pescare una carta da uno dei due mazzi
        //pescare una carta specifica delle 4 sul tavolo
    }

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
    public void setColor(PlayerColor color){
        this.color = color;
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
    public ArrayList<PlayableCard> getPlayingHand() {
        return playingHand;
    }
    public InitialCard getInitialcard() {
        return initialcard;
    }
    public void addVisibleSymbols(ArrayList<Symbol> symbols) {
        for (Symbol s: symbols) {
            visibleSymbols.merge(s,1,Integer::sum);
        }
    }
    public HashMap<Symbol,Integer> getVisibleSymbols() {
        return visibleSymbols;
    }
    public void removeVisibleSymbol(Symbol s) {
        if(visibleSymbols.get(s) != null)
            visibleSymbols.merge(s,-1,Integer::sum);
    }
    public PlayerView getPlayerView(){
        String[][] temp = new String[21][41];
        boolean[][] poss = new boolean[21][41];
        for(int i = 0; i < 21; i++){
            for(int j = 0; j < 41; j++){
                if(gameboard[i][j] != null) {
                    temp[i][j] = gameboard[i][j].getStringForCard();
                    //todo poss = true if there's a not hidden corner in at least one card in diagonals
                    //todo or if there's a card in a cell, put the diagonal cell true if there is not a card
                    if((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0) || !gameboard[i][j].printCard().isEmpty()){
                        poss[i][j] = false;
                    }
                    else{
                        poss[i][j] = true;
                    }
                }
                else
                    poss[i][j] = false;
            }
        }
        ArrayList<GameCardView> hand = new ArrayList<>();
        for(PlayableCard c : playingHand){
            hand.add(new GameCardView(c.getType(), c.getImageId(), c.printCard()));
        }
        return new PlayerView(nickname, score, poss, temp, new GameCardView("goal", hiddenGoal.getImageId(), hiddenGoal.printCard()), hand);
    }
}
