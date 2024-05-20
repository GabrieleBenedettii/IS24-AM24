package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.view.graphicalUser.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateGameNicknameSelectController extends Generic{

    @FXML
    private TextField nickNameTextField;

    @FXML
    private TextField playerNumberTextField;

    public void actionEnter(ActionEvent e) throws IOException {
        if(!nickNameTextField.getText().isEmpty() && !playerNumberTextField.getText().isEmpty()) {
            getInputReaderGUI().addString(nickNameTextField.getText());
            int playerNumber = Integer.parseInt(playerNumberTextField.getText());
            getInputReaderGUI().addString(String.valueOf(playerNumber));
            Sound.playSound("buttonClick.mp3");
        }
    }
}
