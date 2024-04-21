package it.polimi.ingsw.am24.view.flow;

import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class UI {

    private GameView gameView;

    protected abstract void show_visibleSymbols(GameView gameView);
    protected abstract void show_gameView(GameView gameView);
    protected abstract void show_menu();
    protected abstract void show_table(GameView gameView,boolean forChoice);
    protected abstract void show_lobby();
    protected abstract void show_available_colors(ArrayList<String> colors);
    protected abstract void show_hidden_goal(GameCardView[] views);
    protected abstract void show_inital_side(GameCardView[] views);
    protected abstract void show_current_player(String nickname);
    protected abstract void show_logo();
    protected abstract void show_winner_and_rank(boolean winner, HashMap<String,Integer> rank);
    protected abstract void show_wrong_card_play();
    protected abstract void show_joined_players(ArrayList<String> player);
    protected abstract void show_message();
    protected abstract void add_message(String sender, String message, String time);

}
