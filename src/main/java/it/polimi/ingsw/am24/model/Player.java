package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.PlayerView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private final String nickname;
    private PlayerColor color;
    private int score;
    private final ArrayList<PlayableCard> playingHand;
    private InitialCard initialcard;
    private GoalCard hiddenGoal;
    private final HashMap<Symbol,Integer> visibleSymbols;

    private final GameCard[][] gameboard;
    private final boolean[][] possiblePlacements;
    private final Pair<Integer, Integer>[] diagonals;

    public Player(String nickname){
        this.nickname = nickname;
        this.playingHand = new ArrayList<>();
        this.visibleSymbols = new HashMap<>();
        for (Symbol s: Symbol.values()) {
            visibleSymbols.put(s,0);
        }
        this.gameboard = new GameCard[21][41];
        this.possiblePlacements = new boolean[21][41];
        this.score = 0;
        diagonals = new Pair[4];
        diagonals[0] = new Pair<>(-1,-1);
        diagonals[1] = new Pair<>(-1,1);
        diagonals[2] = new Pair<>(1,-1);
        diagonals[3] = new Pair<>(1, 1);
    }

    public boolean play(int cardIndex, boolean front, int x, int y) {
        if(!possiblePlacements[x][y]){
            return false;
        }
        if(playingHand.get(cardIndex).getType().equals("gold")){
            boolean placeble = false;
            playingHand.get(cardIndex).checkRequirementsMet(visibleSymbols, placeble);
            if(!placeble){
                return false;
            }
        }
        gameboard[x][y] = playingHand.get(cardIndex).getCardSide(front);
        playingHand.remove(cardIndex);
        //cover all the covered corners and remove covered symbols from visible symbols
        for(int k = 0; k < 4; k++){
            possiblePlacements[x + diagonals[k].getKey()][y + diagonals[k].getValue()] = gameboard[x + diagonals[k].getKey()][y + diagonals[k].getValue()] == null && !gameboard[x][y].getCornerByIndex(k).isHidden();
            if(gameboard[x + diagonals[k].getKey()][y + diagonals[k].getValue()] != null) {
                gameboard[x + diagonals[k].getKey()][y + diagonals[k].getValue()].getCornerByIndex(3 - k).coverCorner();
                if(gameboard[x + diagonals[k].getKey()][y + diagonals[k].getValue()].getCornerByIndex(3 - k).getSymbol() != null)
                    visibleSymbols.merge(gameboard[x + diagonals[k].getKey()][y + diagonals[k].getValue()].getCornerByIndex(3 - k).getSymbol(),-1,Integer::sum);
            }
        }
        //add all the new visible symbols
        if(!front) visibleSymbols.merge(Symbol.valueOf(gameboard[x][y].getKingdom().toString()), 1, Integer::sum);
        else {
            for(int k = 0; k < 4; k++) {
                if(gameboard[x][y].getCornerByIndex(k).getSymbol() != null && !gameboard[x][y].getCornerByIndex(k).isHidden())
                    visibleSymbols.merge(gameboard[x][y].getCornerByIndex(k).getSymbol(),1,Integer::sum);
            }
        }
        possiblePlacements[x][y] = false;
        return true;
    }

    public void playInitialCard(boolean front) {
        gameboard[10][20] = front ? initialcard : initialcard.getBackCard();
        for(int k = 0; k < 4; k++) {
            possiblePlacements[10 + diagonals[k].getKey()][20 + diagonals[k].getValue()] = !gameboard[10][20].getCornerByIndex(k).isHidden();
            if(gameboard[10][20].getCornerByIndex(k).getSymbol() != null && !gameboard[10][20].getCornerByIndex(k).isHidden())
                visibleSymbols.merge(gameboard[10][20].getCornerByIndex(k).getSymbol(), 1, Integer::sum);
        }

        if(!front) {
            for (Kingdom k : initialcard.getBackCard().getKingdoms())
                visibleSymbols.merge(Symbol.valueOf(k.toString()), 1, Integer::sum);
        }
    }

    public boolean draw(PlayableCard card) {
        playingHand.add(card);
        return true;
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
    public HashMap<Symbol,Integer> getVisibleSymbols() {
        return visibleSymbols;
    }

    public PlayerView getPlayerView(){
        String[][] temp = new String[21][41];
        for(int i = 1; i < 20; i++){
            for(int j = 1; j < 40; j++){
                temp[i][j] = gameboard[i][j] != null ? gameboard[i][j].getStringForCard() : null;
            }
        }
        ArrayList<GameCardView> hand = new ArrayList<>();
        for(PlayableCard c : playingHand){
            hand.add(new GameCardView(c.getType(), c.getImageId(), c.printCard()));
        }
        HashMap<String,Integer> vs = new HashMap<>();
        for(Symbol s : visibleSymbols.keySet()){
            vs.put(s.toString(), visibleSymbols.get(s));
        }
        return new PlayerView(nickname, score, possiblePlacements, temp, new GameCardView("goal", hiddenGoal.getImageId(), hiddenGoal.printCard()), hand, vs);
    }
}
