package it.polimi.ingsw.am24.view.graphicalUser;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.modelView.*;
import it.polimi.ingsw.am24.view.flow.UI;
import it.polimi.ingsw.am24.view.graphicalUser.controllers.*;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GUI extends UI {
    private GUIapp guiApplication;
    private InputReaderGUI inputReaderGUI;
    private boolean alreadyShowedPublisher = false;
    private boolean alreadyShowedLobby = false;
    private PrintStream out;
    private Queue<String> messages;

    private String nickname;

    public GUI(GUIapp guiApplication, InputReaderGUI inputReaderGUI) {
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
                this.guiApplication.setActiveScene(Scenes.CREATEGAMENICKNAMESELECT);
            }
            else{
                this.guiApplication.setActiveScene(Scenes.NICKNAMESELECT);
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
            this.guiApplication.setActiveScene(Scenes.COLORSELECTOR);
            ColorSelector selector = (ColorSelector) this.guiApplication.getController(Scenes.COLORSELECTOR);
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
            this.guiApplication.setActiveScene(Scenes.INITIALCARDSELECTOR);
            InitialCardSelector selector = (InitialCardSelector) this.guiApplication.getController(Scenes.INITIALCARDSELECTOR);
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
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(Scenes.LOGO));
        PauseTransition pause = new PauseTransition(Duration.seconds(Constants.LOGOTIME));
        pause.setOnFinished(event -> {
            alreadyShowedPublisher = true;

            this.show_lobby();
        });
        pause.play();
    }

    @Override
    public void show_authors_and_rules() {

    }

    @Override
    public void show_invalid_username() {
        callPlatformRunLater(() -> {
            if(guiApplication.getGameflow().isCreate()){
                CreateGameNicknameSelectController controller = (CreateGameNicknameSelectController) this.guiApplication.getController((Scenes.CREATEGAMENICKNAMESELECT));
                controller.showInvalidUsername();
            }
            else{
                NicknameSelectController controller = (NicknameSelectController) this.guiApplication.getController((Scenes.NICKNAMESELECT));
                controller.showInvalidUsername();
            }
        });
    }
    @Override
    public void show_nickname_already_used() {
        callPlatformRunLater(() -> {
            if(guiApplication.getGameflow().isCreate()){
                CreateGameNicknameSelectController controller = (CreateGameNicknameSelectController) this.guiApplication.getController((Scenes.CREATEGAMENICKNAMESELECT));
                controller.initialize();
                controller.showNicknameAlreadyUsed();
            }
            else{
                NicknameSelectController controller = (NicknameSelectController) this.guiApplication.getController((Scenes.NICKNAMESELECT));
                controller.initialize();
                controller.showNicknameAlreadyUsed();
            }
        });
    }

    @Override
    public void show_empty_nickname() {
        callPlatformRunLater(() -> {
            if(guiApplication.getGameflow().isCreate()){
                CreateGameNicknameSelectController controller = (CreateGameNicknameSelectController) this.guiApplication.getController((Scenes.CREATEGAMENICKNAMESELECT));
                controller.showEmptyUsername();
            }
            else{
                NicknameSelectController controller = (NicknameSelectController) this.guiApplication.getController((Scenes.NICKNAMESELECT));
                controller.showEmptyUsername();
            }
        });
    }

    @Override
    public void show_no_lobby_available() {
        callPlatformRunLater(() -> {
            NicknameSelectController controller = (NicknameSelectController) this.guiApplication.getController((Scenes.NICKNAMESELECT));
            controller.showNoLobbyAvaiable();
        });
    }

    @Override
    public void show_color_not_available() {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.COLORSELECTOR);
            ColorSelector selector = (ColorSelector) this.guiApplication.getController(Scenes.COLORSELECTOR);
            selector.showColorNotAvailable();
        });
    }

    @Override
    public void show_insert_num_player() {
        callPlatformRunLater(() -> {
            CreateGameNicknameSelectController controller = (CreateGameNicknameSelectController) this.guiApplication.getController((Scenes.CREATEGAMENICKNAMESELECT));
            controller.initialize();
        });
    }

    @Override
    public void show_invalid_num_player() {
        callPlatformRunLater(() -> {
            CreateGameNicknameSelectController controller = (CreateGameNicknameSelectController) this.guiApplication.getController((Scenes.CREATEGAMENICKNAMESELECT));
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
            this.guiApplication.setActiveScene(Scenes.GAME);
            GameBoardController controller = (GameBoardController) this.guiApplication.getController(Scenes.GAME);
            controller.connectionError();
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
            this.guiApplication.setActiveScene(Scenes.ENDGAMESCREEN);
            EndGameScreenController selector = (EndGameScreenController) this.guiApplication.getController(Scenes.ENDGAMESCREEN);
            selector.rankings(rank,winner,winnerNick);
        });
    }

    @Override
    public void show_wrong_card_play() {

    }

    @Override
    public void show_joined_players(ArrayList<String> players, String current, int num) {
        callPlatformRunLater(() -> {
            this.guiApplication.setActiveScene(Scenes.LOBBYVIEW);
            LobbyViewController controller = (LobbyViewController) this.guiApplication.getController(Scenes.LOBBYVIEW);
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
