package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LobbyViewController extends Generic{
    @FXML
    private Text label;
    @FXML
    private HBox playersInLobby;
    private Set<String> playersSet = new HashSet<>();
    @FXML
    public void initialize(ArrayList<String> players, String current, int num){
        label.setText("Players in lobby ["+players.size()+"/"+num+"] :");
        for (String player : players) {
            if (!playersSet.contains(player) || !playersSet.contains(player+"(you)")) {
                if (player.equals(current))
                    player+="(you)";
                Text text = new Text(player);
                text.setStyle("-fx-font-size:30; -fx-font-family: 'Muli'; -fx-font-weight: bold;");
                playersInLobby.getChildren().add(text);
                playersSet.add(player);
            }
        }
    }
}
