package it.polimi.ingsw.am24.model.goal;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.card.CardCorner;
import it.polimi.ingsw.am24.model.card.GameCard;
import it.polimi.ingsw.am24.model.card.InitialCard;

public class VerticalDisposition extends GoalCard{
    private Kingdom mainKingodm;
    private Kingdom secondaryKingdom;

    public VerticalDisposition(String frontImage, String backImage, Integer points, Kingdom mainKingodm, Kingdom secondaryKingdom) {
        super(frontImage, backImage, points);
        this.mainKingodm = mainKingodm;
        this.secondaryKingdom = secondaryKingdom;
    }

    public int calculatePoints(InitialCard card){
        int direction = mainKingodm == Kingdom.PLANT || mainKingodm == Kingdom.INSECT ? 0 : 1;
        for (CardCorner child : card.getCorners()) {
            if(child.getCoveringCard() != null)
                depthFirstTraversal(child.getCoveringCard(),direction,0);
        }
        return this.getPoints();
    }

    int depthFirstTraversal(GameCard root, int direction, int points) {
        if (root == null) {
            return points;
        }

        /*GameCard card1 = root.getCorners().get(direction).getCoveringCard();
        GameCard card2 = card1 != null ? card1.getCorners().get(direction).getCoveringCard() : null;
        if(card1 != null && card2 != null && root.getKingdom() == kingdom && card1.getKingdom() == kingdom && card2.getKingdom() == kingdom) {
            points += this.getPoints();
        }*/

        // Visita ricorsivamente i figli
        for (CardCorner child : root.getCorners()) {
            if(child.getCoveringCard() != null)
                depthFirstTraversal(child.getCoveringCard(),direction,points);
        }

        return points;
    }

    public Kingdom getMainKingdom() {
        return mainKingodm;
    }

    public Kingdom getSecondaryKingdom() {
        return secondaryKingdom;
    }
}
