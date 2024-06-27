package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.view.GUI.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Controller class for the initial card selection screen.
 * Extends GUIController to handle user interactions and input.
 */
public class InitialCardSelectorController extends GUIController {

    /**
     * Initializes the initial card selection screen with given card views.
     *
     * @param views ArrayList of GameCardView objects representing the cards to display.
     */
    @FXML
    public void Initialize(ArrayList<GameCardView> views) {
        String path0 = "images/front/"+views.get(0).getCardId()+".png";
        String path1 = "images/back/"+views.get(1).getCardId()+".png";

        //todo fix
        Image image0 = new Image(Root.class.getResource(path0).toString());
        Image image1 = new Image(Root.class.getResource(path1).toString());

        initialCard0.setImage(image0);
        initialCard1.setImage(image1);
    }

    /**
     * ImageView for displaying the first initial card.
     */
    @FXML
    private ImageView initialCard0;

    /**
     * ImageView for displaying the second initial card.
     */
    @FXML
    private ImageView initialCard1;

    /**
     * Handles the action when the user chooses the front side of the card.
     * Adds "0" to the input reader GUI and plays a button sound.
     *
     * @param actionEvent The ActionEvent triggered by choosing the front side.
     */
    public void chooseFront(ActionEvent actionEvent) {
        getInputReaderGUI().addString("0");
        Sound.playSound("button.wav");
    }

    /**
     * Handles the action when the user chooses the back side of the card.
     * Adds "1" to the input reader GUI and plays a button sound.
     *
     * @param actionEvent The ActionEvent triggered by choosing the back side.
     */
    public void chooseBack(ActionEvent actionEvent) {
        getInputReaderGUI().addString("1");
        Sound.playSound("button.wav");
    }
}
