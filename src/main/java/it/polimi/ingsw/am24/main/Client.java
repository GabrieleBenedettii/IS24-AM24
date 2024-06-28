package it.polimi.ingsw.am24.main;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.view.Flow;
import it.polimi.ingsw.am24.view.TUI.TUI;
import it.polimi.ingsw.am24.view.UI;
import it.polimi.ingsw.am24.view.GUI.GUIApplication;
import javafx.application.Application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The Client class serves as the entry point for the game client application.
 * It handles user input for IP address and network type, configures the game settings,
 * and initiates the game flow based on the selected protocol.
 */
public class Client {

    /**
     * The main method is the entry point of the application. It handles user inputs,
     * configures the server IP address and network protocol, and starts the game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        int choice;
        String protocol;
        //Disable javaFX logger
        killLoggers();

        String ip;

        UI ui =new TUI();

        Scanner in = new Scanner(System.in);
        clearScreen();
        do {
            System.out.print("Insert remote IP (leave empty for localhost) -> ");
            ip = in.nextLine();
            if(!ip.equals("") && !checkIP(ip)){
                clearScreen();
                System.out.println("Not valid");
            }
        } while (!ip.equals("") && !checkIP(ip));

        if(!ip.isEmpty()) Constants.SERVERIP = ip;

        do {
            ui.show_network_type();
            try {
                choice = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
                System.out.println("Nan");
            }
        } while (choice < 1 || choice > 4);

        if(choice == 1 || choice ==3) protocol = "RMI";
        else protocol = "SOCKET";

        System.out.println("Starting the game!");

        if(choice == 1 || choice == 2) new Flow(protocol);
        else Application.launch(GUIApplication.class, protocol);
    }

    /**
     * Validates the format of the given IP address.
     *
     * @param input The IP address to be checked.
     * @return true if the IP address is valid, false otherwise.
     */
    private static boolean checkIP(String input) {
        List<String> input_split;
        input_split = Arrays.stream(input.split("\\.")).toList();
        if (input_split.size() != 4) {
            return false;
        }
        for (String num : input_split) {
            try {
                int dec = Integer.parseInt(num);
                if(dec < 0 || dec > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Disables logging for various JavaFX loggers to reduce console output.
     */
    private static void killLoggers(){
        com.sun.javafx.util.Logging.getJavaFXLogger().disableLogging();
        com.sun.javafx.util.Logging.getCSSLogger().disableLogging();
        com.sun.javafx.util.Logging.getAccessibilityLogger().disableLogging();
        com.sun.javafx.util.Logging.getFocusLogger().disableLogging();
        com.sun.javafx.util.Logging.getInputLogger().disableLogging();
        com.sun.javafx.util.Logging.getLayoutLogger().disableLogging();
    }

    /**
     * Clears the console screen.
     * This method attempts to clear the screen using a command specific to the operating system.
     * For Windows, it uses 'cls', and for Mac, it uses an ANSI escape code.
     */
    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\033\143");   //for Mac
        }
    }
}

