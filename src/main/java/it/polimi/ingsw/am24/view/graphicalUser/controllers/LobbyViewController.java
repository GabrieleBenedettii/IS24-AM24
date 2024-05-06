package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class LobbyViewController extends Generic{
    @FXML
    private Text nickname;
    @FXML
    private Button readyButton;
    @FXML
    private Text gameID;

    public void setNickname(String nickname) {this.nickname.setText(nickname);}
    public void setGameID(int id) {this.gameID.setText("GameId: " + id);}
    public void readyButtonClicked(ActionEvent event) throws IOException {
        getInputReaderGUI().addString("pressed");
        //todo : sound for button press
    }
    public void setVisibility(boolean visibility) { readyButton.setVisible(visibility);}
}
