package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.view.graphicalUser.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuController extends Generic{

    @FXML
    Pane soundIcon;

    public void createNewGameAction(ActionEvent event) throws IOException {
        getInputReaderGUI().addString("0");
        Sound.playSound("buttonClick");
    }
    public void joinFirstAvailableGameAction(ActionEvent event) throws IOException {
        getInputReaderGUI().addString("1");
        Sound.playSound("buttonClick");
    }
    public void infoAction(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Developed by Belfiore Mattia, Benedetti Gabriele, Buccheri Giuseppe, Canepari Michele"
                        + "\n" + "Credits of the game CodexNaturalis to CranioCreations");
        alert.show();
        Sound.playSound("buttonClick");
    }
    public void soundIconAction(MouseEvent event){
        if(event != null){
            Sound.play = !Sound.play;
        }
        //todo: change true and false with sound.play and !sound.play
        if(Sound.play){
            soundIcon.getStyleClass().remove("soundOFF");
            if(!soundIcon.getStyleClass().contains("soundON")){
                soundIcon.getStyleClass().add("soundON");
                Sound.playSound("buttonClick");
            }
        }
        else{
            soundIcon.getStyleClass().remove("soundON");
            if(!soundIcon.getStyleClass().contains("soundOFF")){
                soundIcon.getStyleClass().add("soundOFF");
            }
        }
    }
}
