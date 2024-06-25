package it.polimi.ingsw.am24.view.GUI;

import it.polimi.ingsw.am24.view.GUI.controllers.GUIController;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;
import javafx.scene.Scene;

public class SceneDesc {
    private final Scene scene;
    private final Scenes scenes; // enum
    private final GUIController GUIController;

    public SceneDesc(Scene scene, Scenes scenes, GUIController GUIController) {
        this.scene = scene;
        this.scenes = scenes;
        this.GUIController = GUIController;
    }

    public Scene getScene() {return scene;}
    public Scenes getScenes() {return scenes;}
    public GUIController getGeneric() {return GUIController;}
    public void setInputReaderGUI(InputReaderGUI inputReaderGUI) {
        if(GUIController != null){
            GUIController.setInputReaderGUI(inputReaderGUI);
        }
    }
}
