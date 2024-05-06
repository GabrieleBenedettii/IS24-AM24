package it.polimi.ingsw.am24.view.graphicalUser;

import it.polimi.ingsw.am24.view.graphicalUser.controllers.Generic;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;
import javafx.scene.Scene;

public class SceneDesc {
    private Scene scene;
    private Scenes scenes; // enum
    private Generic generic;
    public SceneDesc(Scene scene, Scenes scenes, Generic generic) {
        this.scene = scene;
        this.scenes = scenes;
        this.generic = generic;
    }
    public Scene getScene() {return scene;}
    public Scenes getScenes() {return scenes;}
    public Generic getGeneric() {return generic;}
    public void setInputReaderGUI(InputReaderGUI inputReaderGUI) {
        if(generic != null){
            generic.setInputReaderGUI(inputReaderGUI);
        }
    }
}
