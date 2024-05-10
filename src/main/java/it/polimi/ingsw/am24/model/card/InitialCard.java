package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;

import java.util.ArrayList;

public class InitialCard extends GameCard{
    private final ArrayList<Kingdom> kingdoms;
    private InitialCard backCard;

    public InitialCard(int imageId, Symbol[] symbols, ArrayList<Kingdom> kingdoms) {
        super(imageId,symbols,null);
        this.kingdoms = kingdoms;
    }

    public InitialCard getBackCard() {
        return backCard;
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    @Override
    public String printCard() {
        StringBuilder print = new StringBuilder("FRONT\n\tKingdoms: ");
        for (Kingdom k: kingdoms) {
            print.append(" ").append(Constants.getText(k)).append(" ");
        }
        print.append("\n\tCorners: ");
        for (CardCorner c: this.getCorners()) {
            print.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        return print.toString();
    }

    public String printBackCard() {
        StringBuilder print = new StringBuilder("BACK\n\tKingdoms:");
        for (Kingdom k: backCard.kingdoms) {
            print.append(" ").append(Constants.getText(k)).append(" ");
        }
        print.append("\n\tCorners: ");
        for (CardCorner c: backCard.getCorners()) {
            print.append(c.isHidden() ? Constants.HIDDEN + " " : (c.getSymbol()!=null ? Constants.getText(c.getSymbol()) : Constants.EMPTY) + " ");
        }
        return print.toString();
    }

    public GameCardView getView() {
        return new GameCardView("Initial Card - front", this.getImageId(), this.printCard());
    }

    public GameCardView getBackView() {
        return new GameCardView("Initial Card - back", this.getImageId(), this.printBackCard());
    }
    public int getId(){
        return getImageId();
    }
}
