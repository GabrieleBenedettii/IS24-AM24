package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.model.Kingdom;
public class ResourceCard extends PlayableCard{

    public ResourceCard(int imageId, Symbol[] symbols, Kingdom kingdom, int points) {
        super(imageId, symbols, kingdom, points);
    }

    public String printCard() {
        StringBuilder text = new StringBuilder("Kingdom: ");
        text.append(Costants.getText(this.getKingdom()));
        text.append("\nCorners: ");
        for (CardCorner c: this.getCorners()) {
            text.append(c.isHidden() ? Costants.HIDDEN + " " : (c.getSymbol()!=null ? Costants.getText(c.getSymbol()) : Costants.EMPTY) + " ");
        }
        text.append("\nPoints: ");
        text.append(this.getPoints());
        return text.toString();
    }
    public String getType(){
        return "resource";
    }


}
