package it.polimi.ingsw.am24.view;

import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class UI {
    /**
     * Prompts the user to insert a nickname.
     */
    public abstract void show_insert_nickname();

    /**
     * Displays the visible symbols for the current player.
     *
     * @param gameView The current game view.
     */
    public abstract void show_visibleSymbols(GameView gameView);

    /**
     * Displays the game view, including the current player's board, points, and visible symbols.
     *
     * @param gameView The current game view to be displayed.
     */
    public abstract void show_gameView(GameView gameView);

    /**
     * Displays the main menu with available commands.
     */
    public abstract void show_menu();

    /**
     * Displays the table with available cards for the current player.
     *
     * @param gameView The current game view.
     * @param forChoice Whether the table is shown for card choice.
     */
    public abstract void show_table(GameView gameView,boolean forChoice);

    /**
     * Prompts the user to enter a command.
     */
    public abstract void show_command();

    /**
     * Prompts the user to enter a command.
     */
    public abstract void show_hidden_goal_card(GameCardView card);

    /**
     * Displays the starting table for the game.
     *
     * @param gameView The current game view.
     */
    public abstract void show_start_table(GameView gameView);

    /**
     * Displays the lobby menu, showing options to create a new lobby or join an existing one.
     * The method first shows the game logo and authors/rules before presenting the options.
     */
    public abstract void show_lobby();

    /**
     * Displays the available colors that the player can choose from.
     * The method highlights each color using ANSI color codes and prompts the user to choose a color.
     *
     * @param colors The list of available colors to be displayed.
     */
    public abstract void show_available_colors(ArrayList<String> colors);

    /**
     * Displays the player's hidden goals.
     * The method lists the descriptions of each hidden goal and prompts the user to choose one.
     *
     * @param views The list of GameCardView objects representing the hidden goals.
     */
    public abstract void show_hidden_goal(ArrayList<GameCardView> views);

    /**
     * Displays the initial card sides for the player to choose from.
     * The method shows the game logo, lists the descriptions of each initial card side, and prompts the user to choose one.
     *
     * @param views The list of GameCardView objects representing the initial card sides.
     */
    public abstract void show_initial_side(ArrayList<GameCardView> views);

    /**
     * Displays a message indicating the current player whose turn it is to play.
     *
     * @param gameView The current game view.
     * @param myNickname The nickname of the player viewing the message.
     */
    public abstract void show_current_player(GameView gameView, String myNickname);

    /**
     * Clears the screen and displays the game logo.
     * The logo is printed using ANSI escape codes to handle different character sets based on the console environment.
     */
    public abstract void show_logo();

    /**
     * Displays the authors and rules of the game.
     */
    public abstract void show_authors_and_rules();

    /**
     * Displays the game result, indicating whether the player won or lost.
     * The method also shows the final ranking of all players.
     *
     * @param winner Whether the current player is the winner.
     * @param rank The final rankings of all players.
     * @param winnerNick The nickname of the winner.
     */
    public abstract void show_winner_and_rank(boolean winner, HashMap<String,Integer> rank, String winnerNick);

    /**
     * Displays an error message indicating that the card placement was invalid.
     */
    public abstract void show_wrong_card_play();

    /**
     * Displays the list of players who have joined the lobby.
     * The method shows the game logo, the current player's name, and a list of all players in the lobby, indicating the current player.
     *
     * @param player The list of players in the lobby.
     * @param current The current player's nickname.
     * @param num The total number of players required for the lobby.
     */
    public abstract void show_joined_players(ArrayList<String> player, String current, int num);

    /**
     * Displays all messages in the message queue.
     */
    public abstract void show_messages();

    /**
     * Adds a "received message" to the message queue and displays it.
     *
     * @param sender The sender of the message.
     * @param receiver The receiver of the message.
     * @param message The content of the message.
     * @param time The time the message was sent.
     */
    public abstract void add_message_received(String sender, String receiver, String message, String time);

    /**
     * Adds a "sent message" to the message queue and displays it.
     *
     * @param receiver The receiver of the message.
     * @param message The content of the message.
     * @param time The time the message was sent.
     */
    public abstract void add_message_sent(String receiver, String message, String time);

    /**
     * Displays the chosen nickname of the player.
     *
     * @param nickname The chosen nickname.
     */
    public abstract void show_chosenNickname(String nickname);

    /**
     * Displays the network type selection menu, allowing the user to choose between different combinations of interfaces and network protocols.
     */
    public abstract void show_network_type();

    /**
     * Displays an error message indicating that the entered nickname is invalid.
     */
    public abstract void show_invalid_username();

    /**
     * Displays an error message indicating that the chosen nickname is already in use.
     */
    public abstract void show_nickname_already_used();

    /**
     * Displays an error message indicating that the chosen nickname cannot be empty.
     */
    public abstract void show_empty_nickname();

    /**
     * Displays an error message indicating that no lobby is available and prompts the user to create a new one.
     */
    public abstract void show_no_lobby_available();

    /**
     * Displays an error message indicating that the chosen color is not available and prompts the user to choose a different color.
     */
    public abstract void show_color_not_available();

    /**
     * Prompts the user to insert the number of players for the game.
     */
    public abstract void show_insert_num_player();

    /**
     * Displays an error message indicating that the input for the number of players is invalid.
     */
    public abstract void show_invalid_num_player();

    /**
     * Displays an error message indicating that the selected initial card side is invalid.
     */
    public abstract void show_invalid_initialcard();

    /**
     * Displays an error message indicating that the play command entered is invalid and provides the correct format.
     */
    public abstract void show_invalid_play_command();

    /**
     * Displays an error message indicating that the input for the play command contains invalid numbers.
     */
    public abstract void show_invalid_play_number();

    /**
     * Displays an error message indicating that the card index entered is invalid.
     */
    public abstract void show_invalid_index();

    /**
     * Displays an error message indicating that the coordinates entered are invalid.
     */
    public abstract void show_invalid_coordinates();

    /**
     * Displays an error message indicating that the card placement is invalid.
     */
    public abstract void show_invalid_positioning();

    /**
     * Displays an error message indicating that the command entered is invalid and provides guidance on how to get help.
     */
    public abstract void show_invalid_command();

    /**
     * Displays a generic error message.
     */
    public abstract void show_error();

    /**
     * Displays an error message indicating that the draw command entered is invalid and provides the correct format.
     */
    public abstract void show_invalid_draw_command();

    /**
     * Displays an error message indicating that an error occurred during game creation.
     */
    public abstract void show_error_create_game();

    /**
     * Displays an error message indicating that an error occurred while joining a game.
     */
    public abstract void show_error_join_game();

    /**
     * Displays an error message indicating that the player does not meet the requirements to place a card.
     */
    public abstract void show_requirements_not_met();

    /**
     * Displays a "Nan" message.
     */
    public abstract void show_nan();

    /**
     * Displays an error message indicating that the connection to the server has been lost.
     * The method waits for 3 seconds and then terminates the program.
     */
    public abstract void show_no_connection_error();
}

