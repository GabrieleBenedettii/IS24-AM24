package it.polimi.ingsw.am24.model;

import it.polimi.ingsw.am24.Exceptions.AlreadyDrawnException;
import it.polimi.ingsw.am24.Exceptions.InvalidPositioningException;
import it.polimi.ingsw.am24.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.am24.constants.Constants;
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

/**
 * The {@code Player} class represents a player in the game, holding their state including nickname,
 * color, score, playing hand, initial card, hidden goal, visible symbols, game board, placement order,
 * and possible placements.
 */
public class Player {
    /** The player's nickname, initialized through the constructor and cannot be changed. */
    private final String nickname;
    /** The color assigned to the player. */
    private PlayerColor color;
    /** The player's current score. */
    private int score;
    /** The player's hand, which holds the playable cards. */
    private final ArrayList<PlayableCard> playingHand;
    /** The initial card of the player. */
    private InitialCard initialcard;
    /** The hidden goal card of the player. */
    private GoalCard hiddenGoal;
    /** A map tracking visible symbols and their counts. */
    private final HashMap<Symbol,Integer> visibleSymbols;
    /** The player's game board represented as a 2D array of game cards. */
    private final GameCard[][] gameBoard;
    /** The order in which placements have been made on the game board. */
    private final ArrayList<Placement> placeOrder;
    /** An array indicating possible placements on the game board. */
    private final int[][] possiblePlacements;
    /** An array holding the diagonal positions for placement calculations. */
    private final Pair<Integer, Integer>[] diagonals;

    /**
     * Constructs a new player with the specified nickname.
     * Initializes the playing hand, visible symbols, game board, placement order,
     * possible placements, and diagonals.
     *
     * @param nickname the player's nickname
     */
    public Player(String nickname){
        this.nickname = nickname;
        this.playingHand = new ArrayList<>();
        this.visibleSymbols = new HashMap<>();
        for (Symbol s: Symbol.values()) {
            visibleSymbols.put(s,0);
        }
        this.gameBoard = new GameCard[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];
        this.placeOrder = new ArrayList<>();
        this.possiblePlacements = new int[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];
        Arrays.stream(possiblePlacements).forEach(row->Arrays.fill(row,-1));
        this.score = 0;
        diagonals = new Pair[4];
        diagonals[0] = new Pair<>(-1,-1);
        diagonals[1] = new Pair<>(-1,1);
        diagonals[2] = new Pair<>(1,-1);
        diagonals[3] = new Pair<>(1, 1);
    }

    /**
     * Plays a card from the player's hand at the specified position on the game board.
     *
     * @param cardIndex the index of the card in the player's hand
     * @param front     whether the front side of the card should be played
     * @param x         the x-coordinate on the game board
     * @param y         the y-coordinate on the game board
     * @throws InvalidPositioningException if the position is invalid
     * @throws RequirementsNotMetException if the card's requirements are not met
     */
    public void play(int cardIndex, boolean front, int x, int y) throws InvalidPositioningException, RequirementsNotMetException {
        if(possiblePlacements[x][y] != 1){
            throw new InvalidPositioningException();
        }
        if(playingHand.get(cardIndex) instanceof GoldCard && front){
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
            if (playingHand.get(cardIndex) instanceof GoldCard) {
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

    /**
     * Plays the initial card at the center of the game board.
     *
     * @param front whether the front side of the card should be played
     */
    public void playInitialCard(boolean front) {
        gameBoard[Constants.MATRIX_DIMENSION/2][Constants.MATRIX_DIMENSION/2] = front ? initialcard : initialcard.getBackCard();
        placeOrder.add(new Placement(Constants.MATRIX_DIMENSION/2,Constants.MATRIX_DIMENSION/2,gameBoard[Constants.MATRIX_DIMENSION/2][Constants.MATRIX_DIMENSION/2].getViewForMatrix(),front));
        for(int k = 0; k < 4; k++) {
            possiblePlacements[Constants.MATRIX_DIMENSION/2 + diagonals[k].getKey()][Constants.MATRIX_DIMENSION/2 + diagonals[k].getValue()] = !gameBoard[Constants.MATRIX_DIMENSION/2][Constants.MATRIX_DIMENSION/2].getCornerByIndex(k).isHidden() ? 1 : 0;
            if(gameBoard[Constants.MATRIX_DIMENSION/2][Constants.MATRIX_DIMENSION/2].getCornerByIndex(k).getSymbol() != null && !gameBoard[Constants.MATRIX_DIMENSION/2][Constants.MATRIX_DIMENSION/2].getCornerByIndex(k).isHidden())
                visibleSymbols.merge(gameBoard[Constants.MATRIX_DIMENSION/2][Constants.MATRIX_DIMENSION/2].getCornerByIndex(k).getSymbol(), 1, Integer::sum);
        }

        if(!front) {
            for (Kingdom k : initialcard.getBackCard().getKingdoms())
                visibleSymbols.merge(Symbol.valueOf(k.toString()), 1, Integer::sum);
        }
    }

    /**
     * Draws a card and adds it to the player's hand.
     *
     * @param card the card to be drawn
     * @throws AlreadyDrawnException if the player has already drawn the maximum number of cards
     */
    public void draw(PlayableCard card) throws AlreadyDrawnException {
        if(playingHand.size() > 2) throw new AlreadyDrawnException();
        playingHand.add(card);
    }

    /**
     * Sets the player's hand with the specified cards.
     *
     * @param card1 the first resource card
     * @param card2 the second resource card
     * @param card3 the gold card
     */
    public void setPlayingHand(ResourceCard card1, ResourceCard card2, GoldCard card3) {
        playingHand.add(0,card1);
        playingHand.add(1,card2);
        playingHand.add(2,card3);
    }

    /**
     * Gets the player's hidden goal card.
     *
     * @return the hidden goal card
     */
    public GoalCard getHiddenGoal() {
        return hiddenGoal;
    }

    /**
     * Sets the player's hidden goal card.
     *
     * @param obj the hidden goal card
     */
    public void setHiddenGoal(GoalCard obj){
        this.hiddenGoal = obj;
    }

    /**
     * Sets the player's color.
     *
     * @param color the player's color
     */
    public void setColor(PlayerColor color){
        this.color = color;
    }

    /**
     * Sets the player's initial card.
     *
     * @param card the initial card
     */
    public void setInitialCard(InitialCard card) {
        this.initialcard = card;
    }

    /**
     * Gets the player's nickname.
     *
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the player's color.
     *
     * @return the player's color
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Gets the player's current score.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds points to the player's score.
     *
     * @param points the points to be added
     */
    public void addPoints(int points) {
        this.score += points;
    }

    /**
     * Gets the player's current hand of playable cards.
     *
     * @return the player's playing hand
     */
    public ArrayList<PlayableCard> getPlayingHand() {
        return playingHand;
    }

    /**
     * Gets the player's initial card.
     *
     * @return the initial card
     */
    public InitialCard getInitialcard() {
        return initialcard;
    }

    /**
     * Gets the map of visible symbols and their counts.
     *
     * @return the map of visible symbols
     */
    public HashMap<Symbol,Integer> getVisibleSymbols() {
        return visibleSymbols;
    }

    /**
     * Gets the player's game board.
     *
     * @return the game board
     */
    public GameCard[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Gets the private player view, including the hidden goal and hand cards.
     *
     * @return the private player view
     */
    public PrivatePlayerView getPrivatePlayerView(){
        ArrayList<GameCardView> hand = new ArrayList<>();
        for(PlayableCard c : playingHand){
            hand.add(new GameCardView(c.getType(), c.getImageId(), c.printCard()));
        }
        return new PrivatePlayerView(hiddenGoal != null ? new GameCardView("goal", hiddenGoal.getImageId(), hiddenGoal.printCard()) : null, hand);
    }

    /**
     * Gets the public player view, including color, score, game board, placement order, and visible symbols.
     *
     * @return the public player view
     */
    public PublicPlayerView getPublicPlayerView(){
        GameCardView[][] temp = new GameCardView[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];
        for(int i = 0; i < Constants.MATRIX_DIMENSION; i++){
            for(int j = 0; j < Constants.MATRIX_DIMENSION; j++){
                temp[i][j] = gameBoard[i][j] != null ? gameBoard[i][j].getViewForMatrix() : null;
            }
        }
        HashMap<String,Integer> vs = new HashMap<>();
        for(Symbol s : visibleSymbols.keySet()){
            vs.put(s.toString(), visibleSymbols.get(s));
        }
        return new PublicPlayerView(color != null ? color.toString() : "",score, temp, placeOrder, vs, getBooleanPossiblePlacements());
    }

    /**
     * Converts the possible placements array to a boolean array.
     *
     * @return a boolean array representing possible placements
     */
    private boolean[][] getBooleanPossiblePlacements() {
        boolean[][] pp = new boolean[Constants.MATRIX_DIMENSION][Constants.MATRIX_DIMENSION];
        for(int i = 0; i < possiblePlacements.length; i++) {
            for(int j = 0; j < possiblePlacements[0].length; j++) {
                pp[i][j] = possiblePlacements[i][j] == 1;
            }
        }
        return pp;
    }
}
