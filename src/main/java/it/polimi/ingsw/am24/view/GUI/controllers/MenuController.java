package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.view.GUI.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
/**
 * Controller class for the main menu UI.
 * Extends GUIController to handle user interactions and input.
 */
public class MenuController extends GUIController {
    /**
     * Pane element representing the sound icon in the menu.
     */
    @FXML
    Pane soundIcon;

    /**
     * Button for creating a new game.
     */
    @FXML
    Button button1;

    /**
     * Button for joining the first available game.
     */
    @FXML
    Button button2;

    /**
     * Initializes the MenuController.
     * Sets custom fonts for button1 and button2.
     */
    @FXML
    public void initialize() {
        Font customFont = Font.loadFont(Root.class.getResourceAsStream("view/fonts/Plain_Germanica.ttf"), 22);
        button1.setFont(customFont);
        button2.setFont(customFont);
    }

    /**
     * Action handler for creating a new game.
     * Adds "1" to the input reader and plays a sound.
     *
     * @param event Action event triggered by the button click.
     * @throws IOException If an I/O error occurs.
     */
    public void createNewGameAction(ActionEvent event) throws IOException {
        getInputReaderGUI().addString("1");
        Sound.playSound("createjoinsound.wav");
    }

    /**
     * Action handler for joining the first available game.
     * Adds "2" to the input reader and plays a sound.
     *
     * @param event Action event triggered by the button click.
     * @throws IOException If an I/O error occurs.
     */
    public void joinFirstAvailableGameAction(ActionEvent event) throws IOException {
        getInputReaderGUI().addString("2");
        Sound.playSound("createjoinsound.wav");
    }

    /**
     * Action handler for showing information about the game.
     * Displays an alert with game credits and plays a sound.
     *
     * @param event Mouse event triggered by clicking the info icon.
     */
    public void infoAction(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Developed by Belfiore Mattia, Benedetti Gabriele, Buccheri Giuseppe, Canepari Michele"
                        + "\n" + "Credits of the game CodexNaturalis to CranioCreations", ButtonType.CLOSE);
        alert.show();
        Sound.playSound("ding.wav");
    }

    /**
     * Action handler for toggling sound on/off.
     * Toggles the sound icon class and plays a button click sound.
     *
     * @param event Mouse event triggered by clicking the sound icon.
     */
    public void soundIconAction(MouseEvent event){
        if(event != null){
            Sound.play = !Sound.play;
        }
        if(Sound.play){
            soundIcon.getStyleClass().remove("soundOff");
            if(!soundIcon.getStyleClass().contains("soundOn")){
                soundIcon.getStyleClass().add("soundOn");
                Sound.playSound("button.wav");
            }
        }
        else{
            soundIcon.getStyleClass().remove("soundOn");
            if(!soundIcon.getStyleClass().contains("soundOff")){
                soundIcon.getStyleClass().add("soundOff");
            }
        }
    }
}
