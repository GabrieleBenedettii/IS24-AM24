package it.polimi.ingsw.am24.main;

import it.polimi.ingsw.am24.view.GameFlow;
import it.polimi.ingsw.am24.view.commandLine.CLI;
import it.polimi.ingsw.am24.view.flow.UI;
import javafx.application.Application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        int selection;

        //Disable javaFX logger
        killLoggers();

        String input;

        UI ui =new CLI();

        do {
            System.out.print("Insert remote IP (leave empty for localhost) -> ");
            input = new Scanner(System.in).nextLine();
            if(!input.equals("") && !isValidIP(input)){
                clearCMD();
                System.out.println("Not valid");
            }
        } while (!input.equals("") && !isValidIP(input));

        /*if (!input.equals(""))
            DefaultValue.serverIp = input;*/

        /*do {
            System.out.print("Insert your IP (leave empty for localhost) -> ");
            input = new Scanner(System.in).nextLine();
            if(!input.equals("") && !isValidIP(input)){
                clearCMD();
                System.out.println("Not valid");
            }
        } while (!input.equals("") && !isValidIP(input));

        if (!input.equals(""))
            System.setProperty("java.rmi.server.hostname", input);*/

        do {
            ui.show_network_type();
            input = new Scanner(System.in).nextLine();
            try {
                selection = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                selection = -1;
                System.out.println("Nan");
            }
        } while (selection != 1 && selection != 2);

        /*do {
            System.out.println("""
                Select option:
                \t (1) CLI
                \t (2) GUI (not done yet)
                """);
            input = new Scanner(System.in).nextLine();
            try {
                selection = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                selection = -1;
                System.out.println("Nan");
            }
        } while (selection != 1 && selection != 2);

        String conSel = selection == 1 ? "CLI" : "GUI";*/

        System.out.println("Starting the game!");

        new GameFlow(selection == 1 ? "RMI" : "SOCKET");
        //Starts the UI
        //selection == 1 ? new GameFlow(conSel) : Application.launch(GUIApplication.class, conSel.toString());
    }

    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\033\143");   //for Mac
        }
    }

    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                int dec = Integer.parseInt(part);
                if(dec < 0 || dec > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private static void killLoggers(){
        com.sun.javafx.util.Logging.getJavaFXLogger().disableLogging();
        com.sun.javafx.util.Logging.getCSSLogger().disableLogging();
        com.sun.javafx.util.Logging.getAccessibilityLogger().disableLogging();
        com.sun.javafx.util.Logging.getFocusLogger().disableLogging();
        com.sun.javafx.util.Logging.getInputLogger().disableLogging();
        com.sun.javafx.util.Logging.getLayoutLogger().disableLogging();
    }
}

