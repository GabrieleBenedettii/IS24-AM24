package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.Placement;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class  GameBoardController extends Generic{

    @FXML private HBox playingHandContainer;
    @FXML private HBox hiddenGoalContainer;
    @FXML private VBox resourceCardsContainer;
    @FXML private VBox goldCardsContainer;
    @FXML private HBox commonGoalsContainer;
    @FXML private VBox rankingContainer;
    @FXML private Pane gameViewContainer;

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

        //GAME VIEW
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBorder(Border.stroke(Paint.valueOf("black")));

        GameCardView[][] matrix = gameView.getPlayerView().getBoard();
        ArrayList<Placement> order = gameView.getPlayerView().getPlaceOrder();
        double cellWidth = (double) 1100 /matrix.length;
        double cellHeight = 583.5/matrix[0].length;

        //adding all empty cells
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                StackPane stackPane = new StackPane();
                stackPane.setPrefSize(cellWidth, cellHeight);
                gridPane.add(stackPane, j, i);
            }
        }

        //adding placed cards in order
        for(Placement placement : order) {
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(cellWidth, cellHeight);
            Pane pane = new Pane();
            pane.setPrefSize(cellWidth, cellHeight);

            image = new Image(HelloApplication.class.getResource("images/front/"+placement.getCard().getCardId()+".jpg").toString());
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            imageView.setFitWidth(cellWidth/0.78);
            imageView.setLayoutX(-0.11*(cellWidth/0.75));
            imageView.setLayoutY(-0.2*(cellHeight/0.6));

            pane.getChildren().add(imageView);
            stackPane.getChildren().add(pane);
            gridPane.add(stackPane, placement.getY(), placement.getX());
        }

        gameViewContainer.getChildren().add(gridPane);
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
