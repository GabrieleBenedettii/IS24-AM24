package it.polimi.ingsw.am24.view.commandLine;

import it.polimi.ingsw.am24.costants.Costants;
import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;
import it.polimi.ingsw.am24.view.flow.UI;
import it.polimi.ingsw.am24.view.flow.UserInterface;
import javafx.scene.Cursor;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class CLI extends UI {
    private PrintStream out;
    //private Queue<String> message = new LinkedList<String>();
    private Queue<String> message;


    public CLI() {
        message = new LinkedList<String>();
    }

    @Override
    protected void show_gameView(GameView gameView) {
        out.println("current points: " + gameView.getPlayerView().getPlayerScore());
        out.println("\n" + gameView.getCurrent() + "'s table");
        out.print("    ");
        for (int i = 0; i < gameView.getPlayerView().getBoard()[0].length; i++) {
            out.print(i < 10 ? " " + i : i);
            out.print("  ");
        }
        for (int i = 0; i < gameView.getPlayerView().getBoard().length; i++) {
            out.print("\n  ");
            for (int j = 0; j < gameView.getPlayerView().getBoard()[0].length*4+1; j++) {
                out.print("~");
            }
            out.print("\n");
            out.print((i < 10 ? " " + i : i));
            for (int j = 0; j < gameView.getPlayerView().getBoard()[0].length; j++) {
                out.print("|" + (gameView.getPlayerView().getPossiblePlacements()[i][j] ? Costants.BACKGROUND_BLACK : "") + (gameView.getPlayerView().getBoard()[i][j] != null ?
                        gameView.getPlayerView().getBoard()[i][j].substring(0,21) : "   " + Costants.BACKGROUND_RESET));
            }
            out.print("|\n  ");
            for (int j = 0; j < gameView.getPlayerView().getBoard()[0].length; j++) {
                out.print("|" + (gameView.getPlayerView().getPossiblePlacements()[i][j] ? Costants.BACKGROUND_BLACK : "") + (gameView.getPlayerView().getBoard()[i][j] != null ?
                        gameView.getPlayerView().getBoard()[i][j].substring(21) : "   " + Costants.BACKGROUND_RESET));
            }
            out.print("|");

        }
        out.print("\n  ");
        for (int j = 0; j < gameView.getPlayerView().getBoard()[0].length*4+1; j++) {
            out.print("~");
        }

        for(int i = 0; i <  gameView.getPlayerView().getPlayerHand().size(); i++) {
            out.print("\n" + i + "- " + gameView.getPlayerView().getPlayerHand().get(i).getCardDescription());
        }
    }

    @Override
    protected void show_table(GameView gameView,boolean forChoice) {
        int i = 0;
        for(GameCardView gcv : gameView.getCommon().getResourceCards()) {
            out.println(forChoice ? i + " -> " + gcv.getCardDescription() : gcv.getCardDescription());
            i++;
        }
        for(GameCardView gcv : gameView.getCommon().getGoldCards()) {
            out.println(forChoice ? i + " -> " + gcv.getCardDescription() : gcv.getCardDescription());
            i++;
        }
        if (forChoice) {
            out.println(i + " -> Pick a card from RESOURCE CARD DECK, first card color: " + gameView.getCommon().getResourceDeck());
            out.println((i + 1) + " -> Pick a card from GOLD CARD DECK, first card color: " + gameView.getCommon().getGoldDeck());
        }
        else {
            out.println("RESOURCE CARD DECK, first card color: " + gameView.getCommon().getResourceDeck());
            out.println("GOLD CARD DECK, first card color: " + gameView.getCommon().getGoldDeck());
            for(GameCardView gcv : gameView.getCommon().getGoals()) {
                out.println(gcv.getCardDescription());
            }
        }
    }

    @Override
    protected void show_visibleSymbols(GameView gameView) {
        for(String s : gameView.getPlayerView().getVisibleSymbols().keySet()) {
            out.println(s + " -> " + gameView.getPlayerView().getVisibleSymbols().get(s));
        }
    }

    @Override
    protected void show_menu() {
        out.println("Play a card -> play <card> <\"front\"/\"back\"> <x> <y>");
        out.println("Show table -> show");
        out.println("Show own visible symbols -> visible");
        out.println("Draw a card (after play a card) -> draw <option>");
        out.println("Help -> help");
    }

    @Override
    protected void show_lobby(){
        out.println("1 - create a new lobby");
        out.println("2 - join an existing lobby");
        out.print("Do you want to: ");
    }

    @Override
    protected void show_available_colors(ArrayList<String> colors) {
        out.print("Available colors: ");
        for(String s: colors) out.print(s + " ");
    }

    @Override
    protected void show_hidden_goal(GameCardView[] views) {
        for(int i = 0; i < views.length; i++) {
            out.println("\n" + i + " - " + views[i].getCardDescription());
        }
        out.print("Choose your secret goal: ");

    }
    @Override
    protected void show_inital_side(GameCardView[] views) {
        for(int i = 0; i < views.length; i++) {
            out.println("\n" + i + " - " + views[i].getCardDescription() + "\n");
        }
        out.print("Choose your initial card side: ");
    }

    protected void show_current_player(String nickname)
    {
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
    protected void show_winner_and_rank(boolean winner, HashMap<String, Integer> rank) {
        out.println(winner ? "you Win" : "Game Over");
        for(String player : rank.keySet()){
            out.println(rank.get(player)+ " : " +player);
        }
    }

    @Override
    protected void show_wrong_card_play() {
        out.println("invalid card placement");
    }

    @Override
    protected void show_joined_players(ArrayList<String> players) {
        players.forEach((p) -> out.println(p));
    }

    @Override
    protected void show_message() {
        for (String msg : message){
            out.println(msg);
        }
    }

    @Override
    protected void add_message(String sender, String text, String time) {
        String msg = "["+time+"] "+sender+" : "+text;
        message.add(msg);
    }

    @Override
    protected void show_logo() {
        clearScreen();
        new PrintStream(System.out, true, System.console() != null
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
                """);
        out.println(Costants.AUTORI);
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
        out.println(Costants.REGOLE);
    }
}
