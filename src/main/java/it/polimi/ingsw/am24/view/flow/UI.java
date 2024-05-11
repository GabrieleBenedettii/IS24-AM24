package it.polimi.ingsw.am24.view.flow;

import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class UI {
    public abstract void show_insert_nickname();
    public abstract void show_visibleSymbols(GameView gameView);
    public abstract void show_gameView(GameView gameView);
    public abstract void show_menu();
    public abstract void show_table(GameView gameView,boolean forChoice);
    public abstract void show_start_table(GameView gameView);
    public abstract void show_lobby();
    public abstract void show_available_colors(ArrayList<String> colors);
    public abstract void show_hidden_goal(ArrayList<GameCardView> views);
    public abstract void show_initial_side(ArrayList<GameCardView> views);
    public abstract void show_current_player(String nickname);
    public abstract void show_logo();
    public abstract void show_winner_and_rank(boolean winner, HashMap<String,Integer> rank);
    public abstract void show_wrong_card_play();
    public abstract void show_joined_players(ArrayList<String> player);
    public abstract void show_message();
    public abstract void add_message(String message);
    public abstract void show_chosenNickname(String nickname);
    public abstract void show_network_type();
}
