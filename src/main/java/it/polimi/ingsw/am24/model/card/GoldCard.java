package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.am24.costants.Costants.getText;

public class GoldCard extends PlayableCard {
    private final Map<Symbol,Integer> requirements;
    private final boolean pointsForCoveringCorners;
    private final Symbol coveringSymbol;
    private boolean requirementsMet;

    public GoldCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points, boolean pointsForCoveringCorners, Symbol coveringSymbol, Map<Symbol,Integer> requirements) {
        super(imageId, symbols, kingdom, points);
        this.pointsForCoveringCorners = pointsForCoveringCorners;
        this.coveringSymbol = coveringSymbol;
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

    public boolean getPointsForCoveringCorners() {
        return pointsForCoveringCorners;
    }

    public Symbol getCoveringSymbol() {
        return coveringSymbol;
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
        text.append("\nPoints: ");
        if(pointsForCoveringCorners)
            text.append(this.getPoints() + " points for each covered corner");
        else if(coveringSymbol != null)
            text.append(this.getPoints() + " points for each " + coveringSymbol.toString());
        else
            text.append(this.getPoints());
        return text.toString();
    }
    public String getType(){
        return "gold";
    }

    public GameCardView getView() {
        return new GameCardView("Gold Card", this.getImageId(), this.printCard());
    }
}
