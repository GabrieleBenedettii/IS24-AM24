package it.polimi.ingsw.am24.Messages;

import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.card.PlayableCard;

import java.util.ArrayList;
import java.util.HashMap;

public class SetupMessage extends Message{
    private ArrayList<PlayableCard> playableHand;
    private Player player;
    private PlayableCard[][] board;
    private HashMap<Symbol, Integer> visibleSymbols;
    private ArrayList<PlayableCard> faceUpDrawableCards;

    public SetupMessage(ArrayList<PlayableCard> playableHand, Player player, PlayableCard[][] board, HashMap<Symbol, Integer> visibleSymbols, ArrayList<PlayableCard> faceUpDrawableCards) {
        this.playableHand = playableHand;
        this.player = player;
        this.board = board;
        this.visibleSymbols = visibleSymbols;
        this.faceUpDrawableCards = faceUpDrawableCards;
    }
}
