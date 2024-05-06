package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NicknameSelectController extends Generic{
    @FXML
    private TextField nickname;

    public  void actionEnter(ActionEvent event) throws IOException {
        if(!nickname.getText().isEmpty()){
            getInputReaderGUI().addString(nickname.getText());
        }
    }
}
