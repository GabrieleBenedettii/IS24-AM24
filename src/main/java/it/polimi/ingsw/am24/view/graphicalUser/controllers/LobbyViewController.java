package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LobbyViewController extends Generic{

    @FXML
    private HBox playersInLobby;
    private Set<String> playersSet = new HashSet<>();
    @FXML
    public void initialize(ArrayList<String> players){
        for (String player : players) {
            if (!playersSet.contains(player)) {
                Text text = new Text(player);
                text.setStyle("-fx-font-size:30; -fx-font-family: 'Muli'; -fx-font-weight: bold;");
                playersInLobby.getChildren().add(text);
                playersSet.add(player);
            }
        }
    }
}
