package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;

public class ColorSelector {
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private void initialize() {
        // Call a function to get strings
        List<String> strings = getStringsFromFunction();

        // Add strings to the choice box
        choiceBox.getItems().addAll(strings);

        // Set default selection
        choiceBox.getSelectionModel().selectFirst(); // You can set any default value
    }

    // Function to obtain strings
    private List<String> getStringsFromFunction() {
        return List.of("RED", "GREEN", "BLUE", "YELLOW");
    }
}
