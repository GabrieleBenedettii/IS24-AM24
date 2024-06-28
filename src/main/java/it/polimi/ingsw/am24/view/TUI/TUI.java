package it.polimi.ingsw.am24.view.TUI;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.view.UI;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.*;

import org.fusesource.jansi.AnsiConsole;

/**
 * The TUI (Textual User Interface) class extends the UI class.
 * It provides various methods to display game information and interact with the user via the command line.
 */
public class TUI extends UI {
    private PrintStream out;
    private Queue<String> messages;

    /**
     * Initializes a new instance of the TUI class.
     * Sets up the output stream and message queue.
     */
    public TUI() {
        if(!(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0))
            AnsiConsole.systemInstall();
        messages = new LinkedList<>();
        out = new PrintStream(System.out, true);
    }

    /**
     * Prompts the user to insert a nickname.
     */
    @Override
    public void show_insert_nickname() {
        out.print("\nInsert nickname -> ");
    }

    /**
     * Displays the game view, including the current player's board, points, and visible symbols.
     *
     * @param gameView The current game view to be displayed.
     */
    @Override
    public void show_gameView(GameView gameView) {
        show_logo();
        GameCardView[][] board = gameView.getCommon().getPlayerView(gameView.getCurrent()).getBoard();
        int firstRow = board.length, lastRow = 0, firstColumn = board[0].length, lastColumn = 0;
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                if(board[r][c] != null){
                    if(r <= firstRow){
                        firstRow = r;
                    }
                    if(r >= lastRow){
                        lastRow = r;
                    }
                    if(c <= firstColumn){
                        firstColumn = c;
                    }
                    if(c >= lastColumn){
                        lastColumn = c;
                    }
                }
            }
        }
        if(firstRow > 1){
            firstRow -= 2;
        }
        if(lastRow < board.length - 2){
            lastRow += 2;
        }
        if(firstColumn > 1){
            firstColumn -= 2;
        }
        if(lastColumn < board[0].length - 2){
            lastColumn += 2;
        }

        out.println("\n" + gameView.getCurrent() + "'s table");
        out.println("current points: " + gameView.getCommon().getPlayerView(gameView.getCurrent()).getPlayerScore() + "\n");
        out.print("    ");
        //first line of column indexes
        for (int i = firstColumn; i <= lastColumn; i++) {
            out.print(i < 10 ? " " + i : i);
            out.print("  ");
        }
        out.print("     VISIBLE SYMBOLS");
        int visSymbIndex = 0;
        List<String> symbols = gameView.getCommon().getPlayerView(gameView.getCurrent()).getVisibleSymbols().keySet().stream().toList();
        //matrix
        for (int i = firstRow; i <= lastRow; i++) {
            out.print("\n  ");
            for (int j = 0; j < (lastColumn-firstColumn+1)*4+1; j++) {
                out.print("~");
            }
            visSymbIndex = show_visible_symbols(visSymbIndex, gameView, symbols);
            out.print("\n");
            //row index
            out.print((i < 10 ? " " + i : i));
            //first half of cells
            for (int j = firstColumn; j < lastColumn+1; j++) {
                out.print("|" + (gameView.getCommon().getPlayerView(gameView.getCurrent()).getPossiblePlacements()[i][j] ? Constants.BACKGROUND_BLACK : "") + (board[i][j] != null ?
                        board[i][j].getCardDescription().substring(0,21) : "   " + Constants.BACKGROUND_RESET));
            }
            out.print("|");
            //visible symbols
            visSymbIndex = show_visible_symbols(visSymbIndex, gameView, symbols);
            out.print("\n  ");
            for (int j = firstColumn; j < lastColumn+1; j++) {
                out.print("|" + (gameView.getCommon().getPlayerView(gameView.getCurrent()).getPossiblePlacements()[i][j] ? Constants.BACKGROUND_BLACK : "") + (board[i][j] != null ?
                        board[i][j].getCardDescription().substring(21) : "   " + Constants.BACKGROUND_RESET));
            }
            out.print("|");
            visSymbIndex = show_visible_symbols(visSymbIndex, gameView, symbols);
        }
        out.print("\n  ");
        for (int j = 0; j < (lastColumn-firstColumn+1)*4+1; j++) {
            out.print("~");
        }

        out.print("\n");

        for(int i = 0; i <  gameView.getPlayerView().getPlayerHand().size(); i++) {
            out.print("\n" + i + " - " + gameView.getPlayerView().getPlayerHand().get(i).getCardDescription());
        }

        out.print("\n");
    }

    /**
     * Prompts the user to enter a command.
     */
    @Override
    public void show_command() {
        System.out.print("\nCommand -> ");
    }

    /**
     * Prompts the hidden goal.
     */
    @Override
    public void show_hidden_goal_card(GameCardView card) {
        System.out.println("\nHIDDEN GOAL\n" + card.getCardDescription());
    }

    /**
     * Displays visible symbols for the current player.
     *
     * @param index The starting index of visible symbols to display.
     * @param gameView The current game view.
     * @param symbols The list of symbols to display.
     * @return The next index of visible symbols to be displayed.
     */
    public int show_visible_symbols(int index, GameView gameView, List<String> symbols) {
        if(index < gameView.getCommon().getPlayerView(gameView.getCurrent()).getVisibleSymbols().size()) {
            out.print("      " + Constants.getText(Symbol.valueOf(symbols.get(index))) + " -> " + gameView.getCommon().getPlayerView(gameView.getCurrent()).getVisibleSymbols().get(symbols.get(index)));
            index++;
        }
        return index;
    }

    /**
     * Displays the table with available cards for the current player.
     *
     * @param gameView The current game view.
     * @param forChoice Whether the table is shown for card choice.
     */
    @Override
    public void show_table(GameView gameView,boolean forChoice) {
        int i = 0;
        out.print("\n");
        for(GameCardView gcv : gameView.getCommon().getCommonBoardView().getResourceCards()) {
            out.println(i + " - " + gcv.getCardDescription());
            i++;
        }
        i = 2;
        for(GameCardView gcv : gameView.getCommon().getCommonBoardView().getGoldCards()) {
            out.println(i + " - " + gcv.getCardDescription());
            i++;
        }
        i = 4;
        out.println("\n" + i + " - Pick a card from RESOURCE CARD DECK, first card color: " + gameView.getCommon().getCommonBoardView().getResourceDeck());
        out.println((i + 1) + " - Pick a card from GOLD CARD DECK, first card color: " + gameView.getCommon().getCommonBoardView().getGoldDeck());
    }

    /**
     * Displays the starting table for the game.
     *
     * @param gameView The current game view.
     */
    public void show_start_table(GameView gameView) {
        out.print("\n");
        for(GameCardView gcv : gameView.getCommon().getCommonBoardView().getResourceCards()) {
            out.println("R - " + gcv.getCardDescription());
        }
        for(GameCardView gcv : gameView.getCommon().getCommonBoardView().getGoldCards()) {
            out.println("G - " + gcv.getCardDescription());
        }
        out.println("\nRESOURCE CARD DECK, first card color: " + gameView.getCommon().getCommonBoardView().getResourceDeck());
        out.println("GOLD CARD DECK, first card color: " + gameView.getCommon().getCommonBoardView().getGoldDeck());

        out.println("\nCOMMON GOALS");
        for(GameCardView gcv : gameView.getCommon().getCommonBoardView().getGoals()) {
            out.println(" * " + gcv.getCardDescription());
        }
    }

    /**
     * Displays the visible symbols for the current player.
     *
     * @param gameView The current game view.
     */
    @Override
    public void show_visibleSymbols(GameView gameView) {
        for(String s : gameView.getCommon().getPlayerView(gameView.getCurrent()).getVisibleSymbols().keySet()) {
            out.println(s + " -> " + gameView.getCommon().getPlayerView(gameView.getCurrent()).getVisibleSymbols().get(s));
        }
    }

    /**
     * Displays the main menu with available commands.
     */
    @Override
    public void show_menu() {
        out.println("Play a card -> /play <card> <\"front\"/\"back\"> <x> <y>");
        out.println("Show table -> /table");
        out.println("Show own visible symbols -> /visible");
        out.println("Draw a card (after play a card) -> /draw <option>");
        out.println("See the chat messages -> /chat");
        out.println("Show the hidden goal -> /hidden");
        out.println("Help -> /help");
    }

    /**
     * Displays the lobby menu, showing options to create a new lobby or join an existing one.
     * The method first shows the game logo and authors/rules before presenting the options.
     */
    @Override
    public void show_lobby(){
        show_logo();
        show_authors_and_rules();
        out.println("\nSelect an option: ");
        out.println("\t(1) create a new lobby");
        out.println("\t(2) join an existing lobby");
        out.print("Choice -> ");
    }

    /**
     * Displays the network type selection menu, allowing the user to choose between different combinations of interfaces and network protocols.
     */
    @Override
    public void show_network_type(){
        out.println("Select an option: ");
        out.println("\t(1) CLI+RMI");
        out.println("\t(2) CLI+Socket");
        out.println("\t(3) GUI+RMI");
        out.println("\t(4) GUI+Socket");
        out.print("Choice -> ");
    }

    /**
     * Displays the available colors that the player can choose from.
     * The method highlights each color using ANSI color codes and prompts the user to choose a color.
     *
     * @param colors The list of available colors to be displayed.
     */
    @Override
    public void show_available_colors(ArrayList<String> colors) {
        out.print("\nAVAILABLE COLORS: ");
        for(String s: colors) {
            if (s.equals("BLUE")) out.print(Constants.TEXT_BLUE + s + " " + Constants.TEXT_RESET);
            if (s.equals("RED")) out.print(Constants.TEXT_RED + s + " " + Constants.TEXT_RESET);
            if (s.equals("YELLOW")) out.print(Constants.TEXT_YELLOW + s + " " + Constants.TEXT_RESET);
            if (s.equals("GREEN")) out.print(Constants.TEXT_GREEN + s + " " + Constants.TEXT_RESET);
        }
        out.print("\nChoose your color -> ");
    }

    /**
     * Displays the player's hidden goals.
     * The method lists the descriptions of each hidden goal and prompts the user to choose one.
     *
     * @param views The list of GameCardView objects representing the hidden goals.
     */
    @Override
    public void show_hidden_goal(ArrayList<GameCardView> views) {
        out.println("\nYOUR SECRET GOALS");
        for(int i = 0; i < views.size(); i++) {
            out.println(i + " - " + views.get(i).getCardDescription());
        }
        out.print("\nChoose your secret goal: ");
    }

    /**
     * Displays the initial card sides for the player to choose from.
     * The method shows the game logo, lists the descriptions of each initial card side, and prompts the user to choose one.
     *
     * @param views The list of GameCardView objects representing the initial card sides.
     */
    @Override
    public void show_initial_side(ArrayList<GameCardView> views) {
        show_logo();
        out.println("\nINITIAL CARD SIDES");
        for(int i = 0; i < views.size(); i++) {
            out.println(i + " - " + views.get(i).getCardDescription());
        }
        out.print("\nChoose your initial card side: ");
    }

    /**
     * Displays a message indicating the current player whose turn it is to play.
     *
     * @param gameView The current game view.
     * @param myNickname The nickname of the player viewing the message.
     */
    @Override
    public void show_current_player(GameView gameView, String myNickname) {
        out.print("please wait, "+ gameView.getCurrent() +" is playing.");
    }

    /**
     * Displays an error message indicating that the entered nickname is invalid.
     */
    @Override
    public void show_invalid_username() {
        System.out.println("\nInvalid nickname. Please enter a nickname without special characters");
    }

    /**
     * Clears the console screen. The method handles different operating systems (Windows and others).
     * If an exception occurs during the process, it is caught but not handled explicitly.
     */
    public void clearScreen() {
        try{
            if( System.getProperty("os.name").contains("Windows") )
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                out.println("\033\143");
        }
        catch (Exception e){

        }
    }

    /**
     * Displays the game result, indicating whether the player won or lost.
     * The method also shows the final ranking of all players.
     *
     * @param winner Whether the current player is the winner.
     * @param rank The final rankings of all players.
     * @param winnerNick The nickname of the winner.
     */
    @Override
    public void show_winner_and_rank(boolean winner, HashMap<String, Integer> rank, String winnerNick) {
        show_logo();
        out.println(winner ? "Congratulation! you Won" : "Game Over, the winner is " + winnerNick+ " !");
        for(String player : rank.keySet()){
            out.println(rank.get(player)+ " : " +player);
        }
    }

    /**
     * Displays an error message indicating that the card placement was invalid.
     */
    @Override
    public void show_wrong_card_play() {
        out.println("invalid card placement");
    }

    /**
     * Displays the list of players who have joined the lobby.
     * The method shows the game logo, the current player's name, and a list of all players in the lobby, indicating the current player.
     *
     * @param players The list of players in the lobby.
     * @param current The current player's nickname.
     * @param num The total number of players required for the lobby.
     */
    @Override
    public void show_joined_players(ArrayList<String> players, String current, int num) {
        show_logo();
        out.print("\nPLAYERS IN LOBBY ["+players.size()+"/"+num+"] : ");
        for (String s: players){
            if (s.equals(current))
                System.out.print(s+"(you) ");
            else
                System.out.print(s+" ");
        }
    }

    /**
     * Displays all messages in the message queue.
     */
    @Override
    public void show_messages() {
        out.println("\n");
        for (String msg : messages){
            out.println(msg);
        }
    }

    /**
     * Adds a "received message" to the message queue and displays it.
     *
     * @param sender The sender of the message.
     * @param receiver The receiver of the message.
     * @param message The content of the message.
     * @param time The time the message was sent.
     */
    @Override
    public void add_message_received(String sender, String receiver, String message, String time) {
        String parsed_message = "[" + time + "] " + (receiver.equals("") ? "[PUBLIC]" : "[PRIVATE]") + " from " + sender + ": " + message;
        messages.add(parsed_message);
        out.println("\n" + parsed_message);
    }

    /**
     * Adds a "sent message" to the message queue and displays it.
     *
     * @param receiver The receiver of the message.
     * @param message The content of the message.
     * @param time The time the message was sent.
     */
    @Override
    public void add_message_sent(String receiver, String message, String time) {
        String parsed_message = "[" + time + "] " + (receiver.equals("") ? "[PUBLIC] to all" : ("[PRIVATE] to " + receiver)) + ": " + message;
        messages.add(parsed_message);
        out.println("\n" + parsed_message);
    }

    /**
     * Displays the chosen nickname of the player.
     *
     * @param nickname The chosen nickname.
     */
    @Override
    public void show_chosenNickname(String nickname) {
        out.println("your nickname: "+nickname);
    }

    /**
     * Displays an error message indicating that the chosen nickname is already in use.
     */
    @Override
    public void show_nickname_already_used(){System.out.println("\nNickname already used");}

    /**
     * Displays an error message indicating that the chosen nickname cannot be empty.
     */
    @Override
    public void show_empty_nickname() { System.out.println("\nNickname cannot be empty."); }

    /**
     * Displays an error message indicating that no lobby is available and prompts the user to create a new one.
     */
    @Override
    public void show_no_lobby_available(){System.out.println("\nNo lobby is available, please create a new one");}

    /**
     * Displays an error message indicating that the chosen color is not available and prompts the user to choose a different color.
     */
    @Override
    public void show_color_not_available(){System.out.println("\nColor not available. Please choose a different color.");}

    /**
     * Prompts the user to insert the number of players for the game.
     */
    @Override
    public void show_insert_num_player(){System.out.print("\nInsert number of players -> ");}

    /**
     * Displays an error message indicating that the input for the number of players is invalid.
     */
    @Override
    public void show_invalid_num_player(){System.out.println("\nInvalid input. Please enter a number between 2 and 4.");}

    /**
     * Displays an error message indicating that the selected initial card side is invalid.
     */
    @Override
    public void show_invalid_initialcard(){System.out.println("\nInvalid choice. Please select a valid card side.");}

    /**
     * Displays an error message indicating that the play command entered is invalid and provides the correct format.
     */
    @Override
    public void show_invalid_play_command(){System.out.println("\nInvalid command. Please enter in the format: play <cardIndex> <front/back> <x> <y>");}

    /**
     * Displays an error message indicating that the input for the play command contains invalid numbers.
     */
    @Override
    public void show_invalid_play_number(){System.out.println("\nInvalid input. Please enter valid integers for card index, x, and y coordinates.");}

    /**
     * Displays an error message indicating that the card index entered is invalid.
     */
    @Override
    public void show_invalid_index(){System.out.println("\nInvalid card index. Please enter a valid index.");}

    /**
     * Displays an error message indicating that the coordinates entered are invalid.
     */
    @Override
    public void show_invalid_coordinates(){System.out.println("\nInvalid coordinates. Please enter valid coordinates within the board.");}

    /**
     * Displays an error message indicating that the card placement is invalid.
     */
    @Override
    public void show_invalid_positioning(){System.out.println("\nInvalid positioning. You cannot place a card in this position.");}

    /**
     * Displays an error message indicating that the command entered is invalid and provides guidance on how to get help.
     */
    @Override
    public void show_invalid_command(){System.out.println("\nInvalid command. Type \"/help\" for help");}

    /**
     * Displays a generic error message.
     */
    @Override
    public void show_error(){System.out.println("\nAn error occurred. Please try again.");}

    /**
     * Displays an error message indicating that the draw command entered is invalid and provides the correct format.
     */
    @Override
    public void show_invalid_draw_command(){System.out.println("\nInvalid command. Please enter in the format: draw <cardIndex>");}

    /**
     * Displays an error message indicating that an error occurred during game creation.
     */
    @Override
    public void show_error_create_game(){System.out.println("\nError during creating game");}

    /**
     * Displays an error message indicating that an error occurred while joining a game.
     */
    @Override
    public void show_error_join_game(){System.out.println("\nError during joining game");}

    /**
     * Displays an error message indicating that the player does not meet the requirements to place a card.
     */
    @Override
    public void show_requirements_not_met(){System.out.println("\nYou can't place this card, you don't fulfil the requirements");}

    /**
     * Displays a "Nan" message.
     */
    @Override
    public  void show_nan(){System.out.println("Nan");}

    /**
     * Displays an error message indicating that the connection to the server has been lost.
     * The method waits for 3 seconds and then terminates the program.
     */
    @Override
    public void show_no_connection_error() {
        System.out.println("\nCONNECTION TO SERVER LOST!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(-1);
    }

    /**
     * Clears the screen and displays the game logo.
     * The logo is printed using ANSI escape codes to handle different character sets based on the console environment.
     */
    @Override
    public void show_logo() {
        clearScreen();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println("""
                ████████╗████████╗██████╗░░████████╗██╗░░░██╗
                ██╔═════╝██╔═══██║██╔══=██║██╔═════╝╚██╗░██╔╝
                ██║░░░░░░██║░░░██║██║░░░██║██████╗░░░╚████║░░
                ██║░░░░░░██║░░░██║██║░░░██║██╔═══╝░░░██║░██╚╗
                ████████╗████████║██████╔=╝████████╗██╔╝░░██║
                ╚═══════╝╚══════╝╚══════╝░░╚═══════╝╚═╝░░░╚═╝
                ███╗░░██╗░██████╗░████████╗██╗░░░██╗███████╗░░██████╗░██╗░░░░░░██╗░███████╗
                ████╗░██║██╔═══██╗╚══██╔══╝██║░░░██║██╔═══██╗██╔═══██╗██║░░░░░░██║██╔═════╝
                ██╔██╗██║████████║░░░██║░░░██║░░░██║███████╔╝████████║██║░░░░░░██║╚██████╗░
                ██║╚████║██╔═══██║░░░██║░░░██║░░░██║██╔═══██╗██╔═══██║██║░░░░░░██║░╚════██╗
                ██║░╚███║██║░░░██║░░░██║░░░████████║██║░░░██║██║░░░██║████████╗██║███████╔╝
                ╚═╝░░╚══╝╚═╝░░░╚═╝░░░╚═╝░░░╚═══════╝╚═╝░░░╚═╝╚═╝░░░╚═╝╚═══════╝╚═╝╚══════╝░
                """);
    }

    /**
     * Displays the authors and rules of the game.
     */
    @Override
    public void show_authors_and_rules() {
        out.println(Constants.AUTHORS);
        out.println(Constants.RULES);
    }
}
