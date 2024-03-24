package it.polimi.ingsw.am24.model.card;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.ArrayList;

public class InitialCard extends GameCard{
    private final ArrayList<Kingdom> kingdoms;
    private InitialCard backCard;

    public InitialCard(String frontImage, String backImage, Symbol[] symbols, ArrayList<Kingdom> kingdoms) {
        super(frontImage, backImage, symbols);
        this.kingdoms = kingdoms;
        //TO DO
        //controllare se il retro è a sua volta un'altra initialCard perché può avere symbols negli angoli
        //probabilmente ha senso gestirla non come figlia di GameCard
    }

    public InitialCard getBackCard() {
        return backCard;
    }

    public void setBackCard(InitialCard backCard) {
        this.backCard = backCard;
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public String printCard() {
        StringBuilder print = new StringBuilder("Kingdoms: ");
        for (Kingdom k: kingdoms) {
            print.append(" ").append(Costants.getText(k)).append(" ");
        }
        print.append("\nCorners: ");
        for (CardCorner c: this.getCorners()) {
            print.append(c.isHidden() ? Costants.HIDDEN + " " : (c.getSymbol()!=null ? Costants.getText(c.getSymbol()) : Costants.EMPTY) + " ");
        }
        return print.toString();
    }
}
