package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.view.graphicalUser.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class SecretGoalCardSelector extends Generic{
    @FXML
    public void Initialize(ArrayList<GameCardView> views) {
        String path0 = "images/front/"+views.get(0).getCardId()+".jpg";
        String path1 = "images/front/"+views.get(1).getCardId()+".jpg";

        //todo fix
        Image image0 = new Image(HelloApplication.class.getResource(path0).toString());
        Image image1 = new Image(HelloApplication.class.getResource(path1).toString());

        goalCard0.setImage(image0);
        goalCard1.setImage(image1);
    }

    @FXML
    private ImageView goalCard0;

    @FXML
    private ImageView goalCard1;

    public void chooseFirst(ActionEvent actionEvent) {
        getInputReaderGUI().addString("0");
        Sound.playSound("button.wav");
    }
    public void chooseSecond(ActionEvent actionEvent) {
        getInputReaderGUI().addString("1");
        Sound.playSound("button.wav");
    }
}
