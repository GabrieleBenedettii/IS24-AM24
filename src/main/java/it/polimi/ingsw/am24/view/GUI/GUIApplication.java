package it.polimi.ingsw.am24.view.GUI;

import it.polimi.ingsw.am24.view.Flow;
import it.polimi.ingsw.am24.view.GUI.controllers.GUIController;
import it.polimi.ingsw.am24.view.GUI.controllers.MenuController;
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
import java.util.ArrayList;

/**
 * GUIApplication class extends Application and manages the JavaFX application lifecycle and UI scenes.
 */
public class GUIApplication extends Application {

    private Flow gameflow;
    private Stage mainStage, secondaryStage;
    private StackPane rad;
    private ArrayList<SceneDesc> scene;

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the application, including game flow and loading scenes.
     *
     * @param primaryStage Primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        gameflow = new Flow(this, getParameters().getUnnamed().get(0));
        loadScenes();

        this.mainStage = primaryStage;
        this.mainStage.setTitle("Codex Naturalis");
        rad = new StackPane();
        //todo : aggiungere un suono di avvio
    }

    /**
     * Loads all scenes available for the game and initializes controllers.
     */
    private void loadScenes() {
        //Loads all the scenes available to be showed during the game
        scene = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GUIController g;
        for (int i = 0; i < Scenes.values().length; i++) {
            String name = Scenes.values()[i].getFxmlFile();
            loader = new FXMLLoader(Flow.class.getResource(name));
            try {
                root = loader.load();
                g = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.add(new SceneDesc(new Scene(root), Scenes.values()[i], g));
        }
    }

    /**
     * Sets the InputReaderGUI to all controllers in loaded scenes.
     *
     * @param inputReaderGUI InputReaderGUI instance to set.
     */
    public void setInputReaderGUItoAllControllers(InputReaderGUI inputReaderGUI) {
        for (SceneDesc s : scene) {
            s.setInputReaderGUI(inputReaderGUI);
        }
    }

    /**
     * Retrieves the controller associated with a specific scene.
     *
     * @param scene Scene enumeration value representing the scene.
     * @return GUIController associated with the specified scene.
     */
    public GUIController getController(Scenes scene) {
        int index = getSceneIndex(scene);
        if (index != -1) {
            return this.scene.get(getSceneIndex(scene)).getGeneric();
        }
        return null;
    }

    /**
     * Retrieves the index of a specific scene in the list of scenes.
     *
     * @param scene Scene enumeration value representing the scene.
     * @return Index of the scene in the list, or -1 if not found.
     */
    private int getSceneIndex(Scenes scene) {
        for (int i = 0; i < this.scene.size(); i++) {
            if (this.scene.get(i).getScenes().equals(scene))
                return i;
        }
        return -1;
    }

    /**
     * Creates a new window with the specified style.
     */
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

    /**
     * Sets the active scene in the application.
     *
     * @param scene Scene enumeration value representing the scene to set as active.
     */
    public void setActiveScene(Scenes scene){
        this.mainStage.setTitle("Codex Naturalis - "+scene.name());
        SceneDesc s = this.scene.get(getSceneIndex(scene));
        if (scene == Scenes.MENU) {
            this.mainStage.centerOnScreen();
            this.mainStage.setAlwaysOnTop(false);
            MenuController controller = (MenuController) s.getGeneric();
        }
        this.mainStage.setScene(s.getScene());
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.mainStage.setX((screenBounds.getWidth() - this.mainStage.getWidth()) / 2);
        this.mainStage.setY((screenBounds.getHeight() - this.mainStage.getHeight()) / 2);
        this.mainStage.show();

        mainStage.getIcons().add(new Image("it/polimi/ingsw/am24/images/favicon-1.png"));
    }

    /**
     * Retrieves the game flow object managing game interactions.
     *
     * @return Flow object managing the game flow.
     */
    public Flow getGameflow() {
        return gameflow;
    }
}
