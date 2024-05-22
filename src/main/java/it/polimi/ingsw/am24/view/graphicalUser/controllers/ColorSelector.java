package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.view.graphicalUser.Sound;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class ColorSelector extends Generic implements EventHandler<ActionEvent> {
    private ArrayList<String> colors;

    @FXML
    private HBox colorsContainer;
    @FXML
    public void initialize(ArrayList<String> colors) {
        this.colors = colors;
        for (int i = 0; i < colors.size(); i++) {
            Button button = new Button(colors.get(i));
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMaxHeight(Double.MAX_VALUE);
            button.getStyleClass().add(colors.get(i).toLowerCase());
            button.setOnAction(this);
            button.setCursor(Cursor.HAND);
            button.setMinWidth(colorsContainer.getPrefWidth()/colors.size());

            colorsContainer.getChildren().add(button);

            HBox.setHgrow(button, Priority.ALWAYS);
            button.setPrefHeight(150);
        }
    }

    @Override
    public void handle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        getInputReaderGUI().addString(clickedButton.getText());
        Sound.playSound("button.wav");
    }
}
