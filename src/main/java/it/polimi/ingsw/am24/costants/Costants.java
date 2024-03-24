package it.polimi.ingsw.am24.costants;

import it.polimi.ingsw.am24.model.Kingdom;
import it.polimi.ingsw.am24.model.Symbol;

import java.util.HashMap;

public class Costants {
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
    public static final String CODEXNATURALIS =
            "███████  ██████   ████     ███████ ███    ███\n"
                    +"██       █    █   █  ██    █         ██  ██\n"
                    +"██       █    █   █   ██   ████        ██\n"
                    +"██       █    █   █  ██    █         ██  ██\n"
                    +"███████  ██████   ████     ███████ ███    ███\n"
                    +"\n"
                    +"██   █  ████  ████████ ██   ██  █████    ████  ██     ██ ███████\n"
                    +"█ █  █ ██  ██    ██    ██   ██  █   ██  ██  ██ ██     ██ ██\n"
                    +"█  █ █ ██████    ██    ██   ██  ████    ██████ ██     ██ ███████\n"
                    +"█   ██ ██  ██    ██    ██   ██  █  ██   ██  ██ ██     ██      ██\n"
                    +"█    █ ██  ██    ██     █████   █   ██  ██  ██ ██████ ██ ███████\n";
    public static final String AUTORI = "Benedetti Gabriele, Buccheri Giuseppe, Belfiore Mattia, Canepari Michele";
    public static final String REGOLE = "Per il regolamento completo andare su: \n https://www.craniocreations.it/prodotto/codex-naturalis";
    public static int port;
    public static String ipAddress;
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
        Costants.port = port;
    }
    public static void setIpAddress(String ip) {
        Costants.ipAddress = ip;
    }
}
