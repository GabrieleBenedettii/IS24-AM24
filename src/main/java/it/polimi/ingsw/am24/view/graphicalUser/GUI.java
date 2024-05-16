package it.polimi.ingsw.am24.view.graphicalUser;

import it.polimi.ingsw.am24.constants.Constants;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.view.flow.UI;
import it.polimi.ingsw.am24.view.graphicalUser.controllers.ColorSelector;
import it.polimi.ingsw.am24.view.graphicalUser.controllers.InitialCardSelector;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

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

    }

    @Override
    public void show_menu() {

    }

    @Override
    public void show_table(GameView gameView, boolean forChoice) {

    }

    @Override
    public void show_start_table(GameView gameView) {

    }

    @Override
    public void show_lobby() {

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
    public void show_current_player(String nickname) {

    }

    @Override
    public void show_logo() {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(Scenes.LOGO));
        PauseTransition pause = new PauseTransition(Duration.seconds(Constants.logoTime));
        pause.setOnFinished(event -> {
            alreadyShowedPublisher = true;

            this.show_menuOptions();
        });
        pause.play();

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
    public void show_winner_and_rank(boolean winner, HashMap<String, Integer> rank) {

    }

    @Override
    public void show_wrong_card_play() {

    }

    @Override
    public void show_joined_players(ArrayList<String> player) {
        callPlatformRunLater(() -> this.guiApplication.setActiveScene(Scenes.LOBBYVIEW));
    }

    @Override
    public void show_message() {

    }

    @Override
    public void add_message(String message) {

    }

    @Override
    public void show_chosenNickname(String nickname) {

    }

    @Override
    public void show_network_type() {

    }
}
