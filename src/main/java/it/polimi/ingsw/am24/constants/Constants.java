package it.polimi.ingsw.am24.constants;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

public class Constants {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final String TEXT_UNDERLINE = "\033[4m";
    public static final String TEXT_RESET = "\033[0m";
    public static final String TEXT_RED = "\033[31m";
    public static final String TEXT_GREEN = "\033[32m";
    public static final String TEXT_BLUE = "\033[34m";
    public static final String TEXT_YELLOW = "\033[33m";
    public static final String TEXT_PURPLE = "\033[35m";
    public static final String TEXT_CYAN = "\033[36m";
    public static final String TEXT_WHITE = "\033[37m";

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
    public static final String AUTHORS = "Belfiore Mattia, Benedetti Gabriele, Buccheri Giuseppe, Canepari Michele";
    public static final String RULES = "For the complete rules go to: \nhttps://www.craniocreations.it/prodotto/codex-naturalis";
    public static int port;
    public static String ipAddress;
    private static final HashMap<Character, String> firstLetterMap = new HashMap<Character, String>();
    public final static Object gameIdTime = "Created";
    public final static Object gameIdData = "GameId";
    public final static int offSet = 40500;

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
    public static String getText(Character c){ return firstLetterMap.get(c);}
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

    private static final HashMap<Kingdom, String> kingdomColorMap = new HashMap<Kingdom, String>();

    static {
        kingdomColorMap.put(Kingdom.FUNGI, TEXT_FUNGI);
        kingdomColorMap.put(Kingdom.ANIMAL, TEXT_ANIMAL);
        kingdomColorMap.put(Kingdom.PLANT, TEXT_PLANT);
        kingdomColorMap.put(Kingdom.INSECT, TEXT_INSECT);
    }

    public static String getText(Kingdom k) {
        return kingdomColorMap.get(k);
    }


    public static void setPort(int port) {
        Constants.port = port;
    }
    public static void setIpAddress(String ip) {
        Constants.ipAddress = ip;
    }
    public static  final HashMap<Integer, String> corner =new HashMap<>();

    static {
        corner.put(0,"top-left");
        corner.put(1,"top-right");
        corner.put(2,"bottom-left");
        corner.put(3,"bottom-right");
    }
}
