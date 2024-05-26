package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.view.graphicalUser.Sound;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class CreateGameNicknameSelectController extends Generic{

    @FXML
    private Text errorText1;
    @FXML
    private Text errorText2;
    @FXML
    public Text errorText2;
    @FXML
    private TextField nickNameTextField;
    @FXML
    private TextField playerNumberTextField;

    @FXML
    public void initialize() {
        errorText1.setVisible(false);
        errorText2.setVisible(false);
    }

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
                if(Integer.parseInt(playerNumberTextField.getText()) > 1 && Integer.parseInt(playerNumberTextField.getText())<5)
                    errorText2.setVisible(false);
            } catch (NumberFormatException e) {
                showInvalidNumberOfPlayers();
            }
        }
    }

    public void showEmptyUsername() {
        errorText1.setText("Nickname cannot be empty.");
        errorText1.setVisible(true);
    }

    public void showInvalidUsername() {
        errorText1.setText("Please enter a nickname without special characters or numbers");
        errorText1.setVisible(true);
        nickNameTextField.clear();
    }

    public void showInvalidNumberOfPlayers() {
        errorText2.setText("Invalid input. Please enter a number between 2 and 4.");
        errorText2.setVisible(true);
        playerNumberTextField.clear();
    }

    public void showNicknameAlreadyUsed() {
        errorText1.setText("Nickname already used.");
        errorText1.setVisible(true);
        playerNumberTextField.clear();
    }
}
