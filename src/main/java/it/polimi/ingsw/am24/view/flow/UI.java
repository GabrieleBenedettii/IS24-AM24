package it.polimi.ingsw.am24.view.flow;

import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;

import java.util.ArrayList;
import java.util.HashMap;

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
    public abstract void show_current_player(GameView gameView, String myNickname);
    public abstract void show_logo();
    public abstract void show_winner_and_rank(boolean winner, HashMap<String,Integer> rank, String winnerNick);
    public abstract void show_wrong_card_play();
    public abstract void show_joined_players(ArrayList<String> player, String current, int num);
    public abstract void show_message();
    public abstract void add_message_received(String sender, String receiver, String message, String time);
    public abstract void add_message_sent(String receiver, String message, String time);
    public abstract void show_chosenNickname(String nickname);
    public abstract void show_network_type();
    public abstract void show_invalid_username();
    public abstract void show_nickname_already_used();
    public abstract void show_empty_nickname();
    public abstract void show_no_lobby_available();
    public abstract void show_color_not_available();
    public abstract void show_insert_num_player();
    public abstract void show_invalid_num_player();
    public abstract void show_invalid_initialcard();
    public abstract void show_invalid_play_command();
    public abstract void show_invalid_play_number();
    public abstract void show_invalid_index();
    public abstract void show_invalid_coordinates();
    public abstract void show_invalid_positioning();
    public abstract void show_invalid_command();
    public abstract void show_error();
    public abstract void show_invalid_draw_command();
    public abstract void show_error_create_game();
    public abstract void show_error_join_game();
    public abstract void show_requirements_not_met();
    public abstract void show_nan();
    public abstract void show_no_connection_error();
}
