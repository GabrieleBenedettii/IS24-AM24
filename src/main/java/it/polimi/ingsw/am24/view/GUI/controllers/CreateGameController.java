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
 * Controller class for handling the creation of a game in a graphical user interface (GUI).
 * Extends GUIController and manages user interactions related to entering a nickname and
 * selecting the number of players for the game.
 * Provides initialization of GUI elements, validation of user inputs, error handling,
 * and integration with input reading and sound playback functionalities.
 */
public class CreateGameController extends GUIController {

    @FXML
    private Button button;
    @FXML
    private Text errorText1;
    @FXML
    private Text errorText2;
    @FXML
    private TextField nickNameTextField;
    @FXML
    private TextField playerNumberTextField;

    /**
     * Initializes the controller, setting up GUI elements and hiding error messages.
     * Loads a custom font for the button text.
     */
    @FXML
    public void initialize() {
        Font customFont = Font.loadFont(Root.class.getResourceAsStream("view/fonts/Plain_Germanica.ttf"), 40);
        button.setFont(customFont);
        errorText1.setVisible(false);
        errorText2.setVisible(false);
    }

    /**
     * Handles the action triggered when the user enters input for nickname and player number.
     * Validates user input, adds the nickname and number of players to the input reader,
     * plays a button click sound, and shows error messages for invalid inputs.
     *
     * @throws IOException If there is an IO exception during input processing.
     */
    public void actionEnter() throws IOException {
        errorText1.setVisible(false);
        errorText2.setVisible(false);

        if(nickNameTextField.getText().isEmpty() || playerNumberTextField.getText().isEmpty()){
            errorText1.setText("text cannot be empty.");
            errorText1.setVisible(true);
        }
        else {
            try {
                getInputReaderGUI().addString(nickNameTextField.getText());
                getInputReaderGUI().addString(String.valueOf(Integer.parseInt(playerNumberTextField.getText())));
                Sound.playSound("button.wav");
                if(Integer.parseInt(playerNumberTextField.getText()) > 1 && Integer.parseInt(playerNumberTextField.getText())<5)
                    errorText2.setVisible(false);
            } catch (NumberFormatException e) {
                showInvalidNumberOfPlayers();
            }
        }
    }

    /**
     * Displays an error message indicating that the nickname cannot be empty.
     */
    public void showEmptyUsername() {
        errorText1.setText("Nickname cannot be empty.");
        errorText1.setVisible(true);
    }

    /**
     * Displays an error message indicating that the nickname contains special characters or numbers.
     * Clears the nickname text field.
     */
    public void showInvalidUsername() {
        errorText1.setText("Please enter a nickname without special characters or numbers");
        errorText1.setVisible(true);
        nickNameTextField.clear();
    }

    /**
     * Displays an error message indicating that the entered number of players is invalid.
     * Clears the player number text field.
     */
    public void showInvalidNumberOfPlayers() {
        errorText2.setText("Invalid input. Please enter a number between 2 and 4.");
        errorText2.setVisible(true);
        playerNumberTextField.clear();
    }

    /**
     * Displays an error message indicating that the entered nickname is already used.
     * Clears the player number text field.
     */
    public void showNicknameAlreadyUsed() {
        errorText1.setText("Nickname already used.");
        errorText1.setVisible(true);
        playerNumberTextField.clear();
    }
}
