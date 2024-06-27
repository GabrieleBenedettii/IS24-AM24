package it.polimi.ingsw.am24.view.GUI;

import it.polimi.ingsw.am24.view.GUI.controllers.GUIController;
import it.polimi.ingsw.am24.view.input.InputReaderGUI;
import javafx.scene.Scene;

/**
 * Represents a description of a scene in a graphical user interface (GUI) application.
 * This class encapsulates information about a specific scene, its corresponding enum value,
 * and the GUI controller responsible for managing it. It allows setting an input reader
 * specific to the GUI controller for user interaction handling.
 */
public class SceneDesc {
    private final Scene scene;
    private final Scenes scenes; // enum
    private final GUIController GUIController;

    /**
     * Constructs a SceneDesc object with the provided scene, enum value, and GUI controller.
     *
     * @param scene         The scene associated with this description.
     * @param scenes        The enum value representing the scene type.
     * @param GUIController The GUI controller managing interactions for this scene.
     */
    public SceneDesc(Scene scene, Scenes scenes, GUIController GUIController) {
        this.scene = scene;
        this.scenes = scenes;
        this.GUIController = GUIController;
    }

    /**
     * Retrieves the scene associated with this description.
     *
     * @return The Scene object.
     */
    public Scene getScene() {return scene;}

    /**
     * Retrieves the enum value representing the type of scene.
     *
     * @return The Scenes enum value.
     */
    public Scenes getScenes() {return scenes;}

    /**
     * Retrieves the GUI controller managing interactions for this scene.
     *
     * @return The GUIController object.
     */
    public GUIController getGeneric() {return GUIController;}

    /**
     * Sets the input reader specific to the GUI controller for user interaction handling.
     *
     * @param inputReaderGUI The InputReaderGUI object to set.
     */
    public void setInputReaderGUI(InputReaderGUI inputReaderGUI) {
        if(GUIController != null){
            GUIController.setInputReaderGUI(inputReaderGUI);
        }
    }
}
