package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ColorSelector extends Generic implements EventHandler<ActionEvent> {
    private ArrayList<String> colors;

    @FXML
    public void Initialize(ArrayList<String> colors) {
        this.colors = colors;
        for(String color : colors) {
            switch (color){
                case "red": red.setOnAction(this);
                case "blue": blue.setOnAction(this);
                case "green": green.setOnAction(this);
                case "yellow": yellow.setOnAction(this);
            }
        }
    }
    @FXML
    private Button red;

    @FXML
    private Button green;

    @FXML
    private Button blue;

    @FXML
    private Button yellow;

    @Override
    public void handle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        if (clickedButton == red) {
            getInputReaderGUI().addString("red");
        } else if (clickedButton == green) {
            getInputReaderGUI().addString("green");
        } else if (clickedButton == blue) {
            getInputReaderGUI().addString("blue");
        } else if (clickedButton == yellow) {
            getInputReaderGUI().addString("yellow");
        }
    }
}
