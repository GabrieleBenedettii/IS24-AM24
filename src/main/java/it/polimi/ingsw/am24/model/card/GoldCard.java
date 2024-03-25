package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class GoldCard extends GameCard {
    private final Map<Symbol,Integer> requirements;
    private final int points;
    private final boolean pointsForCoveringCorners;
    private final Symbol pointsSymbol;
    private boolean requirementsMet;

    public GoldCard(String frontImage, String backImage, Symbol[] symbols, Kingdom kingdom, int points, boolean pointsForCoveringCorners, Symbol pointsSymbol, Map<Symbol,Integer> requirements) {
        super(frontImage, backImage, symbols, kingdom);
        this.points = points;
        this.pointsForCoveringCorners = pointsForCoveringCorners;
        this.pointsSymbol = pointsSymbol;
        this.requirements = requirements;
        this.requirementsMet = false;
    }
    public void checkRequirementsMet(HashMap<Symbol, Integer> visibleSymbols){
        boolean temp = true;
        for(Symbol s : requirements.keySet()){
            if(visibleSymbols.containsKey(s)) {
                if(requirements.get(s) > (visibleSymbols.get(s))){
                    temp = false;
                    break;
                }
            }
            else{
                temp = false;
                break;
            }
        }
        if(temp){
            requirementsMet = true;
        }
    }

    public boolean isRequirementsMet() {
        return requirementsMet;
    }

    public Map<Symbol, Integer> getRequirements() {
        return requirements;
    }

    public int getPoints() {
        return points;
    }

    public boolean getPointsForCoveringCorners() {
        return pointsForCoveringCorners;
    }

    public Symbol getPointsSymbol() {
        return pointsSymbol;
    }

    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Costants.getText(this.getKingdom()));
        text.append("\nCorners: ");
        for (CardCorner c: this.getCorners()) {
            text.append(c.isHidden() ? Costants.HIDDEN + " " : (c.getSymbol()!=null ? Costants.getText(c.getSymbol()) : Costants.EMPTY) + " ");
        }
        text.append("\nRequirements: ");
        for (Symbol s: requirements.keySet()) {
            text.append(Costants.getText(s));
            text.append(" -> ");
            text.append(requirements.get(s));
            text.append(" ");
        }
        //todo aggiungere la stampa dei punti
        return text.toString();
    }
}
