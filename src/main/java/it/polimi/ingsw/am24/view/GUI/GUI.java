package it.polimi.ingsw.am24.view.GUI;

import it.polimi.ingsw.am24.modelview.*;
import it.polimi.ingsw.am24.view.UI;
import it.polimi.ingsw.am24.view.GUI.controllers.*;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;
import javafx.application.Platform;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * GUI class responsible for managing user interface interactions and displaying game views.
 * Extends UI to implement specific UI-related methods.
 */
public class GUI extends UI {
    private GUIApplication guiApplication;
    private InputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher = false;
    private boolean alreadyShowedLobby = false;
    private PrintStream out;
    private Queue<String> messages;

    private String nickname;

    /**
     * Constructor to initialize GUI with GUIApplication and InputReaderGUI.
     *
     * @param guiApplication GUIApplication instance.
     * @param inputReaderGUI InputReaderGUI instance.
     */
    public GUI(GUIApplication guiApplication, InputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI = inputReaderGUI;
        nickname = null;
        init();
    }

    /**
     * Initializes the GUI, setting up necessary structures.
     */
    public void init() {
        messages = new LinkedList<>();
    }

    /**
     * Calls a method on the JavaFX thread using Platform.runLater().
     *
     * @param r Runnable to execute on the JavaFX thread.
     */
    public void callPlatformRunLater(Runnable r) {
        //Need to use this method to call any methods inside the GuiApplication
        //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
        Platform.runLater(r);
    }

    /**
     * Displays the UI to insert a nickname based on the game flow (create or join game).
     */
    @Override
    public void show_insert_nickname() {
        callPlatformRunLater(() -> {
            if(guiApplication.getGameflow().isCreate()){
                this.guiApplication.setActiveScene(Scenes.CREATE_GAME);
            }
            else{
                this.guiApplication.setActiveScene(Scenes.JOIN_GAME);
            }
        });
    }

    /**
     * Displays visible symbols on the game view.
     *
     * @param gameView GameView object containing the game view data.
     */
    @Override
    public void show_visibleSymbols(GameView gameView) {

    }

    /**
     * Displays the game view with updated data.
     *
     * @param gameView GameView object containing the updated game view.
     */
    @Override
    public void show_gameView(GameView gameView) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.beginTurn(gameView);
        });
    }

    /**
     * Displays the main menu UI.
     */
    @Override
    public void show_menu() {

    }

    /**
     * Displays the command UI.
     */
    @Override
    public void show_command() {

    }

    /**
     * Prompts the hidden goal.
     */
    @Override
    public void show_hidden_goal_card(GameCardView card) {

    }

    /**
     * Displays the table UI with game data.
     *
     * @param gameView  GameView object containing the game data.
     * @param forChoice Boolean indicating if the table is for making a choice.
     */
    @Override
    public void show_table(GameView gameView, boolean forChoice) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.beginDraw(gameView);
        });
    }

    /**
     * Displays the start table UI with initial game data.
     *
     * @param gameView GameView object containing the initial game data.
     */
    @Override
    public void show_start_table(GameView gameView) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.GAME);
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.hiddenGoalChoice(gameView);
        });
    }

    /**
     * Displays the lobby UI.
     */
    @Override
    public void show_lobby() {
        callPlatformRunLater(() ->  {
            this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI);
            this.guiApplication.createNewWindowWithStyle();
            this.guiApplication.setActiveScene(Scenes.MENU);
        });
    }

    /**
     * Displays available colors for selection.
     *
     * @param colors ArrayList of Strings representing available colors.
     */
    @Override
    public void show_available_colors(ArrayList<String> colors) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.COLOR_SELECTOR);
            ColorSelectorController selector = (ColorSelectorController) this.guiApplication.getController(Scenes.COLOR_SELECTOR);
            selector.initialize(colors);
        });
    }

    /**
     * Displays hidden goals for selection.
     *
     * @param views ArrayList of GameCardView objects representing hidden goals.
     */
    @Override
    public void show_hidden_goal(ArrayList<GameCardView> views) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.drawChooseHiddenGoal(views);
        });
    }

    /**
     * Displays initial card sides for selection.
     *
     * @param views ArrayList of GameCardView objects representing initial card sides.
     */
    @Override
    public void show_initial_side(ArrayList<GameCardView> views) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.INITIAL_CARD_SELECTOR);
            InitialCardSelectorController selector = (InitialCardSelectorController) this.guiApplication.getController(Scenes.INITIAL_CARD_SELECTOR);
            selector.Initialize(views);
        });
    }

    /**
     * Displays the current player's turn in the game view.
     *
     * @param gameView   GameView object containing current game state.
     * @param myNickname String representing the current player's nickname.
     */
    @Override
    public void show_current_player(GameView gameView, String myNickname) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.notYourTurn(gameView, myNickname);
        });
    }

    /**
     * Displays the game logo.
     */
    @Override
    public void show_logo() {
    }

    /**
     * Displays authors and rules of the game.
     */
    @Override
    public void show_authors_and_rules() {

    }

    /**
     * Displays error message for invalid username during game setup.
     */
    @Override
    public void show_invalid_username() {
        callPlatformRunLater(() -> {
            if(guiApplication.getGameflow().isCreate()){
                CreateGameController controller = (CreateGameController) this.guiApplication.getController((Scenes.CREATE_GAME));
                controller.showInvalidUsername();
            }
            else{
                JoinGameController controller = (JoinGameController) this.guiApplication.getController((Scenes.JOIN_GAME));
                controller.showInvalidUsername();
            }
        });
    }

    /**
     * Displays error message for nickname already being used during game setup.
     */
    @Override
    public void show_nickname_already_used() {
        callPlatformRunLater(() -> {
            if(guiApplication.getGameflow().isCreate()){
                CreateGameController controller = (CreateGameController) this.guiApplication.getController((Scenes.CREATE_GAME));
                controller.initialize();
                controller.showNicknameAlreadyUsed();
            }
            else{
                JoinGameController controller = (JoinGameController) this.guiApplication.getController((Scenes.JOIN_GAME));
                controller.initialize();
                controller.showNicknameAlreadyUsed();
            }
        });
    }

    /**
     * Displays error message for empty nickname during game setup.
     */
    @Override
    public void show_empty_nickname() {
        callPlatformRunLater(() -> {
            if(guiApplication.getGameflow().isCreate()){
                CreateGameController controller = (CreateGameController) this.guiApplication.getController((Scenes.CREATE_GAME));
                controller.showEmptyUsername();
            }
            else{
                JoinGameController controller = (JoinGameController) this.guiApplication.getController((Scenes.JOIN_GAME));
                controller.showEmptyUsername();
            }
        });
    }

    /**
     * Displays error message for no lobby available during game setup.
     */
    @Override
    public void show_no_lobby_available() {
        callPlatformRunLater(() -> {
            JoinGameController controller = (JoinGameController) this.guiApplication.getController((Scenes.JOIN_GAME));
            controller.showNoLobbyAvaiable();
        });
    }

    /**
     * Displays error message for color not available during color selection.
     */
    @Override
    public void show_color_not_available() {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.COLOR_SELECTOR);
            ColorSelectorController selector = (ColorSelectorController) this.guiApplication.getController(Scenes.COLOR_SELECTOR);
            selector.showColorNotAvailable();
        });
    }

    /**
     * Displays UI to insert the number of players for game setup.
     */
    @Override
    public void show_insert_num_player() {
        callPlatformRunLater(() -> {
            CreateGameController controller = (CreateGameController) this.guiApplication.getController((Scenes.CREATE_GAME));
            controller.initialize();
        });
    }

    /**
     * Displays error message for invalid number of players during game setup.
     */
    @Override
    public void show_invalid_num_player() {
        callPlatformRunLater(() -> {
            CreateGameController controller = (CreateGameController) this.guiApplication.getController((Scenes.CREATE_GAME));
            controller.showInvalidNumberOfPlayers();
        });
    }

    /**
     * Displays error message for invalid initial card selection.
     */
    @Override
    public void show_invalid_initialcard() {

    }

    /**
     * Displays error message for invalid play command during gameplay.
     */
    @Override
    public void show_invalid_play_command() {

    }

    /**
     * Displays error message for invalid play number during gameplay.
     */
    @Override
    public void show_invalid_play_number() {

    }

    /**
     * Displays error message for invalid index during gameplay.
     */
    @Override
    public void show_invalid_index() {

    }

    /**
     * Displays error message for invalid coordinates during gameplay.
     */
    @Override
    public void show_invalid_coordinates() {

    }

    /**
     * Displays error message for invalid positioning during gameplay.
     */
    @Override
    public void show_invalid_positioning() {

    }

    /**
     * Displays error message for invalid command during gameplay.
     */
    @Override
    public void show_invalid_command() {

    }

    /**
     * Displays error message for general error during gameplay.
     */
    @Override
    public void show_error() {

    }

    /**
     * Displays error message for invalid draw command during gameplay.
     */
    @Override
    public void show_invalid_draw_command() {

    }

    /**
     * Displays error message for error creating game.
     */
    @Override
    public void show_error_create_game() {

    }

    /**
     * Displays error message for error joining game.
     */
    @Override
    public void show_error_join_game() {

    }

    /**
     * Displays requirements not met during gameplay.
     */
    @Override
    public void show_requirements_not_met() {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.requirementsNotMet();
        });
    }

    /**
     * Displays Not-a-Number (NaN) error during gameplay.
     */
    @Override
    public void show_nan() {

    }

    /**
     * Displays no connection error during gameplay.
     */
    @Override
    public void show_no_connection_error() {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.SERVER_DISCONNECTION);
            //GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.MENU);
            //controller.connectionError();
        });


    }

    /**
     * Displays the menu options if not already shown.
     */
    private void show_menuOptions() {
        if (alreadyShowedPublisher) {
            callPlatformRunLater(() ->  {
                this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI);
                this.guiApplication.createNewWindowWithStyle();
                this.guiApplication.setActiveScene(Scenes.MENU);
            });
        }
    }

    /**
     * Displays winner and rank information at the end of the game.
     *
     * @param winner     Boolean indicating if the player is a winner.
     * @param rank       HashMap containing player rankings.
     * @param winnerNick String representing the winner's nickname.
     */
    @Override
    public void show_winner_and_rank(boolean winner, HashMap<String, Integer> rank, String winnerNick) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.END_GAME);
            EndGameController selector = (EndGameController) this.guiApplication.getController(Scenes.END_GAME);
            selector.rankings(rank,winner,winnerNick);
        });
    }

    /**
     * Displays error message for wrong card play during gameplay.
     */
    @Override
    public void show_wrong_card_play() {

    }

    /**
     * Displays joined players in the lobby UI.
     *
     * @param players ArrayList of Strings representing joined players.
     * @param current String representing the current player's nickname.
     * @param num     Integer representing the number of players in the lobby.
     */
    @Override
    public void show_joined_players(ArrayList<String> players, String current, int num) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.LOBBY);
            LobbyViewController controller = (LobbyViewController) this.guiApplication.getController(Scenes.LOBBY);
            controller.initialize(players, current, num);
        });
    }

    /**
     * Displays received messages in the game board UI.
     */
    @Override
    public void show_messages() {

    }

    /**
     * Adds received message to the game board UI.
     *
     * @param sender   String representing the sender of the message.
     * @param receiver String representing the receiver of the message.
     * @param message  String representing the message content.
     * @param time     String representing the time of the message.
     */
    @Override
    public void add_message_received(String sender, String receiver, String message, String time) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.addMessage("[" + time + "] " + (receiver.equals("") ? "[PUBLIC]" : "[PRIVATE]") + " from " + sender + ": " + message);
        });
    }

    /**
     * Adds sent message to the game board UI.
     *
     * @param receiver String representing the receiver of the message.
     * @param message  String representing the message content.
     * @param time     String representing the time of the message.
     */
    @Override
    public void add_message_sent(String receiver, String message, String time) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.addMessage("[" + time + "] " + (receiver.equals("") ? "[PUBLIC] to all" : ("[PRIVATE] to " + receiver)) + ": " + message);
        });
    }

    /**
     * Displays the chosen nickname in the game board UI.
     *
     * @param nickname String representing the chosen nickname.
     */
    @Override
    public void show_chosenNickname(String nickname) {

    }

    /**
     * Displays the network type UI.
     */
    @Override
    public void show_network_type() {

    }
}
