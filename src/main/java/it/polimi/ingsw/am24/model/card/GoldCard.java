package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class GoldCard extends GameCard {
    private final Kingdom kingdom;
    private final Map<Symbol,Integer> requirements;
    private final int points;
    private final boolean pointsForCoveringCorners;
    private final Symbol pointsSymbol;
    private boolean requirementsMet;

    public GoldCard(String frontImage, String backImage, Symbol[] symbols, Kingdom kingdom, int points, boolean pointsForCoveringCorners, Symbol pointsSymbol, Map<Symbol,Integer> requirements) {
        super(frontImage, backImage, symbols);
        this.kingdom = kingdom;
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

    public Kingdom getKingdom() {
        return kingdom;
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
}
