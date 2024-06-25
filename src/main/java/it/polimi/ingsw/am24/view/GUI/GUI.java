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

public class GUI extends UI {
    private GUIApplication guiApplication;
    private InputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher = false;
    private boolean alreadyShowedLobby = false;
    private PrintStream out;
    private Queue<String> messages;

    private String nickname;

    public GUI(GUIApplication guiApplication, InputReaderGUI inputReaderGUI) {
        this.guiApplication = guiApplication;
        this.inputReaderGUI = inputReaderGUI;
        nickname = null;
        init();
    }
    public void init() {
        messages = new LinkedList<>();
    }

    public void callPlatformRunLater(Runnable r) {
        //Need to use this method to call any methods inside the GuiApplication
        //Doing so, the method requested will be executed on the JavaFX Thread (else exception)
        Platform.runLater(r);
    }

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

    @Override
    public void show_visibleSymbols(GameView gameView) {

    }

    @Override
    public void show_gameView(GameView gameView) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.beginTurn(gameView);
        });
    }

    @Override
    public void show_menu() {

    }

    @Override
    public void show_command() {

    }

    @Override
    public void show_table(GameView gameView, boolean forChoice) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.beginDraw(gameView);
        });
    }

    @Override
    public void show_start_table(GameView gameView) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.GAME);
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.hiddenGoalChoice(gameView);
        });
    }

    @Override
    public void show_lobby() {
        callPlatformRunLater(() ->  {
            this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI);
            this.guiApplication.createNewWindowWithStyle();
            this.guiApplication.setActiveScene(Scenes.MENU);
        });
    }

    @Override
    public void show_available_colors(ArrayList<String> colors) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.COLOR_SELECTOR);
            ColorSelectorController selector = (ColorSelectorController) this.guiApplication.getController(Scenes.COLOR_SELECTOR);
            selector.initialize(colors);
        });
    }

    @Override
    public void show_hidden_goal(ArrayList<GameCardView> views) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.drawChooseHiddenGoal(views);
        });
    }

    @Override
    public void show_initial_side(ArrayList<GameCardView> views) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.INITIAL_CARD_SELECTOR);
            InitialCardSelectorController selector = (InitialCardSelectorController) this.guiApplication.getController(Scenes.INITIAL_CARD_SELECTOR);
            selector.Initialize(views);
        });
    }

    @Override
    public void show_current_player(GameView gameView, String myNickname) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.notYourTurn(gameView, myNickname);
        });
    }

    @Override
    public void show_logo() {
    }

    @Override
    public void show_authors_and_rules() {

    }

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

    @Override
    public void show_no_lobby_available() {
        callPlatformRunLater(() -> {
            JoinGameController controller = (JoinGameController) this.guiApplication.getController((Scenes.JOIN_GAME));
            controller.showNoLobbyAvaiable();
        });
    }

    @Override
    public void show_color_not_available() {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.COLOR_SELECTOR);
            ColorSelectorController selector = (ColorSelectorController) this.guiApplication.getController(Scenes.COLOR_SELECTOR);
            selector.showColorNotAvailable();
        });
    }

    @Override
    public void show_insert_num_player() {
        callPlatformRunLater(() -> {
            CreateGameController controller = (CreateGameController) this.guiApplication.getController((Scenes.CREATE_GAME));
            controller.initialize();
        });
    }

    @Override
    public void show_invalid_num_player() {
        callPlatformRunLater(() -> {
            CreateGameController controller = (CreateGameController) this.guiApplication.getController((Scenes.CREATE_GAME));
            controller.showInvalidNumberOfPlayers();
        });
    }

    @Override
    public void show_invalid_initialcard() {

    }

    @Override
    public void show_invalid_play_command() {

    }

    @Override
    public void show_invalid_play_number() {

    }

    @Override
    public void show_invalid_index() {

    }

    @Override
    public void show_invalid_coordinates() {

    }

    @Override
    public void show_invalid_positioning() {

    }

    @Override
    public void show_invalid_command() {

    }

    @Override
    public void show_error() {

    }

    @Override
    public void show_invalid_draw_command() {

    }

    @Override
    public void show_error_create_game() {

    }

    @Override
    public void show_error_join_game() {

    }

    @Override
    public void show_requirements_not_met() {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.requirementsNotMet();
        });
    }

    @Override
    public void show_nan() {

    }

    @Override
    public void show_no_connection_error() {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.SERVER_DISCONNECTION);
            //GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.MENU);
            //controller.connectionError();
        });


    }

    private void show_menuOptions() {
        if (alreadyShowedPublisher) {
            callPlatformRunLater(() ->  {
                this.guiApplication.setInputReaderGUItoAllControllers(this.inputReaderGUI);
                this.guiApplication.createNewWindowWithStyle();
                this.guiApplication.setActiveScene(Scenes.MENU);
            });
        }
    }

    @Override
    public void show_winner_and_rank(boolean winner, HashMap<String, Integer> rank, String winnerNick) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.END_GAME);
            EndGameController selector = (EndGameController) this.guiApplication.getController(Scenes.END_GAME);
            selector.rankings(rank,winner,winnerNick);
        });
    }

    @Override
    public void show_wrong_card_play() {

    }

    @Override
    public void show_joined_players(ArrayList<String> players, String current, int num) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.LOBBY);
            LobbyViewController controller = (LobbyViewController) this.guiApplication.getController(Scenes.LOBBY);
            controller.initialize(players, current, num);
        });
    }

    @Override
    public void show_messages() {

    }

    @Override
    public void add_message_received(String sender, String receiver, String message, String time) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.addMessage("[" + time + "] " + (receiver.equals("") ? "[PUBLIC]" : "[PRIVATE]") + " from " + sender + ": " + message);
        });
    }

    @Override
    public void add_message_sent(String receiver, String message, String time) {
        callPlatformRunLater(() -> {
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.addMessage("[" + time + "] " + (receiver.equals("") ? "[PUBLIC] to all" : ("[PRIVATE] to " + receiver)) + ": " + message);
        });
    }

    @Override
    public void show_chosenNickname(String nickname) {

    }

    @Override
    public void show_network_type() {

    }
}
