package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.Exceptions.InvalidPositioningException;
import it.polimi.ingsw.am24.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.am24.model.card.*;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.Placement;
import it.polimi.ingsw.am24.modelView.PrivatePlayerView;
import it.polimi.ingsw.am24.modelView.PublicPlayerView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Player {
    private final String nickname;
    private PlayerColor color;
    private int score;
    private final ArrayList<PlayableCard> playingHand;
    private InitialCard initialcard;
    private GoalCard hiddenGoal;
    private final HashMap<Symbol,Integer> visibleSymbols;

    private final GameCard[][] gameBoard;
    private final ArrayList<Placement> placeOrder;
    private final int[][] possiblePlacements;
    private final Pair<Integer, Integer>[] diagonals;

    public Player(String nickname){
        this.nickname = nickname;
        this.playingHand = new ArrayList<>();
        this.visibleSymbols = new HashMap<>();
        for (Symbol s: Symbol.values()) {
            visibleSymbols.put(s,0);
        }
        this.gameBoard = new GameCard[21][41];
        this.placeOrder = new ArrayList<>();
        this.possiblePlacements = new int[21][41];
        Arrays.stream(possiblePlacements).forEach(row->Arrays.fill(row,-1));
        this.score = 0;
        diagonals = new Pair[4];
        diagonals[0] = new Pair<>(-1,-1);
        diagonals[1] = new Pair<>(-1,1);
        diagonals[2] = new Pair<>(1,-1);
        diagonals[3] = new Pair<>(1, 1);
    }

    public void play(int cardIndex, boolean front, int x, int y) throws InvalidPositioningException, RequirementsNotMetException {
        if(possiblePlacements[x][y] != 1){
            throw new InvalidPositioningException();
        }
        if(playingHand.get(cardIndex).getType().equals("gold") && front){
            boolean placeable;
            placeable = playingHand.get(cardIndex).checkRequirementsMet(visibleSymbols);
            if(!placeable){
                throw new RequirementsNotMetException();
            }
        }

        gameBoard[x][y] = playingHand.get(cardIndex).getCardSide(front);
        placeOrder.add(new Placement(x,y,playingHand.get(cardIndex).getCardSide(front).getViewForMatrix(),front));

        //cover all the covered corners and remove covered symbols from visible symbols
        int coveredCorners = 0;
        for(int k = 0; k < 4; k++){
            possiblePlacements[x + diagonals[k].getKey()][y + diagonals[k].getValue()] = (gameBoard[x + diagonals[k].getKey()][y + diagonals[k].getValue()] == null && !gameBoard[x][y].getCornerByIndex(k).isHidden() && possiblePlacements[x + diagonals[k].getKey()][y + diagonals[k].getValue()] != 0) ? 1 : 0;
            if(gameBoard[x + diagonals[k].getKey()][y + diagonals[k].getValue()] != null) {
                gameBoard[x + diagonals[k].getKey()][y + diagonals[k].getValue()].getCornerByIndex(3 - k).coverCorner();
                coveredCorners++;
                if(gameBoard[x + diagonals[k].getKey()][y + diagonals[k].getValue()].getCornerByIndex(3 - k).getSymbol() != null)
                    visibleSymbols.merge(gameBoard[x + diagonals[k].getKey()][y + diagonals[k].getValue()].getCornerByIndex(3 - k).getSymbol(),-1,Integer::sum);
            }
        }
        //add all the new visible symbols
        if(!front) visibleSymbols.merge(Symbol.valueOf(gameBoard[x][y].getKingdom().toString()), 1, Integer::sum);
        else {
            for(int k = 0; k < 4; k++) {
                if(gameBoard[x][y].getCornerByIndex(k).getSymbol() != null && !gameBoard[x][y].getCornerByIndex(k).isHidden())
                    visibleSymbols.merge(gameBoard[x][y].getCornerByIndex(k).getSymbol(),1,Integer::sum);
            }
        }
        //add points
        if(front) {
            if (playingHand.get(cardIndex).getType().equals("gold")) {
                if (((GoldCard) playingHand.get(cardIndex)).getPointsForCoveringCorners()) {
                    addPoints(coveredCorners * playingHand.get(cardIndex).getPoints());
                } else if (((GoldCard) playingHand.get(cardIndex)).getCoveringSymbol() != null) {
                    addPoints(playingHand.get(cardIndex).getPoints() * visibleSymbols.get(((GoldCard) playingHand.get(cardIndex)).getCoveringSymbol()));
                } else {
                    addPoints(playingHand.get(cardIndex).getPoints());
                }
            } else {
                addPoints(playingHand.get(cardIndex).getPoints());
            }
        }

        possiblePlacements[x][y] = 0;
        playingHand.remove(cardIndex);
    }

    public void playInitialCard(boolean front) {
        gameBoard[10][20] = front ? initialcard : initialcard.getBackCard();
        placeOrder.add(new Placement(10,20,gameBoard[10][20].getViewForMatrix(),front));
        for(int k = 0; k < 4; k++) {
            System.out.println(gameBoard[10][20].getCornerByIndex(k).isHidden());
            possiblePlacements[10 + diagonals[k].getKey()][20 + diagonals[k].getValue()] = !gameBoard[10][20].getCornerByIndex(k).isHidden() ? 1 : 0;
            if(gameBoard[10][20].getCornerByIndex(k).getSymbol() != null && !gameBoard[10][20].getCornerByIndex(k).isHidden())
                visibleSymbols.merge(gameBoard[10][20].getCornerByIndex(k).getSymbol(), 1, Integer::sum);
        }

        if(!front) {
            for (Kingdom k : initialcard.getBackCard().getKingdoms())
                visibleSymbols.merge(Symbol.valueOf(k.toString()), 1, Integer::sum);
        }
    }

    public void draw(PlayableCard card) {
        playingHand.add(card);
    }

    public void setPlayingHand(ResourceCard card1, ResourceCard card2, GoldCard card3) {
        playingHand.add(0,card1);
        playingHand.add(1,card2);
        playingHand.add(2,card3);
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

    public GameCard[][] getGameBoard() {
        return gameBoard;
    }

    public PrivatePlayerView getPrivatePlayerView(){
        ArrayList<GameCardView> hand = new ArrayList<>();
        for(PlayableCard c : playingHand){
            hand.add(new GameCardView(c.getType(), c.getImageId(), c.printCard()));
        }
        return new PrivatePlayerView(hiddenGoal != null ? new GameCardView("goal", hiddenGoal.getImageId(), hiddenGoal.printCard()) : null, hand);
    }

    public PublicPlayerView getPublicPlayerView(){
        GameCardView[][] temp = new GameCardView[21][41];
        for(int i = 1; i < 20; i++){
            for(int j = 1; j < 40; j++){
                temp[i][j] = gameBoard[i][j] != null ? gameBoard[i][j].getViewForMatrix() : null;
            }
        }
        HashMap<String,Integer> vs = new HashMap<>();
        for(Symbol s : visibleSymbols.keySet()){
            vs.put(s.toString(), visibleSymbols.get(s));
        }
        return new PublicPlayerView(color != null ? color.toString() : "",score, temp, placeOrder, vs, getBooleanPossiblePlacements());
    }

    private boolean[][] getBooleanPossiblePlacements() {
        boolean[][] pp = new boolean[21][41];
        for(int i = 0; i < possiblePlacements.length; i++) {
            for(int j = 0; j < possiblePlacements[0].length; j++) {
                pp[i][j] = possiblePlacements[i][j] == 1;
            }
        }
        return pp;
    }
}
