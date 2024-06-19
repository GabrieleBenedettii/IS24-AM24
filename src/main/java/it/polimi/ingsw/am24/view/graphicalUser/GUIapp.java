package it.polimi.ingsw.am24.view.graphicalUser;

import it.polimi.ingsw.am24.view.GameFlow;
import it.polimi.ingsw.am24.view.graphicalUser.controllers.Generic;
import it.polimi.ingsw.am24.view.graphicalUser.controllers.MenuController;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class GUIapp extends Application {

    private GameFlow gameflow;
    private Stage mainStage, secondaryStage;
    private StackPane rad;
    private ArrayList<SceneDesc> scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        gameflow = new GameFlow(this, getParameters().getUnnamed().get(0));
        loadScenes();

        this.mainStage = primaryStage;
        this.mainStage.setTitle("Codex Naturalis");
        rad = new StackPane();
        //todo : aggiungere un suono di avvio
    }
    private void loadScenes() {
        //Loads all the scenes available to be showed during the game
        scene = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        Generic g;
        for (int i = 0; i < Scenes.values().length; i++) {
            String name = Scenes.values()[i].getFxmlFile();
            loader = new FXMLLoader(GameFlow.class.getResource(name));
            try {
                root = loader.load();
                g = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.add(new SceneDesc(new Scene(root), Scenes.values()[i], g));
        }
    }
    public void setInputReaderGUItoAllControllers(InputReaderGUI inputReaderGUI) {
        for (SceneDesc s : scene) {
            s.setInputReaderGUI(inputReaderGUI);
        }
    }
    public Generic getController(Scenes scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return this.scene.get(getSceneIndex(scene)).getGeneric();
        }
        return null;
    }

    private int getSceneIndex(Scenes scene) {
        for (int i = 0; i < this.scene.size(); i++) {
            if (this.scene.get(i).getScenes().equals(scene))
                return i;
        }
        return -1;
    }

    public void createNewWindowWithStyle() {
        // Crea una nuova finestra con lo stile desiderato
        Stage newStage = new Stage();

        // Copia la scena dalla finestra precedente
        newStage.setScene(this.mainStage.getScene());

        //Blocca fullscreen e resize
        newStage.setResizable(false);

        // Mostra la nuova finestra
        newStage.show();

        // Chiudi la finestra precedente
        this.mainStage.close();

        // Imposta la nuova finestra come primaryStage
        this.mainStage = newStage;
        this.mainStage.centerOnScreen();
        this.mainStage.setAlwaysOnTop(true);

        this.mainStage.setOnCloseRequest(event -> {
            System.out.println("Closing all");

            System.exit(1);
        });
    }

    public void setActiveScene(Scenes scene){
        this.mainStage.setTitle("Codex Naturalis - "+scene.name());
        SceneDesc s = this.scene.get(getSceneIndex(scene));
        switch (scene) {
            case LOGO -> {
                this.mainStage.initStyle(StageStyle.UNDECORATED);
                this.mainStage.setAlwaysOnTop(true);
                this.mainStage.centerOnScreen();
            }
            case MENU -> {
                this.mainStage.centerOnScreen();
                this.mainStage.setAlwaysOnTop(false);
                MenuController controller = (MenuController) s.getGeneric();
            }
        }
        this.mainStage.setScene(s.getScene());
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.mainStage.setX((screenBounds.getWidth() - this.mainStage.getWidth()) / 2);
        this.mainStage.setY((screenBounds.getHeight() - this.mainStage.getHeight()) / 2);
        this.mainStage.show();

        mainStage.getIcons().add(new Image("it/polimi/ingsw/am24/images/favicon-1.png"));
    }

    public GameFlow getGameflow() {
        return gameflow;
    }
}
