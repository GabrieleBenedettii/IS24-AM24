package it.polimi.ingsw.am24.main;

import it.polimi.ingsw.am24.network.rmi.RMIServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {

        String input;
        Scanner in = new Scanner(System.in);

        /*do {
            clearCMD();
            System.out.println("Insert remote IP (leave empty for localhost) -> ");
            input = in.nextLine();
        } while (!input.equals("") && !isValidIP(input));

        if (input.equals(""))
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        else{
            *//*DefaultValue.serverIp = input;
            System.setProperty("java.rmi.server.hostname", input);*//*
        }*/

        RMIServer.bind();

        /*Server serverSOCKET = new Server();
        serverSOCKET.start(DefaultValue.Default_port_Socket);*/
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


    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\033\143");   //for Mac
        }
    }

}

