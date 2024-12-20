package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.view.GUI.Sound;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
/**
 * Controller class for managing color selection in a graphical user interface (GUI).
 * Extends GUIController and implements EventHandler for handling button actions.
 * Handles initialization of color buttons based on provided colors list and responds
 * to user interaction events such as button clicks by adding selected color to input.
 */
public class ColorSelectorController extends GUIController implements EventHandler<ActionEvent> {
    @FXML
    public Text errorText;
    private ArrayList<String> colors;

    @FXML
    private HBox colorsContainer;

    /**
     * Initializes the controller with a list of available colors.
     * Initializes color buttons dynamically based on the provided colors list.
     *
     * @param colors List of available colors to display as buttons.
     */
    @FXML
    public void initialize(ArrayList<String> colors) {
        this.colors = colors;
        for (int i = 0; i < colors.size(); i++) {
            Button button = new Button(colors.get(i).toLowerCase());
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

    /**
     * Displays an error message indicating that the selected color is not available.
     * Clears the color selection UI to allow choosing a different color.
     */
    public void showColorNotAvailable() {
        errorText.setText("Color not available. Please choose a different color.");
        errorText.setVisible(true);
        colorsContainer.getChildren().clear();
    }

    /**
     * Handles button click events from color selection buttons.
     * Adds the selected color text to the input reader and plays a button click sound.
     *
     * @param event ActionEvent triggered by clicking a color button.
     */
    @Override
    public void handle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        getInputReaderGUI().addString(clickedButton.getText());
        Sound.playSound("button.wav");
    }
}
