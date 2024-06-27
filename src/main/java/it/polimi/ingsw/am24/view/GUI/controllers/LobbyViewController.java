package it.polimi.ingsw.am24.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Controller class for displaying players in a lobby.
 * Extends GUIController to handle user interactions and input.
 */
public class LobbyViewController extends GUIController {

    /**
     * Text element to display the label for players in the lobby.
     */
    @FXML
    private Text label;

    /**
     * HBox container to display players in the lobby.
     */
    @FXML
    private HBox playersInLobby;

    /**
     * Set to keep track of players displayed in the lobby.
     */
    private Set<String> playersSet = new HashSet<>();

    /**
     * Initializes the LobbyViewController.
     * Sets the label text for players in the lobby and adds players to the UI.
     *
     * @param players List of players in the lobby.
     * @param current Current player's nickname.
     * @param num Maximum number of players allowed in the lobby.
     */
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
