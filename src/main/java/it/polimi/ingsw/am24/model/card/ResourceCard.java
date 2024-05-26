package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.Kingdom;
public class ResourceCard extends PlayableCard{

    public ResourceCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols, kingdom, points);
    }

    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Constants.getText(this.getKingdom()));
        text.append("\n\tCorners: ");
        for (CardCorner c: this.getCorners()) {
            text.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        text.append("\n\tPoints: ");
        text.append(this.getPoints());
        return text.toString();
    }
    public String getType(){
        return "resource";
    }


}
