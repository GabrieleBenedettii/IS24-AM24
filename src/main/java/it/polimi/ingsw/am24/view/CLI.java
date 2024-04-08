package it.polimi.ingsw.am24.view;

import it.polimi.ingsw.am24.messages.*;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.PlayerView;

import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class CLI extends UserInterface {
    private Scanner in;
    private PrintStream out;
    private String nickname;
    private boolean isGameEnded;

    private int numPlayers;
    private boolean isMyTurn;
    private Queue<Message> receivedMessages;
    private final Object lockLogin;
    private final Object lockQueue;
    private final Object lockGame;

    public CLI() {
        super();
        in = new Scanner(System.in);
        out = new PrintStream(System.out, true);
        lockLogin = new Object();
        lockQueue = new Object();
        lockGame = new Object();
        init();
    }

    private void init() {
        nickname = null;
        numPlayers = 0;
        isGameEnded = false;

        receivedMessages = new LinkedList<>();
    }

    @Override
    public void update(Message m) {
        addMessageToQueue(m);
        synchronized (lockLogin) {
            lockLogin.notifyAll();
        }
    }

    @Override
    public void run() {
        while(!setupConnection());

        while(true) {
            doLogin();

            //thread for reading messages arrived in the queue
            new Thread(){
                @Override
                public void run() {
                super.run();
                while(true) {
                    if (isMessagesQueueEmpty()) {
                        continue;
                    }
                    Message m = popMessageFromQueue();

                    if (m instanceof PlayersInLobbyMessage) {
                        ((PlayersInLobbyMessage) m).getPlayers().stream().forEach(p -> out.print(p + " "));
                    }
                    else if (m instanceof SecretObjectiveDealtMessage) {
                        //clearScreen();
                        chooseSecretGoal(((SecretObjectiveDealtMessage) m).getViews());
                    }
                    else if (m instanceof InitialCardDealtMessage) {
                        //clearScreen();
                        chooseSide(((InitialCardDealtMessage) m).getViews());
                    }
                    if(isGameEnded) break;
                }
                }
            }.start();

            while(true){
                synchronized (lockGame){
                    while(!isMyTurn && !isGameEnded) {
                        try {
                            lockGame.wait();
                        } catch (InterruptedException e) {
                            System.err.println("Interrupted while waiting for server: " + e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }
                }
                if(isGameEnded) break;
                //notifyListeners(doTurnAction());
            }
        }

    }

    private boolean setupConnection(){
        int choice;

        //todo add controls
        out.println("1 - RMI");
        out.println("2 - SOCKET (not done already)");
        out.print("Choose the connection type: ");
        choice = in.nextInt();
        in.nextLine();

        //todo check ip
        String ip = "127.0.0.1";
        /*out.print("Please provide the IP address or the URL of the server: ");
        ip = in.nextLine();*/

        //todo check port
        int port = 1234;
        /*out.print("Please provide the port address or the URL of the server: ");
        port = in.nextInt();
        in.nextLine();*/

        if(choice == 1){
            out.println("Connecting with RMI...");
            try{
                this.setUpRMIClient(ip,port);
            }catch (RemoteException | NotBoundException e){
                out.println("Cannot connect with RMI. Make sure the IP provided is valid and try again later...");
                return false;
            }
        }
        return true;
    }

    private void doLogin() {
        readNickname();

        while(true)
        {
            Message m = this.popMessageFromQueue();

            if(m instanceof NickNameNotTakenMessage){
                chooseLobby();
            }
            //todo taken nickname
            //todo no lobby available
            else if(m instanceof PlayersInLobbyMessage){
                out.print("Players: ");
                ((PlayersInLobbyMessage) m).getPlayers().stream().forEach(p -> out.print(p + " "));
                break;
            }
            else if(m instanceof AvailableColorsMessage){
                chooseColor(((AvailableColorsMessage) m).getAvailableColors());
            }
            else if (m instanceof SecretObjectiveDealtMessage) {
                //clearScreen();
                chooseSecretGoal(((SecretObjectiveDealtMessage) m).getViews());
                break;
            }
        }
    }

    private void readNickname() {
        do{
            out.print("\nInsert nickname <spaces are not allowed>: ");
            this.nickname = in.nextLine();
        }while(this.nickname.contains(" ") || this.nickname.equals(""));

        doReconnect(this.nickname);
        waitForResponse();
    }

    private void waitForResponse() {
        synchronized (lockLogin) {
            try {
                lockLogin.wait();
            } catch (InterruptedException e) {
                System.err.println("Interrupted while waiting for server: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private void chooseLobby(){
        out.println("1 - create a new lobby");
        out.println("2 - join an existing lobby");
        out.print("Do you want to: ");
        //todo control
        int choice = in.nextInt();
        in.nextLine();

        switch (choice){
            case 1:
                //todo add control
                out.print("Number of players: ");
                numPlayers = in.nextInt();
                in.nextLine();
                doConnect(nickname, numPlayers);
                break;
            case 2:
                doConnect(this.nickname, 1);
                break;
        }
        waitForResponse();
    }

    private void chooseColor(List<String> colors) {
        String color;
        out.print("Available colors: ");
        for(String s: colors) out.print(s + " ");
        out.print("\nYour color: ");

        color = in.nextLine();
        doChooseColor(nickname, color);
        waitForResponse();
    }

    private void chooseSecretGoal(GameCardView[] views) {
        for(int i = 0; i < views.length; i++) {
            out.println("\n" + i + " - " + views[i].getCardDescription());
        }
        out.print("Choose your secret goal: ");
        int choice = in.nextInt();
        in.nextLine();
        doChooseHiddenGoal(nickname,views[choice].getCardId());
        waitForResponse();
    }

    private void chooseSide(GameCardView[] views) {
        for(int i = 0; i < views.length; i++) {
            out.println("\n" + i + " - " + views[i].getCardDescription() + "\n");
        }
        out.print("Choose your initial card side: ");
        int choice = in.nextInt();
        in.nextLine();
        doChooseInitialCardSide(nickname,choice);
        waitForResponse();
    }

    private void addMessageToQueue(Message m){
        synchronized (lockQueue){
            this.receivedMessages.add(m);
        }
    }

    private Message popMessageFromQueue(){
        synchronized (lockQueue){
            return this.receivedMessages.poll();
        }
    }

    private boolean isMessagesQueueEmpty(){
        synchronized (lockQueue){
            return this.receivedMessages.isEmpty();
        }
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
}
