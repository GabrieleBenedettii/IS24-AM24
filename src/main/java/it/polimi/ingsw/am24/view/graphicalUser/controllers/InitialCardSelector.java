package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.modelView.GameCardView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;

public class InitialCardSelector extends Generic {
    @FXML
    public void Initialize(ArrayList<GameCardView> views) {
        //todo check the correct loading
        String path0 = "/images/front/"+views.get(0).getCardId()+".jpg";
        String path1 = "/images/back/"+views.get(1).getCardId()+".jpg";

        URL url0 = getClass().getResource(path0);
        URL url1 = getClass().getResource(path1);

        if (url0 != null && url1 != null) {
            Image image0 = new Image(url0.toExternalForm());
            Image image1 = new Image(url1.toExternalForm());

            initialCard0.setImage(image0);
            initialCard1.setImage(image1);
        } else {
            System.out.println("Image resource not found");
        }
    }

    @FXML
    private ImageView initialCard0;

    @FXML
    private ImageView initialCard1;

    public void chooseFront(ActionEvent actionEvent) {
        getInputReaderGUI().addString("0");
    }
    public void chooseBack(ActionEvent actionEvent) {
        getInputReaderGUI().addString("1");
    }
}
