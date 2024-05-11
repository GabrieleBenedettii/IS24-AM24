package it.polimi.ingsw.am24.view;

import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.PublicBoardView;
import it.polimi.ingsw.am24.network.rmi.RMIClient;
import it.polimi.ingsw.am24.network.socket.SocketClient;
import it.polimi.ingsw.am24.view.commandLine.CLI;
import it.polimi.ingsw.am24.view.flow.CommonClientActions;
import it.polimi.ingsw.am24.view.flow.Flow;
import it.polimi.ingsw.am24.view.flow.UI;
import it.polimi.ingsw.am24.view.flow.utility.Event;
import it.polimi.ingsw.am24.view.flow.utility.EventType;
import it.polimi.ingsw.am24.view.flow.utility.GameStatus;
import it.polimi.ingsw.am24.view.graphicalUser.GUI;
import it.polimi.ingsw.am24.view.graphicalUser.GUIapp;
import it.polimi.ingsw.am24.view.input.InputParser;
import it.polimi.ingsw.am24.view.input.InputReader;
import it.polimi.ingsw.am24.view.input.InputReaderCLI;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.regex.Pattern;

public class GameFlow extends Flow implements Runnable, CommonClientActions {
    private String nickname;

    private CommonClientActions actions;

    private GameStatus status = GameStatus.BEGIN;

    private final Queue<Event> events = new LinkedList<>();

    private final UI ui;

    protected InputParser inputParser;
    protected InputReader inputReader;

    private boolean joined;
    private ArrayList<String> availableColors;
    private ArrayList<GameCardView> cards;
    private GameView gameView;

    public GameFlow(String connectionType) {
        switch (connectionType) {
            case "RMI" -> actions = new RMIClient(this);
            case "SOCKET" -> actions = new SocketClient(this);
        }

        ui = new CLI();

        nickname = "";
        joined = false;
        this.inputReader = new InputReaderCLI();
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }
    public GameFlow(GUIapp application, String connectionType) {
        switch (connectionType) {
            case "RMI" -> actions = new RMIClient(this);
            case "SOCKET" -> actions = new SocketClient(this);
        }
        this.inputReader = new InputReaderGUI();

        ui = new GUI(application, (InputReaderGUI) inputReader);

        nickname = "";
        joined = false;
        this.inputReader = new InputReaderGUI();
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        Event event;
        addEvent(EventType.APP_MENU);
        ui.show_logo();
        while (!Thread.interrupted()) {
            if (joined) {
                //Get one event
                event = events.poll();
                if (event != null) {
                    //if something happened
                    switch (status) {
                        case BEGIN -> {
                            try {
                                statusWait(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case RUNNING -> {
                            try {
                                statusRunning(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ENDED -> {
                            try {
                                statusEnded(event);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            } else {
                event = events.poll();
                if (event != null) {
                    statusNotInAGame(event);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void statusNotInAGame(Event event) {
        switch (event.getType()) {
            case APP_MENU -> {
                boolean selectionok;
                do {
                    selectionok = askSelectGame();
                } while (!selectionok);
            }
            case NICKNAME_ALREADY_USED -> {
                nickname = null;
                events.add(new Event(EventType.APP_MENU));
                //todo create ui message
                System.out.println("Nickname already used");
            }
            case NO_LOBBY_AVAILABLE -> {
                nickname = null;
                events.add(new Event(EventType.APP_MENU));
                //todo create ui message
                System.out.println("No lobby is available, please create a new one");
            }
        }
    }

    private void statusWait(Event event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case AVAILABLE_COLORS -> askColor();
            case HIDDEN_GOAL_CHOICE -> askSecretGoalDealt();
            case INITIAL_CARD_SIDE -> askInitialCardSide();
        }

    }

    private void statusRunning(Event event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case BEGIN_PLAY -> {
                this.inputParser.setNickname(nickname);
                this.inputParser.setIdGame(gameView.getGameId());

                if(gameView.getCurrent().equals(nickname)) {
                    ui.show_gameView(gameView);
                    askCardPlay();
                }
                else
                    ui.show_current_player(gameView.getCurrent());
            }
            case BEGIN_DRAW -> {
                if(gameView.getCurrent().equals(nickname)) {
                    ui.show_table(gameView, true);
                    askCardDraw();
                }
                else
                    ui.show_current_player(gameView.getCurrent());
            }
            /*case SENT_MESSAGE -> {
                //todo fix
                ui.show_sent_message(nickname);
            }*/
        }
    }

    private void statusEnded(Event event) throws InterruptedException {
        switch (event.getType()) {
            case GAME_ENDED -> {
                //new Scanner(System.in).nextLine();
                this.inputParser.getDataToProcess().pop();
                try {
                    this.inputParser.getDataToProcess().pop();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                /*this.leave(nickname, event.getModel().getGameId());
                this.youLeft();*/
            }
        }
    }

    private void askNickname() {
        String invalidChars = "~!@#$%^&*()-_=+[]{}|;:',.<>?";
        boolean validNickname = false;
        do {
            ui.show_insert_nickname();
            try {
                nickname = this.inputParser.getDataToProcess().pop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // check for special characters
            if (nickname.matches(".*[" + Pattern.quote(invalidChars) + "].*")) {
                System.out.println("Invalid nickname. Please enter a nickname without special characters or numbers.");
            } else {
                validNickname = true;
            }
        } while (!validNickname);
        //ui.show_chosenNickname(nickname);
    }

    private boolean askSelectGame() {
        String choice;
        ui.show_lobby();
        try {
            choice = this.inputParser.getDataToProcess().pop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        askNickname();

        switch (choice) {
            case "1" -> {
                Integer numPlayers = askNumPlayers();
                createGame(nickname, numPlayers);
            }
            case "2" -> joinFirstGameAvailable(nickname);
            /*case "js" -> {
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    joinGame(nickname, gameId);
            }*/
            default -> {
                return false;
            }
        }

        return true;
    }

    private void askColor() {
        String color;
        ui.show_available_colors(availableColors);
        try {
            color = this.inputParser.getDataToProcess().pop();
            if (!availableColors.contains(color) && !availableColors.contains(color.toUpperCase())) {
                System.out.println("Color not available. Please choose a different color.");
                askColor();
                return;
            }
            chooseColor(nickname, color);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer askNumPlayers() {
        String temp;
        Integer numPlayers = null;
        do {
            //ui.show_inputGameIdMsg();
            System.out.print("\nInsert number of players -> ");
            try {
                try {
                    temp = this.inputParser.getDataToProcess().pop();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                numPlayers = Integer.parseInt(temp);
                if (numPlayers < 2 || numPlayers > 4) {
                    System.out.println("Invalid number of players. Please enter a number between 2 and 4.");
                    numPlayers = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 2 and 4.");
                //ui.show_NaNMsg();
            }
        } while (numPlayers == null);
        return numPlayers;
    }

    private void askSecretGoalDealt() {
        //ui.show_hidden_goal(cards);
        boolean retry;
        do {
            ui.show_start_table(gameView);
            ui.show_hidden_goal(cards);
            try {
                int choice = Integer.parseInt(this.inputParser.getDataToProcess().pop());
                actions.chooseHiddenGoal(nickname, cards.get(choice).getCardId());
                retry = false;
            } catch (Exception e) {
                System.out.println("Nan");
                retry = true;
            }
        } while(retry);
    }

    private void askInitialCardSide() {
        ui.show_initial_side(cards);
        boolean retry = true;
        do {
            try {
                int choice = Integer.parseInt(this.inputParser.getDataToProcess().pop());
                if (choice < 0 || choice >= cards.size()) {
                    System.out.println("Invalid choice. Please select a valid card side.");
                    continue; // re-enter the loop
                }
                actions.chooseInitialCardSide(nickname, choice);
                retry = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        } while (retry);
    }

    private void askCardPlay() throws IOException {
        String command = "";
        //ui.show_player_view();
        do {
            try {
                System.out.print("\nCommand -> ");
                command = this.inputParser.getDataToProcess().pop();
                String[] command_args = command.split(" ");

                switch (command_args[0]) {
                    case "/play" -> {
                        if(command_args.length != 5) {
                            System.out.println("Invalid command. Please enter in the format: play <cardIndex> <front/back> <x> <y>");
                            continue;
                        }
                        int cardIndex, x, y;
                        boolean front;
                        try {
                            cardIndex = Integer.parseInt(command_args[1]);
                            x = Integer.parseInt(command_args[3]);
                            y = Integer.parseInt(command_args[4]);
                            front = command_args[2].equalsIgnoreCase("front");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter valid integers for card index, x, and y coordinates.");
                            continue;
                        }
                        if (cardIndex < 0 || cardIndex >= gameView.getPlayerView().getPlayerHand().size()) {
                            System.out.println("Invalid card index. Please enter a valid index.");
                            continue;
                        }
                        if (x < 0 || x >= gameView.getPlayerView().getBoard().length || y < 0 || y >= gameView.getPlayerView().getBoard()[0].length) {
                            System.out.println("Invalid coordinates. Please enter valid coordinates within the board.");
                            continue;
                        }
                        if (!gameView.getPlayerView().getPossiblePlacements()[x][y]) {
                            System.out.println("Invalid positioning. You cannot place a card in this position.");
                            continue;
                        }
                        playCard(nickname, cardIndex, front, x, y);
                    }
                    case "/table" -> {
                        ui.show_start_table(gameView);
                        continue;
                    }
                    /*case "/visible" -> {
                        ui.show_visibleSymbols(gameView);
                        continue;
                    }*/
                    case "/help" -> {
                        ui.show_menu();
                        continue;
                    }
                    default -> {
                        System.out.println("Invalid command. Type \"/help\" for help");
                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                continue;
            }
            break;
        } while (true);
//        do {
//            try {
//                command = this.inputParser.getDataToProcess().pop();
//            } catch (Exception e) {
//                System.out.println("Wrong command");
//                continue;
//            }
//            String[] command_args = command.split(" ");
//
//            if(command_args[0].equals("play")) {
//                if (!command_args[2].equals("front") && !command_args[2].equals("back")) continue;
//                boolean front = command_args[2].equals("front");
//
//                int x;
//                int y;
//                int cardIndex;
//                try {
//                    cardIndex = Integer.parseInt(command_args[1]);
//                    x = Integer.parseInt(command_args[3]);
//                    y = Integer.parseInt(command_args[4]);
//                } catch (Exception e) {
//                    System.out.println("Nan");
//                    continue;
//                }
//
//                if (!gameView.getPlayerView().getPossiblePlacements()[x][y])
//                    continue;
//
//                playCard(nickname,cardIndex,front,x,y);
//            }
//        } while(false);
    }

    private void askCardDraw() {
        String command = "";
        //ui.show_public_board_view();
        do {
            try {
                System.out.print("\nCommand -> ");
                command = this.inputParser.getDataToProcess().pop();
                String[] command_args = command.split(" ");
                if (!command_args[0].equalsIgnoreCase("/draw") || command_args.length < 2) {
                    System.out.println("Invalid command. Please enter in the format: draw <cardIndex>");
                    continue;
                }
                int cardIndex = Integer.parseInt(command_args[1]);
                if (cardIndex < 0 || cardIndex >= 6) {
                    System.out.println("Invalid card index. Please enter a valid index.");
                    continue;
                }
                drawCard(nickname, cardIndex);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid card index.");
                continue;
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                continue;
            }
            break;
        } while (true);
    }

    //METHODS THAT THE CLIENT CAN REQUEST TO THE SERVER

    @Override
    public void createGame(String nickname, int numPlayers) {
        //ui.show_creatingNewGameMsg(nickname);
        try {
            actions.createGame(nickname, numPlayers);
        } catch (IOException | NotBoundException e) {
            System.out.println("Error during creating game");
        }
    }

    @Override
    public void joinFirstGameAvailable(String nickname) {
        //ui.show_joiningFirstAvailableMsg(nickname);
        try {
            actions.joinFirstGameAvailable(nickname);
        } catch (IOException | NotBoundException e) {
            System.out.println("Error during joining game");
        }
    }

    @Override
    public void chooseColor(String nickname, String color) throws IOException {
        actions.chooseColor(nickname, color);
    }

    @Override
    public void chooseHiddenGoal(String nickname, int choice) throws IOException {
        actions.chooseHiddenGoal(nickname, choice);
    }

    @Override
    public void chooseInitialCardSide(String nickname, int choice) throws IOException {
        actions.chooseInitialCardSide(nickname, choice);
    }

    @Override
    public void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException {
        actions.playCard(nickname, cardIndex, front, x, y);
    }

    @Override
    public void drawCard(String nickname, int choice) throws IOException {
        actions.drawCard(nickname, choice);
    }

    @Override
    public void sendPublicMessage(String sender, String message) throws RemoteException {
        actions.sendPublicMessage(sender, message);
    }

    @Override
    public void sendPrivateMessage(String sender, String receiver, String message) throws RemoteException {
        actions.sendPrivateMessage(sender, receiver, message);
    }

    //EVENTS RECEIVED FROM THE SERVER

    @Override
    public void invalidNumPlayers() {
        addEvent(EventType.APP_MENU);
    }

    @Override
    public void playerJoined(ArrayList<String> players) {
        ui.show_joined_players(players);
    }

    @Override
    public void noLobbyAvailable() {
        events.add(new Event(EventType.NO_LOBBY_AVAILABLE));
    }

    @Override
    public void nicknameAlreadyUsed() {
        events.add(new Event(EventType.NICKNAME_ALREADY_USED));
    }

    @Override
    public void availableColors(ArrayList<String> colors) {
        this.availableColors = colors;
        this.status = GameStatus.BEGIN;
        addEvent(EventType.AVAILABLE_COLORS);
    }

    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, PublicBoardView publicBoardView) {
        this.gameView = new GameView(null, 0, null, publicBoardView, null);
        this.cards = cardViews;
        addEvent(EventType.HIDDEN_GOAL_CHOICE);
    }

    @Override
    public void initialCardSide(GameCardView front, GameCardView back) {
        addEvent(EventType.INITIAL_CARD_SIDE);
        this.cards = new ArrayList<>();
        cards.add(front);
        cards.add(back);
    }

    @Override
    public void beginTurn(GameView gameView) {
        this.status = GameStatus.RUNNING;
        addEvent(EventType.BEGIN_PLAY);
        this.gameView = gameView;
    }

    @Override
    public void invalidPositioning() {
        System.out.println("Invalid positioning!!!");
        addEvent(EventType.BEGIN_PLAY);
    }

    @Override
    public void requirementsNotMet() {
        System.out.println("You can't place this card!!!");
        addEvent(EventType.BEGIN_PLAY);
    }

    @Override
    public void beginDraw() {
        this.status = GameStatus.RUNNING;
        addEvent(EventType.BEGIN_DRAW);
    }

    @Override
    public void wrongCardPlay(GameView gameView) {
        addEvent(EventType.BEGIN_PLAY);
        this.gameView = gameView;
        ui.show_wrong_card_play();
    }

    @Override
    public void sentMessage(String message) {
        //Show the message only if is for everyone or is for me (or I sent it)
        //ui.add_message(message);
        //addEvent(EventType.SENT_MESSAGE);
        System.out.println("\nNew message: " + message);
    }

    @Override
    public void gameEnded(String winner, HashMap<String,Integer> rank) {
        this.status = GameStatus.ENDED;
        addEvent(EventType.GAME_ENDED);
        ui.show_winner_and_rank(winner.equals(nickname), rank);
        //resetGameId(fileDisconnection, gameModel);
    }

    private void addEvent(EventType type) {
        events.add(new Event(type));
        if (type.equals(EventType.AVAILABLE_COLORS) || type.equals(EventType.PLAYER_JOINED) || (status != null && status.equals(GameStatus.RUNNING)))
            joined = true;

        if(type.equals(EventType.APP_MENU))
            joined = false;
    }
}
