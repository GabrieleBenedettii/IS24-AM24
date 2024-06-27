package it.polimi.ingsw.am24.view;

import it.polimi.ingsw.am24.listeners.GameListener;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.network.rmi.RMIClient;
import it.polimi.ingsw.am24.network.socket.SocketClient;
import it.polimi.ingsw.am24.view.TUI.TUI;
import it.polimi.ingsw.am24.view.GUI.GUI;
import it.polimi.ingsw.am24.view.GUI.GUIApplication;
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

/**
 * The {@code Flow} class implements the game flow logic, handling client actions and game events.
 * It runs as a separate thread and interacts with the user through the UI.
 */
public class Flow implements Runnable, ClientActions, GameListener {
    private int gameId;
    private String nickname;
    private String current_choice = null;
    private boolean create = false;
    private int num;
    private ClientActions actions;
    private GameStatus status = GameStatus.NOT_STARTED;
    private final Queue<EventType> events = new LinkedList<>();
    private final UI ui;
    protected InputParser inputParser;
    protected InputReader inputReader;
    private boolean joined;
    private ArrayList<String> availableColors;
    private ArrayList<GameCardView> cards;
    private GameView gameView;
    private HashMap<String,Boolean> colorSelected = new HashMap<>();

    /**
     * Constructs a new {@code Flow} with the specified connection type for CLI.
     *
     * @param connectionType the type of connection ("RMI" or "SOCKET")
     */
    public Flow(String connectionType) {
        switch (connectionType) {
            case "RMI" -> actions = new RMIClient(this);
            case "SOCKET" -> actions = new SocketClient(this);
        }

        this.ui = new TUI();

        this.nickname = "";
        this.joined = false;
        this.num = 0;
        this.inputReader = new InputReaderCLI();
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }


    /**
     * Constructs a new {@code Flow} with the specified connection type for GUI.
     *
     * @param application    the GUI application
     * @param connectionType the type of connection ("RMI" or "SOCKET")
     */
    public Flow(GUIApplication application, String connectionType) {
        switch (connectionType) {
            case "RMI" -> actions = new RMIClient(this);
            case "SOCKET" -> actions = new SocketClient(this);
        }
        this.inputReader = new InputReaderGUI();

        this.ui = new GUI(application, (InputReaderGUI) inputReader);

        this.nickname = "";
        this.joined = false;
        this.num = 0;
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
        new Thread(this).start();
    }

    /**
     * The main run loop of the {@code Flow} thread.
     */
    @Override
    public void run() {
        EventType event;
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
                                statusEnded();
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

    /**
     * Handles the events when the player is not currently in a game.
     *
     * @param event the event type to be processed
     */
    private void statusNotInAGame(EventType event) {
        switch (event) {
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
                events.add(EventType.APP_MENU);
            }
            case NO_LOBBY_AVAILABLE -> {
                nickname = null;
                ui.show_no_lobby_available();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(EventType.APP_MENU);
            }
        }
    }

    /**
     * Handles the events during the waiting phase of the game.
     *
     * @param event the event type to be processed
     * @throws IOException if there is an IO error
     * @throws InterruptedException if the thread is interrupted
     */
    private void statusWait(EventType event) throws IOException, InterruptedException {
        switch (event) {
            case AVAILABLE_COLORS -> askColor(false);
            case NOT_AVAILABLE_COLORS -> {
                askColor(true);
            }
            case HIDDEN_GOAL_CHOICE -> askSecretGoalDealt();
            case INITIAL_CARD_SIDE -> askInitialCardSide();
        }
    }

    /**
     * Handles the events during the running phase of the game.
     *
     * @param event the event type to be processed
     * @throws IOException if there is an IO error
     * @throws InterruptedException if the thread is interrupted
     */
    private void statusRunning(EventType event) throws IOException, InterruptedException {
        switch (event) {
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
        }
    }

    /**
     * Handles the game status when it has ended.
     *
     * @throws InterruptedException if the thread is interrupted
     */
    private void statusEnded() throws InterruptedException {
        this.inputParser.getDataToProcess().pop();
        try {
            this.inputParser.getDataToProcess().pop();
            stopHeartbeat();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts the user to input a nickname until a valid nickname is provided.
     * A valid nickname should not contain special characters and should not be empty.
     * Shows appropriate UI messages for invalid inputs.
     */
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

    /**
     * Returns the current value of the "create" flag.
     * The "create" flag indicates whether the user intends to create a new game.
     *
     * @return true if the user intends to create a new game, false otherwise.
     */
    public boolean isCreate() {
        return create;
    }

    /**
     * Asks the user to select between creating a new game or joining an existing one based on input choices.
     * Handles actions based on user input and updates game state accordingly.
     *
     * @param nicknameAlreadyUsed Flag indicating if the nickname has already been used previously.
     * @param previous            Previous choice made by the user.
     * @return true if the selection was successful and processed; false otherwise.
     */
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

    /**
     * Asks the user to choose a color from the available colors.
     * Handles the selection process and updates the game state accordingly.
     */
    private void askColor(boolean colorNotAvailable) {
        String color;
        if (colorNotAvailable){
            ui.show_color_not_available();
        }

        ui.show_available_colors(availableColors);
        try {
            color = this.inputParser.getDataToProcess().pop();
            if (!availableColors.contains(color.toUpperCase())) {
                ui.show_color_not_available();
                askColor(false);
                return;
            }
            chooseColor(nickname, color);
            colorSelected.put(nickname,true);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts the user to enter a nickname and number of players, ensuring both inputs are valid.
     * Displays appropriate messages for invalid inputs and updates the game state upon successful input.
     * @return The number of players chosen by the user.
     */
    private Integer askNicknameAndPlayers() {
        boolean validInputs = false;
        String invalidChars = "0123456789~£!@#$%^&*()-_=+[]{}|;:',.<>?";

        String tempNickname;
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

    /**
     * Prompts the player to select a secret goal from the dealt cards.
     * This method displays the start table and hidden goal options to the player,
     * waits for the player's input, and processes the chosen hidden goal.
     * If the input is invalid, it prompts the player again until a valid choice is made.
     */
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

    /**
     * Prompts the player to choose the initial side of a card.
     * This method displays the initial card side options to the player,
     * waits for the player's input, and processes the chosen card side.
     * If the input is invalid, it prompts the player again until a valid choice is made.
     */
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

    /**
     * Prompts the player to play a card.
     * This method displays the command prompt to the player, waits for the player's input,
     * and processes the play card command. It validates the command and parameters before execution.
     * If the input is invalid, it prompts the player again until a valid command is given.
     */
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
                    case "/hidden" -> {
                        ui.show_hidden_goal_card(gameView.getPlayerView().getHiddenGoal());
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

    /**
     * Prompts the player to draw a card.
     * This method displays the command prompt to the player, waits for the player's input,
     * and processes the draw card command. It validates the command and parameters before execution.
     * If the input is invalid, it prompts the player again until a valid command is given.
     */
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
    /**
     * Creates a new game with the specified nickname and number of players.
     *
     * @param nickname the nickname of the player creating the game
     * @param numPlayers the number of players for the game
     */
    public void createGame(String nickname, int numPlayers) {
        //ui.show_creatingNewGameMsg(nickname);
        try {
            actions.createGame(nickname, numPlayers);
        } catch (IOException | NotBoundException e) {
            ui.show_error_create_game();
        }
    }

    /**
     * Joins the first available game with the specified nickname.
     *
     * @param nickname the nickname of the player joining the game
     */
    public void joinFirstGameAvailable(String nickname) {
        //ui.show_joiningFirstAvailableMsg(nickname);
        try {
            actions.joinFirstGameAvailable(nickname);
        } catch (IOException | NotBoundException e) {
            ui.show_error_join_game();
        }
    }

    /**
     * Chooses a color for the player with the specified nickname.
     *
     * @param nickname the nickname of the player choosing the color
     * @param color the color chosen by the player
     * @throws IOException if an I/O error occurs
     */
    public void chooseColor(String nickname, String color) throws IOException {
        actions.chooseColor(nickname, color);
    }

    /**
     * Chooses a hidden goal for the player with the specified nickname.
     *
     * @param nickname the nickname of the player choosing the hidden goal
     * @param choice the index of the chosen hidden goal
     * @throws IOException if an I/O error occurs
     */
    public void chooseHiddenGoal(String nickname, int choice) throws IOException {
        actions.chooseHiddenGoal(nickname, choice);
    }

    /**
     * Chooses the initial card side for the player with the specified nickname.
     *
     * @param nickname the nickname of the player choosing the initial card side
     * @param choice the index of the chosen card side
     * @throws IOException if an I/O error occurs
     */
    public void chooseInitialCardSide(String nickname, int choice) throws IOException {
        actions.chooseInitialCardSide(nickname, choice);
    }

    /**
     * Plays a card for the player with the specified nickname.
     *
     * @param nickname the nickname of the player playing the card
     * @param cardIndex the index of the card to be played
     * @param front true if the card is to be played face up, false otherwise
     * @param x the x-coordinate of the placement position
     * @param y the y-coordinate of the placement position
     * @throws IOException if an I/O error occurs
     */
    public void playCard(String nickname, int cardIndex, boolean front, int x, int y) throws IOException {
        actions.playCard(nickname, cardIndex, front, x, y);
    }

    /**
     * Draws a card for the player with the specified nickname.
     *
     * @param nickname the nickname of the player drawing the card
     * @param choice the index of the chosen card to draw
     * @throws IOException if an I/O error occurs
     */
    public void drawCard(String nickname, int choice) throws IOException {
        actions.drawCard(nickname, choice);
    }

    /**
     * Sends a public message from the specified sender.
     *
     * @param sender the nickname of the sender
     * @param message the message to be sent
     * @throws RemoteException if a remote communication error occurs
     */
    public void sendPublicMessage(String sender, String message) throws RemoteException {
        actions.sendPublicMessage(sender, message);
    }

    /**
     * Sends a private message from the specified sender to the specified receiver.
     *
     * @param sender the nickname of the sender
     * @param receiver the nickname of the receiver
     * @param message the message to be sent
     * @throws RemoteException if a remote communication error occurs
     */
    public void sendPrivateMessage(String sender, String receiver, String message) throws RemoteException {
        actions.sendPrivateMessage(sender, receiver, message);
    }

    /**
     * Sends a heartbeat signal to maintain the connection.
     *
     * @throws IOException if an I/O error occurs
     */
    public void heartbeat() throws IOException {

    }

    /**
     * Stops sending heartbeat signals.
     */
    public void stopHeartbeat() {
        actions.stopHeartbeat();
    }

    //EVENTS RECEIVED FROM THE SERVER

    /**
     * Handles the event of an invalid number of players.
     */
    public void invalidNumPlayers() {
        addEvent(EventType.APP_MENU);
    }

    /**
     * Handles the event of a player joining the game.
     *
     * @param players the list of players in the game
     * @param current the nickname of the current player
     * @param num the total number of players in the game
     */
    public void playerJoined(ArrayList<String> players, String current, int num) {
        if(colorSelected.get(current))
            ui.show_joined_players(players,current, num);
    }

    /**
     * Handles the event of no lobby being available.
     */
    public void noLobbyAvailable() {
        events.add(EventType.NO_LOBBY_AVAILABLE);
    }

    /**
     * Handles the event of a nickname already being used.
     */
    public void nicknameAlreadyUsed() {
        events.add(EventType.NICKNAME_ALREADY_USED);
    }

    /**
     * Handles the event of available colors being received.
     *
     * @param colors the list of available colors
     */
    public void availableColors(ArrayList<String> colors) {
        this.availableColors = colors;
        this.status = GameStatus.FIRST_PHASE;
        addEvent(EventType.AVAILABLE_COLORS);
    }

    /**
     * Handles the event of not available colors being received.
     *
     * @param colors the list of not available colors
     * @throws RemoteException if a remote communication error occurs
     */
    public void notAvailableColors(ArrayList<String> colors) throws RemoteException {
        this.availableColors = colors;
        this.status = GameStatus.FIRST_PHASE;
        addEvent(EventType.NOT_AVAILABLE_COLORS);
    }

    /**
     * Handles the event of a hidden goal choice being made.
     *
     * @param cardViews the list of card views representing the hidden goals
     * @param gameView the current state of the game view
     */
    public void hiddenGoalChoice(ArrayList<GameCardView> cardViews, GameView gameView) {
        this.gameView = gameView;
        this.cards = cardViews;
        addEvent(EventType.HIDDEN_GOAL_CHOICE);
    }

    /**
     * Handles the event of an initial card side being chosen.
     *
     * @param front the front side of the card
     * @param back the back side of the card
     */
    public void initialCardSide(GameCardView front, GameCardView back) {
        this.cards = new ArrayList<>();
        cards.add(front);
        cards.add(back);
        addEvent(EventType.INITIAL_CARD_SIDE);
    }

    /**
     * Handles the event of a turn beginning.
     *
     * @param gameView the current state of the game view
     */
    public void beginTurn(GameView gameView) {
        this.gameView = gameView;
        this.status = GameStatus.RUNNING;
        addEvent(EventType.BEGIN_PLAY);
    }

    /**
     * Handles the event of an invalid positioning of a card.
     */
    public void invalidPositioning() {
        ui.show_invalid_positioning();
        addEvent(EventType.BEGIN_PLAY);
    }

    /**
     * Handles the event of requirements not being met.
     */
    public void requirementsNotMet() {
        ui.show_requirements_not_met();
        addEvent(EventType.BEGIN_PLAY);
    }
    /**
     * Begins the draw phase of the game.
     * This method sets the game status to running, updates the game view, and triggers the BEGIN_DRAW event.
     *
     * @param gameView The current game view to be updated.
     */
    public void beginDraw(GameView gameView) {
        this.gameView = gameView;
        this.status = GameStatus.RUNNING;
        addEvent(EventType.BEGIN_DRAW);
    }

    /**
     * Handles an incorrect card play.
     * This method updates the game view, displays a message indicating the wrong card play,
     * and triggers the BEGIN_PLAY event.
     *
     * @param gameView The current game view to be updated.
     */
    public void wrongCardPlay(GameView gameView) {
        this.gameView = gameView;
        ui.show_wrong_card_play();
        addEvent(EventType.BEGIN_PLAY);
    }

    /**
     * Handles sending a message.
     * This method displays the "sent" or "received" message based on the sender and receiver,
     * and triggers the SENT_MESSAGE event.
     *
     * @param sender The sender of the message.
     * @param receiver The receiver of the message.
     * @param message The content of the message.
     * @param time The time the message was sent.
     */
    public void sentMessage(String sender, String receiver, String message, String time) {
        //Show the message only if is for everyone or is for me (or I sent it)
        if(sender.equals(nickname)) ui.add_message_sent(receiver, message, time);
        else ui.add_message_received(sender, receiver, message, time);
        addEvent(EventType.SENT_MESSAGE);
    }

    /**
     * Handles the end of the game.
     * This method updates the game status to ended, displays the winner and rankings,
     * and triggers the GAME_ENDED event.
     *
     * @param winner The nickname of the player who won the game.
     * @param rank The final rankings of the players.
     */
    public void gameEnded(String winner, HashMap<String,Integer> rank) {
        this.status = GameStatus.ENDED;
        addEvent(EventType.GAME_ENDED);
        ui.show_winner_and_rank(Arrays.stream(winner.split(",")).toList().contains(nickname), rank, winner);
    }

    /**
     * Adds an event to the event list and updates the joined status based on the event type.
     *
     * @param type The type of event to be added.
     */
    private void addEvent(EventType type) {
        events.add(type);
        if (type.equals(EventType.AVAILABLE_COLORS) || type.equals(EventType.PLAYER_JOINED) || (status != null && status.equals(GameStatus.RUNNING)))
            joined = true;

        if(type.equals(EventType.APP_MENU))
            joined = false;
    }

    /**
     * Handles a no connection error.
     * This method displays a message indicating that there is no connection.
     */
    public void noConnectionError() {
        ui.show_no_connection_error();
    }
}
