package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.view.GUI.Sound;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class NicknameSelectController extends GUIController {

    @FXML
    private Button button;
    @FXML
    private TextField nickname;
    @FXML
    private Text errorText;
    @FXML
    public void initialize() {
        Font customFont = Font.loadFont(Root.class.getResourceAsStream("view/fonts/Plain_Germanica.ttf"), 40);
        button.setFont(customFont);
        resetNicknameField();
    }

    public void actionEnter() throws IOException {
        getInputReaderGUI().addString(nickname.getText());
        Sound.playSound("button.wav");
    }
    public void showEmptyUsername() {
        errorText.setText("Nickname cannot be empty.");
        errorText.setVisible(true);
    }

    public void showInvalidUsername() {
        errorText.setText("Please enter a nickname without special characters");
        errorText.setVisible(true);
    }

    public void showNicknameAlreadyUsed() {
        errorText.setText("Nickname already used.");
        errorText.setVisible(true);
    }

    public void resetNicknameField() {
        nickname.clear();
    }

    public void showNoLobbyAvaiable() {
        errorText.setText("No lobby available\nReturning to menu...");
        errorText.setVisible(true);
    }
}
