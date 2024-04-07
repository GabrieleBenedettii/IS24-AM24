package it.polimi.ingsw.am24.modelView;

import it.polimi.ingsw.am24.model.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class PublicBoardView implements Serializable {
    private final ArrayList<GameCardView> goals = new ArrayList<>();
    private final ArrayList<GameCardView> resourceCards = new ArrayList<>();
    private final ArrayList<GameCardView> goldCards = new ArrayList<>();
    public PublicBoardView(Game game){
        for(int i = 0; i < 2; i++){
            this.goals.add(new GameCardView("goal", game.getCommonGoals().get(i).getImageId(),game.getCommonGoals().get(i).printCard()));
            this.goldCards.add(new GameCardView("gold", game.getVisibleGoldCard().get(i).getImageId(), game.getVisibleGoldCard().get(i).printCard()));
            this.resourceCards.add(new GameCardView("resource", game.getVisibleResCard().get(i).getImageId(), game.getVisibleResCard().get(i).printCard()));
        }
    }
    public ArrayList<GameCardView> getGoals() {
        return goals;
    }
    public ArrayList<GameCardView> getResourceCards() {
        return resourceCards;
    }
    public ArrayList<GameCardView> getGoldCards() {
        return goldCards;
    }
}
