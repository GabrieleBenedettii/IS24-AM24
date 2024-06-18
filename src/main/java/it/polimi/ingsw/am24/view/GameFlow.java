package it.polimi.ingsw.am24.view;

import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
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
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class GameFlow extends Flow implements Runnable, CommonClientActions {
    private int gameId;
    private String nickname;
    private String current_choice = null;
    private boolean create = false;
    private int num;
    private CommonClientActions actions;
    private GameStatus status = GameStatus.NOT_STARTED;
    private final Queue<Event> events = new LinkedList<>();
    private final UI ui;
    protected InputParser inputParser;
    protected InputReader inputReader;
    private boolean joined;
    private ArrayList<String> availableColors;
    private ArrayList<GameCardView> cards;
    private GameView gameView;
    private boolean colorNotAvailable;
    private HashMap<String,Boolean> colorSelected = new HashMap<>();

    public GameFlow(String connectionType, String ip) {
        switch (connectionType) {
            case "RMI" -> actions = new RMIClient(this, ip);
            case "SOCKET" -> actions = new SocketClient(this, ip);
        }

        this.ui = new CLI();

        this.nickname = "";
        this.joined = false;
        this.colorNotAvailable = false;
        this.num = 0;
        this.inputReader = new InputReaderCLI();
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }
    public GameFlow(GUIapp application, String connectionType, String ip) {
        switch (connectionType) {
            case "RMI" -> actions = new RMIClient(this, ip);
            case "SOCKET" -> actions = new SocketClient(this, ip);
        }
        this.inputReader = new InputReaderGUI();

        this.ui = new GUI(application, (InputReaderGUI) inputReader);

        this.nickname = "";
        this.joined = false;
        this.colorNotAvailable = false;
        this.num = 0;
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        Event event;
        addEvent(EventType.APP_MENU);
        while (!Thread.interrupted()) {
            if (joined) {
                //Get one event
                event = events.poll();
                if (event != null) {
                    //if something happened
                    switch (status) {
                        case FIRST_PHASE -> {
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

    private Boolean nicknameAlreadyUsed = false;
    private void statusNotInAGame(Event event) {
        switch (event.getType()) {
            case APP_MENU -> {
                boolean selectionok;
                do {
                    selectionok = askSelectGame(nicknameAlreadyUsed, current_choice);
                } while (!selectionok);
            }
            case NICKNAME_ALREADY_USED -> {
                nickname = null;
                ui.show_nickname_already_used();
                nicknameAlreadyUsed = true;
                events.add(new Event(EventType.APP_MENU));
            }
            case NO_LOBBY_AVAILABLE -> {
                nickname = null;
                ui.show_no_lobby_available();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(new Event(EventType.APP_MENU));
            }
        }
    }

    private void statusWait(Event event) throws IOException, InterruptedException {
        switch (event.getType()) {
            case AVAILABLE_COLORS -> askColor();
            case NOT_AVAILABLE_COLORS -> {
                colorNotAvailable = true;
                askColor();
            }
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
                    ui.show_current_player(gameView, nickname);
            }
            case BEGIN_DRAW -> {
                if(gameView.getCurrent().equals(nickname)) {
                    ui.show_table(gameView, true);
                    askCardDraw();
                }
                else
                    ui.show_current_player(gameView, nickname);
            }
            /*case SENT_MESSAGE -> {
                //todo fix
                ui.show_sent_message(nickname);
            }*/
        }
    }

    private void statusEnded(Event event) throws InterruptedException {
        this.inputParser.getDataToProcess().pop();
        try {
            this.inputParser.getDataToProcess().pop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void askNickname() {
        boolean validNickname = false;
        String invalidChars = "0123456789~£!@#$%^&*()-_=+[]{}|;:',.<>?";
        do {
            ui.show_insert_nickname();
            try {
                nickname = this.inputParser.getDataToProcess().pop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // check for special characters
            if (nickname.matches(".*[" + Pattern.quote(invalidChars) + "].*")) {
                ui.show_invalid_username();
            } else if (nickname.isEmpty()) {
                ui.show_empty_nickname();
            } else {
                validNickname = true;
            }
        } while (!validNickname);
        //ui.show_chosenNickname(nickname);
    }

    public boolean isCreate() {
        return create;
    }

    private boolean askSelectGame(Boolean nicknameAlreadyUsed, String previous) {
        String choice;
        if (nicknameAlreadyUsed){
            nicknameAlreadyUsed = false;
            choice = previous;
        }
        else {
            ui.show_lobby();
            try {
                choice = this.inputParser.getDataToProcess().pop();
                current_choice = choice;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        switch (choice) {
            case "1" -> {
                create = true;
                num = askNicknameAndPlayers();
                createGame(nickname, num);
                colorSelected.put(nickname,false);
            }
            case "2" -> {
                askNickname();
                joinFirstGameAvailable(nickname);
                colorSelected.put(nickname,false);
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    private void askColor() {
        String color;
        if (colorNotAvailable){
            ui.show_color_not_available();
        }

        ui.show_available_colors(availableColors);
        try {
            color = this.inputParser.getDataToProcess().pop();
            if (!availableColors.contains(color.toUpperCase())) {
                ui.show_color_not_available();
                askColor();
                return;
            }
            chooseColor(nickname, color);
            colorSelected.put(nickname,true);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer askNicknameAndPlayers() {
        boolean validInputs = false;
        String invalidChars = "0123456789~£!@#$%^&*()-_=+[]{}|;:',.<>?";

        String tempNickname = null;
        Integer tempNumPlayers = null;

        do {
            boolean validNickname = false;
            boolean validNumPlayers = false;

            ui.show_insert_nickname();
            try {
                tempNickname = this.inputParser.getDataToProcess().pop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (tempNickname.matches(".*[" + Pattern.quote(invalidChars) + "].*")) {
                ui.show_invalid_username();
            } else if (tempNickname.isEmpty()) {
                ui.show_empty_nickname();
            } else {
                validNickname = true;
            }

            if(validNickname){
                ui.show_insert_num_player();

                try {
                    String temp = this.inputParser.getDataToProcess().pop();
                    tempNumPlayers = Integer.parseInt(temp);
                    if (tempNumPlayers < 2 || tempNumPlayers > 4) {
                        ui.show_invalid_num_player();
                        tempNumPlayers = null;
                    } else {
                        validNumPlayers = true;
                    }
                } catch (NumberFormatException | InterruptedException e) {
                    ui.show_invalid_num_player();
                }
            }

            // Se entrambi gli input sono validi, aggiorna le variabili principali
            if (validNickname && validNumPlayers) {
                nickname = tempNickname;
                validInputs = true;
            }
        } while (!validInputs);
        return tempNumPlayers;
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
                ui.show_nan();
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
                    ui.show_invalid_initialcard();
                    continue; // re-enter the loop
                }
                chooseInitialCardSide(nickname, choice);
                retry = false;
            } catch (NumberFormatException e) {
                ui.show_invalid_initialcard();
            } catch (Exception e) {
                ui.show_error();
            }
        } while (retry);
    }

    private void askCardPlay() {
        String command;
        //ui.show_player_view();
        do {
            try {
                ui.show_command();
                command = this.inputParser.getDataToProcess().pop();
                String[] command_args = command.split(" ");

                switch (command_args[0]) {
                    case "/play" -> {
                        if(command_args.length != 5) {
                            ui.show_invalid_play_command();
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
                            ui.show_invalid_play_number();
                            continue;
                        }
                        if (cardIndex < 0 || cardIndex >= gameView.getPlayerView().getPlayerHand().size()) {
                            ui.show_invalid_index();
                            continue;
                        }
                        if (x < 0 || x >= gameView.getCommon().getPlayerView(nickname).getBoard().length || y < 0 || y >= gameView.getCommon().getPlayerView(nickname).getBoard()[0].length) {
                            ui.show_invalid_coordinates();
                            continue;
                        }
                        if (!gameView.getCommon().getPlayerView(nickname).getPossiblePlacements()[x][y]) {
                            ui.show_invalid_positioning();
                            continue;
                        }
                        playCard(nickname, cardIndex, front, x, y);
                    }
                    case "/table" -> {
                        ui.show_start_table(gameView);
                        continue;
                    }
                    case "/chat" -> {
                        ui.show_messages();
                        continue;
                    }
                    case "/help" -> {
                        ui.show_menu();
                        continue;
                    }
                    default -> {
                        ui.show_invalid_command();
                        continue;
                    }
                }
            } catch (Exception e) {
                ui.show_error();
                continue;
            }
            break;
        } while (true);
    }

    private void askCardDraw() {
        String command;
        //ui.show_public_board_view();
        do {
            try {
                ui.show_command();
                command = this.inputParser.getDataToProcess().pop();
                String[] command_args = command.split(" ");
                if (!command_args[0].equalsIgnoreCase("/draw") || command_args.length < 2) {
                    ui.show_invalid_draw_command();
                    continue;
                }
                int cardIndex = Integer.parseInt(command_args[1]);
                if (cardIndex < 0 || cardIndex >= 6) {
                    ui.show_invalid_index();
                    continue;
                }
                drawCard(nickname, cardIndex);
            } catch (NumberFormatException e) {
                ui.show_invalid_index();
                continue;
            } catch (Exception e) {
                ui.show_error();
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
            ui.show_error_create_game();
        }
    }

    @Override
    public void joinFirstGameAvailable(String nickname) {
        //ui.show_joiningFirstAvailableMsg(nickname);
        try {
            actions.joinFirstGameAvailable(nickname);
        } catch (IOException | NotBoundException e) {
            ui.show_error_join_game();
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

    @Override
    public void heartbeat() throws IOException {

    }

    //EVENTS RECEIVED FROM THE SERVER

    @Override
    public void invalidNumPlayers() {
        addEvent(EventType.APP_MENU);
    }

    @Override
    public void playerJoined(ArrayList<String> players, String current, int num) {
        if(colorSelected.get(current))
            ui.show_joined_players(players,current, num);
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
        this.status = GameStatus.FIRST_PHASE;
        addEvent(EventType.AVAILABLE_COLORS);
    }

    @Override
    public void notAvailableColors(ArrayList<String> colors) throws RemoteException {
        this.availableColors = colors;
        this.status = GameStatus.FIRST_PHASE;
        addEvent(EventType.NOT_AVAILABLE_COLORS);
    }

    @Override
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) {
        this.gameView = gameView;
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
        ui.show_invalid_positioning();
        addEvent(EventType.BEGIN_PLAY);
    }

    @Override
    public void requirementsNotMet() {
        ui.show_requirements_not_met();
        addEvent(EventType.BEGIN_PLAY);
    }

    @Override
    public void beginDraw(GameView gameView) {
        this.gameView = gameView;
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
    public void sentMessage(String sender, String receiver, String message, String time) {
        //Show the message only if is for everyone or is for me (or I sent it)
        if(sender.equals(nickname)) ui.add_message_sent(receiver, message, time);
        else ui.add_message_received(sender, receiver, message, time);
        addEvent(EventType.SENT_MESSAGE);
    }

    @Override
    public void gameEnded(String winner, HashMap<String,Integer> rank) {
        this.status = GameStatus.ENDED;
        addEvent(EventType.GAME_ENDED);
        ui.show_winner_and_rank(winner.equals(nickname), rank, winner);
        //resetGameId(fileDisconnection, gameModel);
    }

    private void addEvent(EventType type) {
        events.add(new Event(type));
        if (type.equals(EventType.AVAILABLE_COLORS) || type.equals(EventType.PLAYER_JOINED) || (status != null && status.equals(GameStatus.RUNNING)))
            joined = true;

        if(type.equals(EventType.APP_MENU))
            joined = false;
    }

    @Override
    public void noConnectionError() {
        ui.show_no_connection_error();
    }
}
