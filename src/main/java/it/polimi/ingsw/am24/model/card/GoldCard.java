package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.am24.constants.Constants.getText;

public class GoldCard extends PlayableCard {
    private final Map<Symbol,Integer> requirements;
    private final boolean pointsForCoveringCorners;
    private final Symbol coveringSymbol;


    public GoldCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points, boolean pointsForCoveringCorners, Symbol pointSymbol, Map<Symbol,Integer> requirements) {
        super(imageId, symbols, kingdom, points);
        this.pointsForCoveringCorners = pointsForCoveringCorners;
        this.coveringSymbol = pointSymbol;
        this.requirements = requirements;

    }
    public boolean checkRequirementsMet(HashMap<Symbol, Integer> visibleSymbols){
        for(Symbol s : requirements.keySet()){
            if(requirements.get(s) > (visibleSymbols.get(s))){
                return false;
            }
        }
        return true;
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
        text.append(Constants.getText(this.getKingdom()));
        text.append("\n\tCorners: ");
        for (CardCorner c: this.getCorners()) {
            text.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        text.append("\n\tRequirements: ");
        for (Symbol s: requirements.keySet()) {
            text.append(Constants.getText(s));
            text.append(" -> ");
            text.append(requirements.get(s));
            text.append(" ");
        }
        text.append("\n\tPoints: ");
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
