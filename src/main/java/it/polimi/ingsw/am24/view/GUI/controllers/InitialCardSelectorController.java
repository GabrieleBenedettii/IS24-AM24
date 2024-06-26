package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.view.GUI.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class InitialCardSelectorController extends GUIController {
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

    @FXML
    private ImageView initialCard0;

    @FXML
    private ImageView initialCard1;

    public void chooseFront(ActionEvent actionEvent) {
        getInputReaderGUI().addString("0");
        Sound.playSound("button.wav");
    }
    public void chooseBack(ActionEvent actionEvent) {
        getInputReaderGUI().addString("1");
        Sound.playSound("button.wav");
    }
}
