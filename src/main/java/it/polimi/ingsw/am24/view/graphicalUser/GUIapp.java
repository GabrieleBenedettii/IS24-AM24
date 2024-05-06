package it.polimi.ingsw.am24.view.graphicalUser;

import it.polimi.ingsw.am24.view.GameFlow;
import it.polimi.ingsw.am24.view.graphicalUser.controllers.Generic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
        gameflow = new GameFlow("RMI");
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
            loader = new FXMLLoader(getClass().getResource(Scenes.values()[i].getFxmlFile()));
            try {
                root = loader.load();
                g = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.add(new SceneDesc(new Scene(root), Scenes.values()[i], g));
        }
    }

    public void setActiveScene(Scenes scene){}
}
