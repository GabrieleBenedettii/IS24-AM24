package it.polimi.ingsw.am24.view.commandLine;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.model.Symbol;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.view.flow.UI;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.*;

public class CLI extends UI {
    private PrintStream out;
    private Queue<String> messages;


    public CLI() {
        messages = new LinkedList<>();
        out = new PrintStream(System.out, true);
    }

    @Override
    public void show_insert_nickname() {
        out.print("\nInsert nickname -> ");
    }

    @Override
    public void show_gameView(GameView gameView) {
        int firstRow = gameView.getPlayerView().getBoard().length, lastRow = 0, firstColumn = gameView.getPlayerView().getBoard()[0].length, lastColumn = 0;
        for(int r = 0; r < gameView.getPlayerView().getBoard().length; r++){
            for(int c = 0; c < gameView.getPlayerView().getBoard()[0].length; c++){
                if(gameView.getPlayerView().getBoard()[r][c] != null){
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
        if(lastRow < gameView.getPlayerView().getBoard().length - 2){
            lastRow += 2;
        }
        if(firstColumn > 1){
            firstColumn -= 2;
        }
        if(lastColumn < gameView.getPlayerView().getBoard()[0].length - 2){
            lastColumn += 2;
        }

        out.println("\n" + gameView.getCurrent() + "'s table");
        out.println("current points: " + gameView.getPlayerView().getPlayerScore() + "\n");
        out.print("    ");
        //first line of column indexes
        for (int i = firstColumn; i <= lastColumn; i++) {
            out.print(i < 10 ? " " + i : i);
            out.print("  ");
        }
        out.print("\tVISIBLE SYMBOLS");
        int visSymbIndex = 0;
        List<String> symbols = gameView.getPlayerView().getVisibleSymbols().keySet().stream().toList();
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
                out.print("|" + (gameView.getPlayerView().getPossiblePlacements()[i][j] ? Constants.BACKGROUND_BLACK : "") + (gameView.getPlayerView().getBoard()[i][j] != null ?
                        gameView.getPlayerView().getBoard()[i][j].substring(0,21) : "   " + Constants.BACKGROUND_RESET));
            }
            out.print("|");
            //visible symbols
            visSymbIndex = show_visible_symbols(visSymbIndex, gameView, symbols);
            out.print("\n  ");
            for (int j = firstColumn; j < lastColumn+1; j++) {
                out.print("|" + (gameView.getPlayerView().getPossiblePlacements()[i][j] ? Constants.BACKGROUND_BLACK : "") + (gameView.getPlayerView().getBoard()[i][j] != null ?
                        gameView.getPlayerView().getBoard()[i][j].substring(21) : "   " + Constants.BACKGROUND_RESET));
            }
            out.print("|");
            visSymbIndex = show_visible_symbols(visSymbIndex, gameView, symbols);
        }
        out.print("\n  ");
        for (int j = 0; j < (lastColumn-firstColumn+1)*4+1; j++) {
            out.print("~");
        }

        for(int i = 0; i <  gameView.getPlayerView().getPlayerHand().size(); i++) {
            out.print("\n" + i + " - " + gameView.getPlayerView().getPlayerHand().get(i).getCardDescription());
        }
    }

    public int show_visible_symbols(int index, GameView gameView, List<String> symbols) {
        if(index < gameView.getPlayerView().getVisibleSymbols().size()) {
            out.print("\t\t" + Constants.getText(Symbol.valueOf(symbols.get(index))) + " -> " + gameView.getPlayerView().getVisibleSymbols().get(symbols.get(index)));
            index++;
        }
        return index;
    }

    @Override
    public void show_table(GameView gameView,boolean forChoice) {
        int i = 0;
        for(GameCardView gcv : gameView.getCommon().getResourceCards()) {
            out.println(i + " - " + gcv.getCardDescription());
            i++;
        }
        i = 2;
        for(GameCardView gcv : gameView.getCommon().getGoldCards()) {
            out.println(i + " - " + gcv.getCardDescription());
            i++;
        }
        i = 4;
        out.println(i + " - Pick a card from RESOURCE CARD DECK, first card color: " + gameView.getCommon().getResourceDeck());
        out.println((i + 1) + " - Pick a card from GOLD CARD DECK, first card color: " + gameView.getCommon().getGoldDeck());
    }

    public void show_start_table(GameView gameView) {
        for(GameCardView gcv : gameView.getCommon().getResourceCards()) {
            out.println("R - " + gcv.getCardDescription());
        }
        for(GameCardView gcv : gameView.getCommon().getGoldCards()) {
            out.println("G - " + gcv.getCardDescription());
        }
        out.println("RESOURCE CARD DECK, first card color: " + gameView.getCommon().getResourceDeck());
        out.println("GOLD CARD DECK, first card color: " + gameView.getCommon().getGoldDeck());

        out.println("\nCOMMON GOALS");
        for(GameCardView gcv : gameView.getCommon().getGoals()) {
            out.println(" * " + gcv.getCardDescription());
        }
    }

    @Override
    public void show_visibleSymbols(GameView gameView) {
        for(String s : gameView.getPlayerView().getVisibleSymbols().keySet()) {
            out.println(s + " -> " + gameView.getPlayerView().getVisibleSymbols().get(s));
        }
    }

    @Override
    public void show_menu() {
        out.println("Play a card -> /play <card> <\"front\"/\"back\"> <x> <y>");
        out.println("Show table -> /table");
        out.println("Show own visible symbols -> /visible");
        out.println("Draw a card (after play a card) -> /draw <option>");
        out.println("Help -> /help");
    }

    @Override
    public void show_lobby(){
        out.println("\nSelect an option: ");
        out.println("\t(1) create a new lobby");
        out.println("\t(2) join an existing lobby");
        out.print("Choice -> ");
    }

    @Override
    public void show_network_type(){
        out.println("Select an option: ");
        out.println("\t(1) CLI+RMI");
        out.println("\t(2) CLI+Socket");
        out.println("\t(3) GUI+RMI");
        out.println("\t(4) GUI+Socket");
        out.print("Choice -> ");
    }

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

    @Override
    public void show_hidden_goal(ArrayList<GameCardView> views) {
        out.println("\nYOUR SECRET GOALS");
        for(int i = 0; i < views.size(); i++) {
            out.println(i + " - " + views.get(i).getCardDescription());
        }
        out.print("Choose your secret goal: ");
    }

    @Override
    public void show_initial_side(ArrayList<GameCardView> views) {
        out.println("\nINITIAL CARD SIDES");
        for(int i = 0; i < views.size(); i++) {
            out.println(i + " - " + views.get(i).getCardDescription());
        }
        out.print("Choose your initial card side: ");
    }

    @Override
    public void show_current_player(String nickname) {
        out.print("please wait, "+ nickname +" is playing.");
    }


    //not working on intellij
    public void clearScreen() {
        try{
            if( System.getProperty("os.name").contains("Windows") )
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                out.println("\033[H\033[2J");
        }
        catch (Exception e){

        }
    }

    @Override
    public void show_winner_and_rank(boolean winner, HashMap<String, Integer> rank) {
        out.println(winner ? "you Win" : "Game Over");
        for(String player : rank.keySet()){
            out.println(rank.get(player)+ " : " +player);
        }
    }

    @Override
    public void show_wrong_card_play() {
        out.println("invalid card placement");
    }

    @Override
    public void show_joined_players(ArrayList<String> players) {
        out.print("\nPLAYERS IN LOBBY: ");
        players.forEach((p) -> out.print(p));
    }

    @Override
    public void show_message() {
        for (String msg : messages){
            out.println(msg);
        }
    }

    @Override
    public void add_message(String message) {
        messages.add(message);
    }

    @Override
    public void show_chosenNickname(String nickname) {
        out.println("your nickname: "+nickname);
    }

    @Override
    public void show_logo() {
        //clearScreen();
        /*new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println("""
                                                                                                           \s
                                                                                                           \s
                  ,----..                                                                                  \s
                 /   /   \\                                   ,--,                                          \s
                |   :     :  __  ,-.                 ,---, ,--.'|    ,---.                                 \s
                .   |  ;. /,' ,'/ /|             ,-+-. /  ||  |,    '   ,'\\                                \s
                .   ; /--` '  | |' | ,--.--.    ,--.'|'   |`--'_   /   /   |                               \s
                ;   | ;    |  |   ,'/       \\  |   |  ,"' |,' ,'| .   ; ,. :                               \s
                |   : |    '  :  / .--.  .-. | |   | /  | |'  | | '   | |: :                               \s
                .   | '___ |  | '   \\__\\/: . . |   | |  | ||  | : '   | .; :                               \s
                '   ; : .'|;  : |   ," .--.; | |   | |  |/ '  : |_|   :    |                               \s
                '   | '/  :|  , ;  /  /  ,.  | |   | |--'  |  | '.'\\   \\  /                                \s
                |   :    /  ---'  ;  :   .'   \\|   |/      ;  :    ;`----'                                 \s
                 \\   \\ .'         |  ,     .-./'---'       |  ,   /                                        \s
                  `---`            `--`---'                 ---`-'                                         \s
                  ,----..                                   ___                                            \s
                 /   /   \\                                ,--.'|_    ,--,                                  \s
                |   :     :  __  ,-.                      |  | :,' ,--.'|    ,---.        ,---,            \s
                .   |  ;. /,' ,'/ /|                      :  : ' : |  |,    '   ,'\\   ,-+-. /  | .--.--.   \s
                .   ; /--` '  | |' | ,---.     ,--.--.  .;__,'  /  `--'_   /   /   | ,--.'|'   |/  /    '  \s
                ;   | ;    |  |   ,'/     \\   /       \\ |  |   |   ,' ,'| .   ; ,. :|   |  ,"' |  :  /`./  \s
                |   : |    '  :  / /    /  | .--.  .-. |:__,'| :   '  | | '   | |: :|   | /  | |  :  ;_    \s
                .   | '___ |  | ' .    ' / |  \\__\\/: . .  '  : |__ |  | : '   | .; :|   | |  | |\\  \\    `. \s
                '   ; : .'|;  : | '   ;   /|  ," .--.; |  |  | '.'|'  : |_|   :    ||   | |  |/  `----.   \\\s
                '   | '/  :|  , ; '   |  / | /  /  ,.  |  ;  :    ;|  | '.'\\   \\  / |   | |--'  /  /`--'  /\s
                |   :    /  ---'  |   :    |;  :   .'   \\ |  ,   / ;  :    ;`----'  |   |/     '--'.     / \s
                 \\   \\ .'          \\   \\  / |  ,     .-./  ---`-'  |  ,   /         '---'        `--'---'  \s
                  `---`             `----'   `--`---'               ---`-'                                 \s
                """);*/
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println("""
                ████████╗███████╗██████╗░░███████╗██╗░░░██╗
                ██╔═════╝██╔══██║██╔══=██║██╔════╝╚██╗░██╔╝
                ██║░░░░░░██║░░██║██║░░░██║█████╗░░░╚████║░░
                ██║░░░░░░██║░░██║██║░░░██║██╔══╝░░░██║░██╚╗
                ████████╗███████║██████╔=╝███████╗██╔╝░░██║
                ╚═══════╝╚══════╝╚═════╝░░╚══════╝╚═╝░░░╚═╝
                ███╗░░██╗ █████╗ ████████╗██╗░░░░██╗██████╗░██████╗░██╗░░░░░██╗░██████╗
                ████╗░██║██╔══██╗╚══██╔══╝██║░░░░██║██╔══██╗██╔══██╗██║░░░░░██║██╔════╝
                ██╔██╗██║███████║░░░██║░░░██║░░░░██║██████╔╝███████║██║░░░░░██║╚█████╗░
                ██║╚████║██╔══██║░░░██║░░░██║░░░░██║██╔══██╗██╔══██║██║░░░░░██║░╚═══██╗
                ██║░╚███║██║░░██║░░░██║░░░█████████║██║░░██║██║░░██║███████╗██║██████╔╝
                ╚═╝░░╚══╝╚═╝░░╚═╝░░░╚═╝░░░╚═══════=╝╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚═╝╚═════╝░
                """);
        out.println(Constants.AUTHORS);
        out.println(Constants.RULES);
    }
}
