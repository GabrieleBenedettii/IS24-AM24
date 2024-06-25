package it.polimi.ingsw.am24.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EndGameController extends GUIController {
    @FXML
    private Label resultMessage;
    @FXML
    private Label player0;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;

    public void rankings(HashMap<String, Integer> rank,Boolean winner,String winnerNick) {
        Label[] playerLabels = {player0, player1, player2, player3};
        for (Label label : playerLabels) {
            label.setVisible(false);
        }

        Map<String, Integer> sortedRank = rank.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        int i = 0;
        for(String player : sortedRank.keySet()){
            playerLabels[i].setText(player + "   :   " + sortedRank.get(player) + " points");
            playerLabels[i].setVisible(true);
            i++;
        }

        if (winner) {
            resultMessage.setText("Congratulations! You won!");
        } else {
            resultMessage.setText("Game Over, the winner is " + winnerNick+ " !");
        }
    }
}
