package it.polimi.ingsw.am24.constants;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.awt.*;
import java.util.HashMap;

public class Constants {
    //server configuration
    public static String SERVERIP = "127.0.0.1";
    public static final int RMIPort = 1234;
    public static final int SOCKETPort = 8888;
    public static final String SERVERNAME = "CodexNaturalis-Server";
    public static final int SECONDS_BETWEEN_ATTEMPTS = 2;

    //game configuration
    public static final int MAX_PLAYERS = 4;
    public static final int MATRIX_DIMENSION = 100;
    public static final String AUTHORS = "Belfiore Mattia, Benedetti Gabriele, Buccheri Giuseppe, Canepari Michele";
    public static final String RULES = "For the complete rules go to: \nhttps://www.craniocreations.it/prodotto/codex-naturalis";

    //colors
    public static final String TEXT_RESET = "\033[0m";
    public static final String TEXT_RED = "\033[31m";
    public static final String TEXT_GREEN = "\033[32m";
    public static final String TEXT_BLUE = "\033[34m";
    public static final String TEXT_YELLOW = "\033[33m";
    public static final String TEXT_PURPLE = "\033[35m";
    public static final String TEXT_CYAN = "\033[36m";
    public static final String TEXT_WHITE = "\033[37m";

    //colored letters
    public static final String TEXT_F = TEXT_RED + "F" + TEXT_RESET;
    public static final String TEXT_A = TEXT_CYAN + "A" + TEXT_RESET;
    public static final String TEXT_I = TEXT_PURPLE + "I" + TEXT_RESET;
    public static final String TEXT_P = TEXT_GREEN + "P" + TEXT_RESET;
    public static final String TEXT_K = TEXT_YELLOW + "K" + TEXT_RESET;
    public static final String TEXT_M = TEXT_YELLOW + "M" + TEXT_RESET;
    public static final String TEXT_Q = TEXT_YELLOW + "Q" + TEXT_RESET;
    public static final String TEXT_E = TEXT_WHITE + "E" + TEXT_RESET;
    public static final String TEXT_H = TEXT_WHITE + "H" + TEXT_RESET;
    public static final String TEXT_S = TEXT_WHITE + " " + TEXT_RESET;

    //colored kingdoms
    public static final String TEXT_FUNGI = TEXT_RED + "FUNGI" + TEXT_RESET;
    public static final String TEXT_ANIMAL = TEXT_BLUE + "ANIMAL" + TEXT_RESET;
    public static final String TEXT_PLANT = TEXT_GREEN + "PLANT" + TEXT_RESET;
    public static final String TEXT_INSECT = TEXT_PURPLE + "INSECT" + TEXT_RESET;
    public static final String TEXT_INK = TEXT_YELLOW + "INK" + TEXT_RESET;
    public static final String TEXT_MANUSCRIPT = TEXT_YELLOW + "MANUSCRIPT" + TEXT_RESET;
    public static final String TEXT_QUILL = TEXT_YELLOW + "QUILL" + TEXT_RESET;
    public static final String BACKGROUND_RESET = "\033[0m";
    public static final String BACKGROUND_BLACK = "\033[40m";
    public static final String HIDDEN = "HIDDEN";
    public static final String EMPTY = "EMPTY";

    //get the colored letter from a letter
    private static final HashMap<Character, String> firstLetterMap = new HashMap<>();
    static {
        firstLetterMap.put('F', TEXT_F);
        firstLetterMap.put('A', TEXT_A);
        firstLetterMap.put('I', TEXT_I);
        firstLetterMap.put('P', TEXT_P);
        firstLetterMap.put('K', TEXT_K);
        firstLetterMap.put('M', TEXT_M);
        firstLetterMap.put('Q', TEXT_Q);
        firstLetterMap.put('E', TEXT_E);
        firstLetterMap.put('H', TEXT_H);
        firstLetterMap.put(' ', TEXT_S);
    }
    public static String getText(Character c) {
        return firstLetterMap.get(c);
    }

    //get the colored symbol from symbol
    private static final HashMap<Symbol, String> symbolColorMap = new HashMap<>();
    static {
        symbolColorMap.put(Symbol.FUNGI, TEXT_FUNGI);
        symbolColorMap.put(Symbol.ANIMAL, TEXT_ANIMAL);
        symbolColorMap.put(Symbol.PLANT, TEXT_PLANT);
        symbolColorMap.put(Symbol.INSECT, TEXT_INSECT);
        symbolColorMap.put(Symbol.INK, TEXT_INK);
        symbolColorMap.put(Symbol.MANUSCRIPT, TEXT_MANUSCRIPT);
        symbolColorMap.put(Symbol.QUILL, TEXT_QUILL);
    }
    public static String getText(Symbol s) {
        return symbolColorMap.get(s);
    }

    //get the colored kingdom from kingdom
    private static final HashMap<Kingdom, String> kingdomColorMap = new HashMap<>();
    static {
        kingdomColorMap.put(Kingdom.FUNGI, TEXT_FUNGI);
        kingdomColorMap.put(Kingdom.ANIMAL, TEXT_ANIMAL);
        kingdomColorMap.put(Kingdom.PLANT, TEXT_PLANT);
        kingdomColorMap.put(Kingdom.INSECT, TEXT_INSECT);
    }
    public static String getText(Kingdom k) {
        return kingdomColorMap.get(k);
    }

    //scoreboard scores coordinates
    public static final HashMap<Integer, Point> scoreboard = new HashMap<>();
    static {
        scoreboard.put(0,new Point(192,1349));
        scoreboard.put(1,new Point(361,1349));
        scoreboard.put(2,new Point(535,1349));
        scoreboard.put(3,new Point(623,1193));
        scoreboard.put(4,new Point(449,1193));
        scoreboard.put(5,new Point(280,1193));
        scoreboard.put(6,new Point(106,1193));
        scoreboard.put(7,new Point(106,1038));
        scoreboard.put(8,new Point(280,1038));
        scoreboard.put(9,new Point(449,1038));
        scoreboard.put(10,new Point(623,1038));
        scoreboard.put(11,new Point(623,882));
        scoreboard.put(12,new Point(449,882));
        scoreboard.put(13,new Point(280,882));
        scoreboard.put(14,new Point(106,882));
        scoreboard.put(15,new Point(106,727));
        scoreboard.put(16,new Point(280,727));
        scoreboard.put(17,new Point(449,727));
        scoreboard.put(18,new Point(623,727));
        scoreboard.put(19,new Point(623,572));
        scoreboard.put(20,new Point(363,491));
        scoreboard.put(21,new Point(106,572));
        scoreboard.put(22,new Point(106,413));
        scoreboard.put(23,new Point(106,254));
        scoreboard.put(24,new Point(206,128));
        scoreboard.put(25,new Point(363,101));
        scoreboard.put(26,new Point(520,128));
        scoreboard.put(27,new Point(623,254));
        scoreboard.put(28,new Point(623,413));
        scoreboard.put(29,new Point(363,289));
    }

    //corners
    public static  final HashMap<Integer, String> corner =new HashMap<>();
    static {
        corner.put(0,"top-left");
        corner.put(1,"top-right");
        corner.put(2,"bottom-left");
        corner.put(3,"bottom-right");
    }

    //colors
    public static  final HashMap<String, String> colors =new HashMap<>();
    static {
        colors.put("blue","#025FA8");
        colors.put("red","AF0F2E");
        colors.put("yellow","c8aa2b");
        colors.put("green","#008026");
    }
}
