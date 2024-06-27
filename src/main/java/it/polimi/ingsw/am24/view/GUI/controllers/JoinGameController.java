package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.view.GUI.Sound;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Controller class for joining a game lobby.
 * Extends GUIController to handle user interactions and input.
 */
public class JoinGameController extends GUIController {

    /**
     * Button used to join the game lobby.
     */
    @FXML
    private Button button;

    /**
     * TextField for entering the nickname.
     */
    @FXML
    private TextField nickname;

    /**
     * Text element to display error messages.
     */
    @FXML
    private Text errorText;

    /**
     * Initializes the JoinGameController.
     * Sets a custom font for the button and resets the nickname field.
     */
    @FXML
    public void initialize() {
        Font customFont = Font.loadFont(Root.class.getResourceAsStream("view/fonts/Plain_Germanica.ttf"), 40);
        button.setFont(customFont);
        resetNicknameField();
    }

    /**
     * Handles the action when the user presses Enter to join the game lobby.
     * Adds the entered nickname to the input reader GUI and plays a button sound.
     *
     * @throws IOException If an I/O exception occurs.
     */
    public void actionEnter() throws IOException {
        getInputReaderGUI().addString(nickname.getText());
        Sound.playSound("button.wav");
    }

    /**
     * Displays an error message when the nickname field is empty.
     */
    public void showEmptyUsername() {
        errorText.setText("Nickname cannot be empty.");
        errorText.setVisible(true);
    }

    /**
     * Displays an error message when the entered nickname contains special characters.
     */
    public void showInvalidUsername() {
        errorText.setText("Please enter a nickname without special characters");
        errorText.setVisible(true);
    }

    /**
     * Displays an error message when the entered nickname is already used.
     */
    public void showNicknameAlreadyUsed() {
        errorText.setText("Nickname already used.");
        errorText.setVisible(true);
    }

    /**
     * Clears the nickname field.
     */
    public void resetNicknameField() {
        nickname.clear();
    }

    /**
     * Displays a message when no lobby is available and returns to the menu.
     */
    public void showNoLobbyAvaiable() {
        errorText.setText("No lobby available\nReturning to menu...");
        errorText.setVisible(true);
    }
}
