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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

import java.util.ArrayList;
import it.polimi.ingsw.am24.constants.Constants;

public class  GameBoardController extends Generic{

    @FXML private HBox playingHandContainer;
    @FXML private HBox hiddenGoalContainer;
    @FXML private VBox resourceCardsContainer;
    @FXML private VBox goldCardsContainer;
    @FXML private HBox commonGoalsContainer;
    @FXML private VBox rankingContainer;
    @FXML private Pane gameViewContainer;

    private GameView gameView;
    private Image image;
    private ImageView imageView;
    private ImageView clickedImageView;
    private int id;

    @FXML
    public void beginTurn(GameView gameView) {
        this.gameView = gameView;

        //PLAYING HAND
        drawGameHand(true);

        //HIDDEN
        drawHiddenGoal();

        //GOLD CARDS ON TABLE
        drawGoldCardsTable(false);

        //RESOURCE CARDS ON TABLE
        drawResourceCardsTable(false);

        //COMMON GOALS CARDS
        //drawCommonGoals();

        //GAME BOARD
        drawGameBoard(true);
    }

    @FXML
    public void beginDraw(GameView gameView) {
        this.gameView = gameView;

        //PLAYING HAND
        drawGameHand(false);

        //HIDDEN
        drawHiddenGoal();

        //GOLD CARDS ON TABLE
        drawGoldCardsTable(true);

        //RESOURCE CARDS ON TABLE
        drawResourceCardsTable(true);

        //COMMON GOALS CARDS
        //drawCommonGoals();

        //GAME BOARD
        drawGameBoard(false);
    }

    @FXML
    public void hiddenGoalChoice(GameView gameView){
        this.gameView = gameView;

        //PLAYING HAND
        drawGameHand(false);

        //GOLD CARDS ON TABLE
        drawGoldCardsTable(false);

        //RESOURCE CARDS ON TABLE
        drawResourceCardsTable(false);

        //COMMON GOALS CARDS
        drawCommonGoals();
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

    private void drawGameBoard(boolean clickable) {
        gameViewContainer.getChildren().clear();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        int firstRow = gameView.getPlayerView().getBoard().length;
        int lastRow = 0;
        int firstColumn = gameView.getPlayerView().getBoard()[0].length;
        int lastColumn = 0;
        for(int r = 0; r < gameView.getPlayerView().getBoard().length; r++){
            for(int c = 0; c < gameView.getPlayerView().getBoard()[0].length; c++){
                if(gameView.getPlayerView().getBoard()[r][c] != null){
                    if(r <= firstRow){
                        firstRow = r;
                    }
                    if(r >= lastRow){
                        lastRow = r;
                    }
                    if(c <= firstColumn){
                        firstColumn = c;
                    }
                    if(c >= lastColumn){
                        lastColumn = c;
                    }
                }
            }
        }
        if(firstRow > 1){
            firstRow -= 2;
        }
        if(lastRow < gameView.getPlayerView().getBoard().length - 2){
            lastRow += 2;
        }
        if(firstColumn > 1){
            firstColumn -= 2;
        }
        if(lastColumn < gameView.getPlayerView().getBoard()[0].length - 2){
            lastColumn += 2;
        }

        ArrayList<Placement> order = gameView.getPlayerView().getPlaceOrder();
        double size = Math.max(lastRow-firstRow+1, lastColumn-firstColumn+1);
        double cellWidth = (double) 1100/size;
        double cellHeight = 583.5/size;

        //adding all empty cells
        for (int i = firstRow; i <= lastRow; i++) {
            for (int j = firstColumn; j <= lastColumn; j++) {
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

            System.out.println(placement.getCard().getCardId());
            image = new Image(HelloApplication.class.getResource("images/front/"+placement.getCard().getCardId()+".jpg").toString());
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            imageView.setFitWidth(cellWidth/0.78);
            imageView.setLayoutX(-0.11*(cellWidth/0.78));
            imageView.setLayoutY(-0.2*(cellHeight/0.6));

            pane.getChildren().add(imageView);
            stackPane.getChildren().add(pane);
            gridPane.add(stackPane, placement.getY(), placement.getX());
        }

        //adding all possible placement
        boolean[][] pp = gameView.getPlayerView().getPossiblePlacements();
        for (int i = 0; i < pp.length; i++) {
            for (int j = 0; j < pp[i].length; j++) {
                if(pp[i][j]) {
                    StackPane stackPane = new StackPane();
                    stackPane.setPrefSize(cellWidth, cellHeight);
                    Pane pane = new Pane();
                    pane.setPrefSize(cellWidth, cellHeight);

                    Rectangle card = new Rectangle(cellWidth / 0.78, cellHeight / 0.6);
                    card.setId(i+"-"+j);
                    card.setFill(Color.GRAY);
                    card.setOpacity(0.2);
                    card.setTranslateX(-0.11 * (cellWidth / 0.78));
                    card.setTranslateY(-0.2 * (cellHeight / 0.6));

                    if(clickable) {
                        card.setCursor(Cursor.HAND);
                        card.setOnMouseClicked(event -> {
                            if(clickedImageView != null) {
                                String[] coordinates = card.getId().split("-");
                                pane.getChildren().clear();
                                clickedImageView.setPreserveRatio(true);
                                clickedImageView.setSmooth(true);
                                clickedImageView.setCache(true);

                                clickedImageView.setFitWidth(cellWidth/0.78);
                                clickedImageView.setLayoutX(-0.11*(cellWidth/0.78));
                                clickedImageView.setLayoutY(-0.2*(cellHeight/0.6));

                                pane.getChildren().add(clickedImageView);
                                getInputReaderGUI().addString("/play " + clickedImageView.getId() + " " + "front" + " " + coordinates[0] + " " + coordinates[1]);
                            }
                        });
                    }

                    pane.getChildren().add(card);
                    stackPane.getChildren().add(pane);
                    gridPane.add(stackPane, j, i);
                }
            }
        }

        gameViewContainer.getChildren().add(gridPane);
    }


    private void drawGameHand(boolean clickable){
        playingHandContainer.getChildren().clear();
        for (int i = 0; i < gameView.getPlayerView().getPlayerHand().size(); i++) {
            id = gameView.getPlayerView().getPlayerHand().get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            ImageView iw = new ImageView(image);
            iw.setId(""+i);
            setImageOptionsH(iw,200,25);

            if(clickable) {
                iw.getStyleClass().add("clickableCard");
                iw.setCursor(Cursor.HAND);
                iw.setOnMouseClicked(event -> {
                    clearBorders(iw.getParent(), iw);
                    iw.setOpacity(0.5);
                    clickedImageView = iw;
                });
            }

            playingHandContainer.getChildren().add(iw);
            HBox.setHgrow(iw, Priority.ALWAYS);
            iw.setFitHeight(Double.MAX_VALUE);
        }
    }

    private void clearBorders(Parent parent, Node node) {
        if (parent instanceof HBox) {
            HBox hbox = (HBox) parent;
            for (Node n : hbox.getChildren()) {
                if (node instanceof ImageView && !n.equals(node)) {
                    n.setOpacity(1);
                }
            }
        }
    }

    private void drawCommonGoals() {
        for (int i = 0; i < gameView.getCommon().getGoals().size(); i++) {
            id = gameView.getCommon().getGoals().get(i).getCardId(); //
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            imageView = new ImageView(image);
            setImageOptionsH(imageView, 150, 10);

            commonGoalsContainer.getChildren().add(imageView);
            HBox.setHgrow(imageView, Priority.ALWAYS);
            imageView.setFitHeight(Double.MAX_VALUE);
        }
    }

    private void drawHiddenGoal() {
        hiddenGoalContainer.getChildren().clear();
        id = gameView.getPlayerView().getSecretCard().getCardId(); //secret goal
        image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
        imageView = new ImageView(image);
        setImageOptionsH(imageView,200,25);

        hiddenGoalContainer.getChildren().add(imageView);
        HBox.setHgrow(imageView, Priority.ALWAYS);
        imageView.setFitHeight(Double.MAX_VALUE);
    }

    private void drawResourceCardsTable(boolean clickable) {
        resourceCardsContainer.getChildren().clear();

        if(gameView.getCommon().getResourceDeck().equals(Constants.TEXT_FUNGI)) id = 1;
        else if (gameView.getCommon().getResourceDeck().equals(Constants.TEXT_PLANT)) id = 11;
        else if (gameView.getCommon().getResourceDeck().equals(Constants.TEXT_ANIMAL)) id = 21;
        else if (gameView.getCommon().getResourceDeck().equals(Constants.TEXT_INSECT)) id = 31;

        image = new Image(HelloApplication.class.getResource("images/back/"+id+".jpg").toString());
        ImageView deck = new ImageView(image);
        setImageOptionsV(deck);
        deck.setId("4");
        deck.getStyleClass().add("deckTop");

        if(clickable) {
            deck.setCursor(Cursor.HAND);
            deck.setOnMouseClicked(event -> {
                getInputReaderGUI().addString("/draw " + deck.getId());
            });
        }

        resourceCardsContainer.getChildren().add(deck);
        VBox.setVgrow(deck, Priority.ALWAYS);
        deck.setFitHeight(Double.MAX_VALUE);

        for (int i = 0; i < gameView.getCommon().getResourceCards().size(); i++) {
            id = gameView.getCommon().getResourceCards().get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            ImageView iw = new ImageView(image);
            setImageOptionsV(iw);
            iw.setId(""+i);

            if(clickable) {
                iw.getStyleClass().add("clickableCard");
                iw.setCursor(Cursor.HAND);
                iw.setOnMouseClicked(event -> {
                    getInputReaderGUI().addString("/draw " + iw.getId());
                });
            }

            resourceCardsContainer.getChildren().add(iw);
            VBox.setVgrow(iw, Priority.ALWAYS);
            iw.setFitHeight(Double.MAX_VALUE);
        }
    }

    private void drawGoldCardsTable(boolean clickable) {
        goldCardsContainer.getChildren().clear();

        if(gameView.getCommon().getGoldDeck().equals(Constants.TEXT_FUNGI)) id = 41;
        else if (gameView.getCommon().getGoldDeck().equals(Constants.TEXT_PLANT)) id = 51;
        else if (gameView.getCommon().getGoldDeck().equals(Constants.TEXT_ANIMAL)) id = 61;
        else if (gameView.getCommon().getGoldDeck().equals(Constants.TEXT_INSECT)) id = 71;

        image = new Image(HelloApplication.class.getResource("images/back/"+id+".jpg").toString());      //TODO create ID for gold and resource deck
        ImageView deck = new ImageView(image);
        setImageOptionsV(deck);
        deck.setId("5");
        deck.getStyleClass().add("deckTop");

        goldCardsContainer.getChildren().add(deck);
        VBox.setVgrow(deck, Priority.ALWAYS);
        deck.setFitHeight(Double.MAX_VALUE);

        if(clickable) {
            deck.setCursor(Cursor.HAND);
            deck.setOnMouseClicked(event -> {
                getInputReaderGUI().addString("/draw " + deck.getId());
            });
        }

        for (int i = 0; i < gameView.getCommon().getGoldCards().size(); i++) {
            id = gameView.getCommon().getGoldCards().get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            ImageView iw = new ImageView(image);
            setImageOptionsV(iw);
            iw.setId(""+(i+2));

            if(clickable) {
                iw.getStyleClass().add("clickableCard");
                iw.setCursor(Cursor.HAND);
                iw.setOnMouseClicked(event -> {
                    getInputReaderGUI().addString("/draw " + iw.getId());
                });
            }

            goldCardsContainer.getChildren().add(iw);
            VBox.setVgrow(iw, Priority.ALWAYS);
            iw.setFitHeight(Double.MAX_VALUE);
        }
    }

    @FXML
    public void drawChooseHiddenGoal(ArrayList<GameCardView> views){

        VBox hidden = new VBox();
        hidden.setPadding(new Insets(225, 100, 100, 100));

        Label label = new Label();
        label.setText("Choose your hidden goal");
        label.setFont(new Font(24));
        label.setAlignment(Pos.CENTER);

        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().add(label);
        labelBox.setPadding(new Insets(0, 0, 50, 0));

        hidden.getChildren().add(labelBox);

        HBox chooseHiddenGoal = new HBox();
        chooseHiddenGoal.setAlignment(Pos.CENTER);

        for (int i = 0; i < views.size(); i++) {
            id = views.get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            ImageView iw = new ImageView(image);
            setImageOptionsH(iw,350,50);
            iw.getStyleClass().add("clickableCard");
            iw.setId(""+i);
            iw.setCursor(Cursor.HAND);

            iw.setOnMouseClicked(event -> {
                iw.setOpacity(0.5);
                getInputReaderGUI().addString(iw.getId());
            });

            chooseHiddenGoal.getChildren().add(iw);
            HBox.setHgrow(iw, Priority.ALWAYS);
            iw.setFitHeight(Double.MAX_VALUE);
        }

        hidden.getChildren().add(chooseHiddenGoal);
        gameViewContainer.getChildren().add(hidden);
    }

    private void clearContainers() {
        playingHandContainer.getChildren().clear();
        hiddenGoalContainer.getChildren().clear();
        resourceCardsContainer.getChildren().clear();
        goldCardsContainer.getChildren().clear();
        commonGoalsContainer.getChildren().clear();
        rankingContainer.getChildren().clear();
        gameViewContainer.getChildren().clear();
    }
}
