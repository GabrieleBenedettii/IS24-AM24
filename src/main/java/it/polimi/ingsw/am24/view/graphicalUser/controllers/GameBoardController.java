package it.polimi.ingsw.am24.view.graphicalUser.controllers;

import it.polimi.ingsw.am24.HelloApplication;
import it.polimi.ingsw.am24.modelView.GameCardView;
import it.polimi.ingsw.am24.modelView.GameView;
import it.polimi.ingsw.am24.modelView.Placement;
import it.polimi.ingsw.am24.view.graphicalUser.Sound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.ArrayList;
import it.polimi.ingsw.am24.constants.Constants;


public class GameBoardController extends Generic{

    @FXML private VBox messagesContainer;
    @FXML private ScrollPane chatMessagesReceiver;
    @FXML private HBox playingHandContainer;
    @FXML private HBox hiddenGoalContainer;
    @FXML private VBox resourceCardsContainer;
    @FXML private VBox goldCardsContainer;
    @FXML private HBox commonGoalsContainer;
    @FXML private VBox rankingContainer;
    @FXML private Pane gameViewContainer;
    @FXML private StackPane scoreboardContainer;
    @FXML private ToggleButton frontBackToggle;
    @FXML private VBox rotationContainer;

    @FXML private ScrollPane chatMessagesContainer;
    @FXML private ChoiceBox receiver;
    @FXML private TextField messageText;

    @FXML private Label errorLabel;
    @FXML private Label actionMessage;

    private GameView gameView;
    private Image image;
    private ImageView imageView;
    private ImageView clickedImageView;
    private int id;
    private ImageView[] hand;
    private Font normal, bold;
    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        hand = new ImageView[3];
        normal = Font.loadFont(HelloApplication.class.getResourceAsStream("view/fonts/Muli-regular.ttf"), 18);
        bold = Font.loadFont(HelloApplication.class.getResourceAsStream("view/fonts/Muli-bold.ttf"), 18);
    }

    @FXML
    public void beginTurn(GameView gameView) {
        this.gameView = gameView;
        items.remove(gameView.getCurrent());
        gameViewContainer.setOpacity(1);
        actionMessage.setText(gameView.getCurrent() + ", it's your turn. Please, place a card");
        Sound.playSound("createjoinsound.wav");

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
        drawGameBoard(true, gameView.getCurrent());

        //SCORE BOARD
        drawScoreBoard();

        //ROTATION
        drawRotation();
    }

    @FXML
    public void beginDraw(GameView gameView) {
        this.gameView = gameView;
        errorLabel.setVisible(false);
        actionMessage.setText(gameView.getCurrent() + ", it's your turn. Please, draw a card");

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
        drawGameBoard(false, gameView.getCurrent());

        //SCORE BOARD
        drawScoreBoard();

        //ROTATION
        drawRotation();
    }

    @FXML
    public void hiddenGoalChoice(GameView gameView){
        this.gameView = gameView;
        items.add("All");
        items.addAll(gameView.getCommon().getRotation());
        receiver.setItems(items);
        receiver.setValue("All");

        messageText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if(receiver != null && !messageText.equals("")) {
                    getInputReaderGUI().addString((receiver.getValue().toString().equals("All") ? "/c " : "/cs " + receiver.getValue().toString() + " ") + messageText.getText());
                    Sound.playSound("button.wav");
                    messageText.clear();
                }
            }
        });

        //PLAYING HAND
        drawGameHand(false);

        //GOLD CARDS ON TABLE
        drawGoldCardsTable(false);

        //RESOURCE CARDS ON TABLE
        drawResourceCardsTable(false);

        //COMMON GOALS CARDS
        drawCommonGoals();

        //SCORE BOARD
        drawScoreBoard();
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
        VBox.setMargin(imageView, new Insets(5,0,5,0));
    }

    private void drawGameBoard(boolean clickable, String nickname) {
        gameViewContainer.getChildren().clear();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        GameCardView[][] board = gameView.getCommon().getPlayerView(nickname).getBoard();
        int firstRow = board.length;
        int lastRow = 0;
        int firstColumn = board[0].length;
        int lastColumn = 0;
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                if(board[r][c] != null){
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
        if(lastRow < board.length - 2){
            lastRow += 2;
        }
        if(firstColumn > 1){
            firstColumn -= 2;
        }
        if(lastColumn < board[0].length - 2){
            lastColumn += 2;
        }
        ArrayList<Placement> order = gameView.getCommon().getPlayerView(nickname).getPlaceOrder();
        double size = Math.max(lastRow-firstRow+1, lastColumn-firstColumn+1);
        double cellWidth = size >= 7 ? 125 : 800/size;
        double cellHeight = size >= 7 ? 66.3125 : 424.4/size;

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

            image = new Image(HelloApplication.class.getResource("images/"+(placement.getFront() ? "front" : "back")+"/"+placement.getCard().getCardId()+".jpg").toString());
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

        if(clickable) {
            //adding all possible placement
            boolean[][] pp = gameView.getCommon().getPlayerView(nickname).getPossiblePlacements();
            for (int i = 0; i < pp.length; i++) {
                for (int j = 0; j < pp[i].length; j++) {
                    if (pp[i][j]) {
                        StackPane stackPane = new StackPane();
                        stackPane.setPrefSize(cellWidth, cellHeight);
                        Pane pane = new Pane();
                        pane.setPrefSize(cellWidth, cellHeight);

                        Rectangle card = new Rectangle(cellWidth / 0.78, cellHeight / 0.6);
                        card.setId(i + "-" + j);
                        card.setFill(Color.GRAY);
                        card.setOpacity(0.2);
                        card.setTranslateX(-0.11 * (cellWidth / 0.78));
                        card.setTranslateY(-0.2 * (cellHeight / 0.6));

                        if (clickable) {
                            card.setCursor(Cursor.HAND);
                            card.setOnMouseClicked(event -> {
                                if (clickedImageView != null) {
                                    String[] coordinates = card.getId().split("-");
                                    pane.getChildren().clear();
                                    clickedImageView.setPreserveRatio(true);
                                    clickedImageView.setSmooth(true);
                                    clickedImageView.setCache(true);

                                    clickedImageView.setFitWidth(cellWidth / 0.78);
                                    clickedImageView.setLayoutX(-0.11 * (cellWidth / 0.78));
                                    clickedImageView.setLayoutY(-0.2 * (cellHeight / 0.6));

                                    pane.getChildren().add(clickedImageView);
                                    getInputReaderGUI().addString("/play " + clickedImageView.getId() + " " + frontBackToggle.getText() + " " + coordinates[0] + " " + coordinates[1]);
                                    Sound.playSound("button.wav");
                                }
                            });
                        }

                        pane.getChildren().add(card);
                        stackPane.getChildren().add(pane);
                        gridPane.add(stackPane, j, i);
                    }
                }
            }
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefViewportWidth(800);
        scrollPane.setPrefViewportHeight(424.4);
        scrollPane.setPannable(true);
        scrollPane.getStyleClass().add("transparent-scroll-pane");
        gameViewContainer.getChildren().add(scrollPane);
    }


    private void drawGameHand(boolean clickable){
        playingHandContainer.getChildren().clear();
        for (int i = 0; i < gameView.getPlayerView().getPlayerHand().size(); i++) {
            id = gameView.getPlayerView().getPlayerHand().get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/"+frontBackToggle.getText()+"/"+id+".jpg").toString());
            hand[i] = new ImageView(image);

            hand[i].setId(""+i);
            setImageOptionsH(hand[i],150,15);

            if(clickable) {
                hand[i].getStyleClass().add("clickableCard");
                hand[i].setCursor(Cursor.HAND);
                int index = i;
                hand[i].setOnMouseClicked(event -> {
                    clearBorders(hand[index].getParent(), hand[index]);
                    hand[index].setOpacity(0.5);
                    clickedImageView = hand[index];
                    Sound.playSound("button.wav");
                });
            }

            playingHandContainer.getChildren().add(hand[i]);
            HBox.setHgrow(hand[i], Priority.ALWAYS);
            hand[i].setFitHeight(Double.MAX_VALUE);
        }

        frontBackToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            frontBackToggle.setText(newValue ? "back" : "front");
            for (int i = 0; i < gameView.getPlayerView().getPlayerHand().size(); i++) {
                id = gameView.getPlayerView().getPlayerHand().get(i).getCardId();
                image = new Image(HelloApplication.class.getResource("images/" + frontBackToggle.getText() + "/" + id + ".jpg").toString());
                hand[i].setImage(image);
            }
        });
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
        for (int i = 0; i < gameView.getCommon().getCommonBoardView().getGoals().size(); i++) {
            id = gameView.getCommon().getCommonBoardView().getGoals().get(i).getCardId(); //
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
        setImageOptionsH(imageView,150,25);

        hiddenGoalContainer.getChildren().add(imageView);
        HBox.setHgrow(imageView, Priority.ALWAYS);
        imageView.setFitHeight(Double.MAX_VALUE);
    }

    private void drawResourceCardsTable(boolean clickable) {
        resourceCardsContainer.getChildren().clear();

        if(gameView.getCommon().getCommonBoardView().getResourceDeck().equals(Constants.TEXT_FUNGI)) id = 1;
        else if (gameView.getCommon().getCommonBoardView().getResourceDeck().equals(Constants.TEXT_PLANT)) id = 11;
        else if (gameView.getCommon().getCommonBoardView().getResourceDeck().equals(Constants.TEXT_ANIMAL)) id = 21;
        else if (gameView.getCommon().getCommonBoardView().getResourceDeck().equals(Constants.TEXT_INSECT)) id = 31;

        image = new Image(HelloApplication.class.getResource("images/back/"+id+".jpg").toString());
        ImageView deck = new ImageView(image);
        setImageOptionsV(deck);
        deck.setId("4");
        deck.getStyleClass().add("deckTop");

        if(clickable) {
            deck.setCursor(Cursor.HAND);
            deck.setOnMouseClicked(event -> {
                getInputReaderGUI().addString("/draw " + deck.getId());
                Sound.playSound("button.wav");
            });
        }

        resourceCardsContainer.getChildren().add(deck);
        VBox.setVgrow(deck, Priority.ALWAYS);
        deck.setFitHeight(Double.MAX_VALUE);

        for (int i = 0; i < gameView.getCommon().getCommonBoardView().getResourceCards().size(); i++) {
            id = gameView.getCommon().getCommonBoardView().getResourceCards().get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            ImageView iw = new ImageView(image);
            setImageOptionsV(iw);
            iw.setId(""+i);

            if(clickable) {
                iw.getStyleClass().add("clickableCard");
                iw.setCursor(Cursor.HAND);
                iw.setOnMouseClicked(event -> {
                    getInputReaderGUI().addString("/draw " + iw.getId());
                    Sound.playSound("button.wav");
                });
            }

            resourceCardsContainer.getChildren().add(iw);
            VBox.setVgrow(iw, Priority.ALWAYS);
            iw.setFitHeight(Double.MAX_VALUE);
        }
    }

    private void drawGoldCardsTable(boolean clickable) {
        goldCardsContainer.getChildren().clear();

        if(gameView.getCommon().getCommonBoardView().getGoldDeck().equals(Constants.TEXT_FUNGI)) id = 41;
        else if (gameView.getCommon().getCommonBoardView().getGoldDeck().equals(Constants.TEXT_PLANT)) id = 51;
        else if (gameView.getCommon().getCommonBoardView().getGoldDeck().equals(Constants.TEXT_ANIMAL)) id = 61;
        else if (gameView.getCommon().getCommonBoardView().getGoldDeck().equals(Constants.TEXT_INSECT)) id = 71;

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
                Sound.playSound("button.wav");
            });
        }

        for (int i = 0; i < gameView.getCommon().getCommonBoardView().getGoldCards().size(); i++) {
            id = gameView.getCommon().getCommonBoardView().getGoldCards().get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            ImageView iw = new ImageView(image);
            setImageOptionsV(iw);
            iw.setId(""+(i+2));

            if(clickable) {
                iw.getStyleClass().add("clickableCard");
                iw.setCursor(Cursor.HAND);
                iw.setOnMouseClicked(event -> {
                    getInputReaderGUI().addString("/draw " + iw.getId());
                    Sound.playSound("button.wav");
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
        hidden.setPadding(new Insets(0, 0, 0, 0));
        hidden.setAlignment(Pos.CENTER);
        hidden.setPrefSize(800,424.4);

        Label label = new Label();
        label.setText("Choose your hidden goal");
        Font customFont = Font.loadFont(HelloApplication.class.getResourceAsStream("view/fonts/Muli-Regular.ttf"), 22);
        label.setFont(customFont);
        label.setAlignment(Pos.CENTER);

        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.getChildren().add(label);
        labelBox.setPadding(new Insets(50, 0, 25, 0));

        hidden.getChildren().add(labelBox);

        HBox chooseHiddenGoal = new HBox();
        chooseHiddenGoal.setAlignment(Pos.CENTER);
        chooseHiddenGoal.setPadding(new Insets(25, 0, 0, 0));

        for (int i = 0; i < views.size(); i++) {
            id = views.get(i).getCardId();
            image = new Image(HelloApplication.class.getResource("images/front/"+id+".jpg").toString());
            ImageView iw = new ImageView(image);
            setImageOptionsH(iw,250,25);
            iw.getStyleClass().add("clickableCard");
            iw.setId(""+i);
            iw.setCursor(Cursor.HAND);

            iw.setOnMouseClicked(event -> {
                iw.setOpacity(0.5);
                getInputReaderGUI().addString(iw.getId());
                Sound.playSound("button.wav");
            });

            chooseHiddenGoal.getChildren().add(iw);
            HBox.setHgrow(iw, Priority.ALWAYS);
            iw.setFitHeight(Double.MAX_VALUE);
        }

        hidden.getChildren().add(chooseHiddenGoal);
        gameViewContainer.getChildren().add(hidden);
    }

    @FXML
    public void requirementsNotMet() {
        errorLabel.setText("You can't place this card, you don't fulfil the requirements");
        errorLabel.setVisible(true);
    }

    @FXML
    public void notYourTurn(GameView gameView, String myNickname) {
        this.gameView = gameView;
        items.remove(myNickname);
        actionMessage.setText(gameView.getCurrent() + " is playing. Wait for your turn");
        gameViewContainer.setOpacity(0.5);

        drawGameBoard(false, myNickname);
        drawGoldCardsTable(false);
        drawResourceCardsTable(false);
        drawGameHand(false);
        drawHiddenGoal();
        drawRotation();
    }

    private void drawScoreBoard() {
        image = new Image(HelloApplication.class.getResource("images/misc/scoreboard.png").toString());
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        imageView.setFitWidth(250);
        Canvas canvas = new Canvas(250, 498.6);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        for (String s : gameView.getCommon().getRotation()){
            Point p = Constants.scoreboard.get(gameView.getCommon().getPlayerView(s).getPlayerScore());
            double x = 15+p.getX()/2.904;
            double y = p.getY()/2.904;
            double radius = 12;


            if (gameView.getCommon().getPlayerView(s).getColor().equals("RED") ){ x+=5;}
            else if (gameView.getCommon().getPlayerView(s).getColor().equals("BLUE")){ x-=5;}
            else if (gameView.getCommon().getPlayerView(s).getColor().equals("GREEN")){ y+=5;}
            else if (gameView.getCommon().getPlayerView(s).getColor().equals("YELLOW")){ y-=5;}

            drawCircle(gc, x, y, radius, gameView.getCommon().getPlayerView(s).getColor());


        }
        scoreboardContainer.getChildren().addAll(imageView, canvas);
        StackPane.setMargin(imageView, new Insets(0, 0, 0, 30));
    }

    private void drawCircle(GraphicsContext gc, double x, double y, double radius, String color) {
        gc.setFill(Color.valueOf(color));
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    private void drawRotation() {
        rotationContainer.getChildren().clear();
        rotationContainer.setAlignment(Pos.CENTER);

        int padding = 20 - gameView.getCommon().getRotation().size() * 4;

        for (String p : gameView.getCommon().getRotation()) {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(10);
            hbox.setPadding(new Insets(padding, 0, padding, 30));

            Circle circle = new Circle(12, Paint.valueOf(gameView.getCommon().getPlayerView(p).getColor()));
            circle.setCursor(Cursor.HAND);
            circle.setOnMouseClicked(event -> drawGameBoard(false, p));

            Text playerName = new Text(p);
            playerName.setFont(p.equals(gameView.getCurrent()) ? bold : normal);
            hbox.getChildren().addAll(circle, playerName);
            rotationContainer.getChildren().add(hbox);
        }
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

    public void addMessage(String message) {
        Text text = new Text(message);
        messagesContainer.getChildren().add(text);
        chatMessagesReceiver.layout();
        chatMessagesReceiver.setVvalue(1.0);
    }
}
