package it.polimi.ingsw.am24.main;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.view.GameFlow;
import it.polimi.ingsw.am24.view.commandLine.CLI;
import it.polimi.ingsw.am24.view.flow.UI;
import it.polimi.ingsw.am24.view.graphicalUser.GUIapp;
import javafx.application.Application;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        int choice;
        String protocol;
        //Disable javaFX logger
        killLoggers();

        String ip;

        UI ui =new CLI();

        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Insert remote IP (leave empty for localhost) -> ");
            ip = in.nextLine();
            if(!ip.equals("") && !checkIP(ip)){
                Constants.clearScreen();
                System.out.println("Not valid");
            }
        } while (!ip.equals("") && !checkIP(ip));

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

        if(choice == 1 || choice == 2) new GameFlow(protocol, ip);
        else Application.launch(GUIapp.class, protocol, ip);
    }

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

    private static void killLoggers(){
        com.sun.javafx.util.Logging.getJavaFXLogger().disableLogging();
        com.sun.javafx.util.Logging.getCSSLogger().disableLogging();
        com.sun.javafx.util.Logging.getAccessibilityLogger().disableLogging();
        com.sun.javafx.util.Logging.getFocusLogger().disableLogging();
        com.sun.javafx.util.Logging.getInputLogger().disableLogging();
        com.sun.javafx.util.Logging.getLayoutLogger().disableLogging();
    }
}

