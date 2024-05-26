package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.view.graphicalUser.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;

public class MenuController extends Generic{
    @FXML
    Pane soundIcon;
    @FXML
    Button button1;
    @FXML
    Button button2;

    @FXML
    public void initialize() {
        Font customFont = Font.loadFont(HelloApplication.class.getResourceAsStream("view/fonts/Plain_Germanica.ttf"), 22);
        button1.setFont(customFont);
        button2.setFont(customFont);
    }

    public void createNewGameAction(ActionEvent event) throws IOException {
        getInputReaderGUI().addString("1");
        Sound.playSound("createjoinsound.wav");
    }

    public void joinFirstAvailableGameAction(ActionEvent event) throws IOException {
        getInputReaderGUI().addString("2");
        Sound.playSound("createjoinsound.wav");
    }

    public void infoAction(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Developed by Belfiore Mattia, Benedetti Gabriele, Buccheri Giuseppe, Canepari Michele"
                        + "\n" + "Credits of the game CodexNaturalis to CranioCreations");
        alert.show();
        Sound.playSound("buttonClick");
    }

    public void soundIconAction(MouseEvent event){
//        if(event != null){
//            Sound.play = !Sound.play;
//        }
//        if(Sound.play){
//            soundIcon.getStyleClass().remove("soundOFF");
//            if(!soundIcon.getStyleClass().contains("soundON")){
//                soundIcon.getStyleClass().add("soundON");
//                Sound.playSound("buttonClick.mp3");
//            }
//        }
//        else{
//            soundIcon.getStyleClass().remove("soundON");
//            if(!soundIcon.getStyleClass().contains("soundOFF")){
//                soundIcon.getStyleClass().add("soundOFF");
//            }
//        }
    }
}
