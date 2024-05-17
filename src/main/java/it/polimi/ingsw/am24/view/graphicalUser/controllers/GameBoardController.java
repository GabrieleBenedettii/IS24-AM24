package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.modelView.GameView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;



public class  GameBoardController extends Generic{

    @FXML private HBox playingHandContainer;
    @FXML private HBox hiddenGoalContainer;
    @FXML private VBox resourceCardsContainer;
    @FXML private VBox goldCardsContainer;
    @FXML private HBox commonGoalsContainer;
    @FXML private VBox rankingContainer;

    private GridPane grid;
    private GameView gameView;
    @FXML
    public void initialize(GameView gameView) {
        this.gameView = gameView;
        int id;
        Image image;
        ImageView imageView;

        //PLAYING HAND
        for (int i = 0; i < gameView.getPlayerView().getPlayerHand().size(); i++) {
            id = gameView.getPlayerView().getPlayerHand().get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            imageView = new ImageView(image);
            setImageOptionsH(imageView,200,25);

            playingHandContainer.getChildren().add(imageView);
            HBox.setHgrow(imageView, Priority.ALWAYS);
            imageView.setFitHeight(Double.MAX_VALUE);
        }

        //HIDDEN GOAL
        id = gameView.getPlayerView().getSecretCard().getCardId(); //secret goal
        image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
        imageView = new ImageView(image);
        setImageOptionsH(imageView,200,25);

        hiddenGoalContainer.getChildren().add(imageView);
        HBox.setHgrow(imageView, Priority.ALWAYS);
        imageView.setFitHeight(Double.MAX_VALUE);

        //GOLD CARDS ON TABLE
        //int idG =gameView.getCommon().getGoldDeck();
        image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());      //TODO create ID for gold and resource deck
        imageView = new ImageView(image);
        setImageOptionsV(imageView);
        goldCardsContainer.getChildren().add(imageView);
        VBox.setVgrow(imageView, Priority.ALWAYS);
        imageView.setFitHeight(Double.MAX_VALUE);

        for (int i = 0; i < gameView.getCommon().getGoldCards().size(); i++) {
            id = gameView.getCommon().getGoldCards().get(i).getCardId(); //
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            imageView = new ImageView(image);
            imageView.setCursor(Cursor.HAND);
            setImageOptionsV(imageView);
            goldCardsContainer.getChildren().add(imageView);
            VBox.setVgrow(imageView, Priority.ALWAYS);
            imageView.setFitHeight(Double.MAX_VALUE);
        }

        //RESOURCE CARDS ON TABLE
        //int idR =gameView.getCommon().getResDeck();
        image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
        imageView = new ImageView(image);
        setImageOptionsV(imageView);
        resourceCardsContainer.getChildren().add(imageView);
        VBox.setVgrow(imageView, Priority.ALWAYS);
        imageView.setFitHeight(Double.MAX_VALUE);

        for (int i = 0; i < gameView.getCommon().getResourceCards().size(); i++) {
            id = gameView.getCommon().getResourceCards().get(i).getCardId();
            System.out.println(id);
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            imageView = new ImageView(image);imageView.setCursor(Cursor.HAND);
            setImageOptionsV(imageView);
            resourceCardsContainer.getChildren().add(imageView);
            VBox.setVgrow(imageView, Priority.ALWAYS);
            imageView.setFitHeight(Double.MAX_VALUE);
        }

        //COMMON GOALS CARDS
        for (int i = 0; i < gameView.getCommon().getGoals().size(); i++) {
            id = gameView.getCommon().getGoals().get(i).getCardId(); //
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            imageView = new ImageView(image);
            imageView.setCursor(Cursor.HAND);
            setImageOptionsH(imageView, 150, 10);

            commonGoalsContainer.getChildren().add(imageView);
            HBox.setHgrow(imageView, Priority.ALWAYS);
            imageView.setFitHeight(Double.MAX_VALUE);
        }
    }

    private void setImageOptionsH(ImageView imageView, int width, int margin) {
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        imageView.setFitWidth(width);
        HBox.setMargin(imageView, new Insets(0,margin,0,margin));
    }

    private void setImageOptionsV(ImageView imageView) {
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        imageView.setFitWidth(150);
        VBox.setMargin(imageView, new Insets(10,10,10,10));
    }
}
