package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;

public class EndGameScreenController extends Generic{

    @FXML
    private Label player0;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;

    public void rankings(HashMap<String, Integer> rank) {
        player0.setVisible(false);
        //player0.setTextFill(Color.YELLOW); -> mette in risalto il vincitore
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);

        Label tmp = null;
        int i = 0;
        for(String player : rank.keySet()){
            String p = "player" + i;
            tmp.setText(player + "   -->   " + rank.get(player) + " points");
            tmp.setVisible(true);
            i++;
        }
    }
}
