package it.polimi.ingsw.am24.view.GUI.controllers;

import it.polimi.ingsw.am24.Root;
import it.polimi.ingsw.am24.modelview.GameCardView;
import it.polimi.ingsw.am24.modelview.GameView;
import it.polimi.ingsw.am24.modelview.Placement;
import it.polimi.ingsw.am24.view.GUI.Sound;
import it.polimi.ingsw.am24.view.GameStatus;
import javafx.application.Platform;
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
import java.util.HashMap;
import java.util.Objects;

import it.polimi.ingsw.am24.constants.Constants;



public class GameBoardController extends GUIController {

    @FXML private ScrollPane chatMessagesReceiver;
    @FXML private HBox playingHandContainer;
    @FXML private HBox hiddenGoalContainer;
    @FXML private VBox resourceCardsContainer;
    @FXML private VBox goldCardsContainer;
    @FXML private HBox commonGoalsContainer;
    @FXML private Pane gameViewContainer;
    @FXML private StackPane scoreboardContainer;
    @FXML private ToggleButton frontBackToggle;
    @FXML private VBox rotationContainer;
    @FXML private HBox visibleSymbolsContainer;

    @FXML private ChoiceBox receiver;
    @FXML private TextField messageText;

    @FXML private Label errorLabel;
    @FXML private Label actionMessage;
    @FXML private Label nameContainer;
    @FXML private Label playerTableContainer;
    @FXML private Label statusContainer;

    private String nickname;
    private GameView gameView;
    private GameStatus status;
    private Image image;
    private ImageView imageView;
    private ImageView clickedImageView;
    private int id;
    private ImageView[] hand;
    private Font normal, bold;
    private final ObservableList<String> items = FXCollections.observableArrayList();
    //private MediaPlayer music;
    private int i;
    private final GridPane grid =new GridPane();

    private final ArrayList<String> iconsOrder = new ArrayList<>();

    @FXML
    public void initialize() {
        hand = new ImageView[3];
        normal = Font.loadFont(Root.class.getResourceAsStream("view/fonts/Muli-regular.ttf"), 18);
        bold = Font.loadFont(Root.class.getResourceAsStream("view/fonts/Muli-bold.ttf"), 18);

        iconsOrder.add("FUNGI");
        iconsOrder.add("PLANT");
        iconsOrder.add("INSECT");
        iconsOrder.add("ANIMAL");
        iconsOrder.add("QUILL");
        iconsOrder.add("MANUSCRIPT");
        iconsOrder.add("INK");

//        URL url = GUIapp.class.getResource("/totallyNotOblivionSong1.mp3");
//        music = new MediaPlayer(new Media(url.toString()));
//        music.play();
//        music.setOnEndOfMedia(() -> {
//            music.setRate(music.getRate() + 0.5);
//            music.setVolume(music.getVolume() + 25);
//            music.play();
//        });
//        music.setCycleCount(MediaPlayer.INDEFINITE);
    }

    @FXML
    public void beginTurn(GameView gameView) {
        this.gameView = gameView;
        receiver.setDisable(false);
        messageText.setDisable(false);
        clickedImageView = null;
        gameViewContainer.setOpacity(1);
        actionMessage.setText("It's your turn. Please, place a card");
        actionMessage.setAlignment(Pos.CENTER);
        playerTableContainer.setText("");
        gameViewContainer.setStyle("-fx-background-color: #FFF9DFFF; -fx-border-color: #A49849; -fx-border-width: 1px;-fx-border-radius: 10px;");
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

        //VISIBLE SYMBOLS
        drawVisibleSymbols();

        //STATUS
        drawStatus();
    }

    @FXML
    public void beginDraw(GameView gameView) {
        this.gameView = gameView;
        errorLabel.setVisible(false);
        actionMessage.setText("It's your turn. Please, draw a card");
        actionMessage.setAlignment(Pos.CENTER);

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

        //VISIBLE SYMBOLS
        drawVisibleSymbols();

        //STATUS
        drawStatus();
    }

    @FXML
    public void hiddenGoalChoice(GameView gameView){
        this.nickname = gameView.getCurrent();
        receiver.setDisable(true);
        messageText.setDisable(true);
        nameContainer.setText(nickname);
        nameContainer.setAlignment(Pos.CENTER);
        this.gameView = gameView;
        status = GameStatus.RUNNING;
        statusContainer.setText("Status: the game has started ");
        statusContainer.setStyle("-fx-font-family: 'Muli'; -fx-font-size: 15; -fx-font-weight: bold;");
        statusContainer.setAlignment(Pos.CENTER);

        items.add("All");
        items.addAll(gameView.getCommon().getRotation());
        items.remove(nickname);
        receiver.setItems(items);
        receiver.setValue("All");

        messageText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if(receiver != null && !messageText.getText().isEmpty()) {
                    getInputReaderGUI().addString((receiver.getValue().toString().equals("All") ? "/c " : ("/cs " + receiver.getValue().toString() + " ")) + messageText.getText());
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

    private void drawGameBoard(boolean clickable, String nick) {
        gameViewContainer.getChildren().clear();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        GameCardView[][] board = gameView.getCommon().getPlayerView(nick).getBoard();
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
        ArrayList<Placement> order = gameView.getCommon().getPlayerView(nick).getPlaceOrder();
        double size = Math.max(lastRow-firstRow+1, lastColumn-firstColumn+1);
        double cellWidth = size >= 7 ? 125 : 800/size;
        double cellHeight = size >= 7 ? 62.693 : 401.23521/size;

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

            image = new Image(Objects.requireNonNull(Root.class.getResource("images/" + (placement.getFront() ? "front" : "back") + "/" + placement.getCard().getCardId() + ".png")).toString());
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            imageView.setFitWidth(cellWidth/0.78);
            imageView.setLayoutX(-0.11*(cellWidth/0.78));
            imageView.setLayoutY(-0.205*(cellHeight/0.59));

            pane.getChildren().add(imageView);
            stackPane.getChildren().add(pane);
            gridPane.add(stackPane, placement.getY(), placement.getX());
        }

        if(clickable) {
            //adding all possible placements
            boolean[][] pp = gameView.getCommon().getPlayerView(nick).getPossiblePlacements();
            for (int i = 0; i < pp.length; i++) {
                for (int j = 0; j < pp[i].length; j++) {
                    if (pp[i][j]) {
                        StackPane stackPane = new StackPane();
                        stackPane.setPrefSize(cellWidth, cellHeight);
                        Pane pane = new Pane();
                        pane.setPrefSize(cellWidth, cellHeight);

                        Rectangle card = new Rectangle(cellWidth / 0.78, cellHeight / 0.59);
                        card.setId(i + "-" + j);
                        card.setFill(Color.GRAY);
                        card.setOpacity(0.2);
                        card.setTranslateX(-0.11 * (cellWidth / 0.78));
                        card.setTranslateY(-0.205 * (cellHeight / 0.59));

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

                        pane.getChildren().add(card);
                        stackPane.getChildren().add(pane);
                        gridPane.add(stackPane, j, i);
                    }
                }
            }
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefViewportWidth(810);
        scrollPane.setPrefViewportHeight(411);
        scrollPane.setPannable(true);
        scrollPane.getStyleClass().add("transparent-scroll-pane");
        gameViewContainer.getChildren().add(scrollPane);
    }


    private void drawGameHand(boolean clickable){
        playingHandContainer.getChildren().clear();
        for (int i = 0; i < gameView.getPlayerView().getPlayerHand().size(); i++) {
            id = gameView.getPlayerView().getPlayerHand().get(i).getCardId();
            image = new Image(Objects.requireNonNull(Root.class.getResource("images/" + frontBackToggle.getText() + "/" + id + ".png")).toString());
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
                image = new Image(Objects.requireNonNull(Root.class.getResource("images/" + frontBackToggle.getText() + "/" + id + ".png")).toString());
                hand[i].setImage(image);
            }
        });
    }

    private void clearBorders(Parent parent, Node node) {
        if (parent instanceof HBox hbox) {
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
            image = new Image(Objects.requireNonNull(Root.class.getResource("images/front/" + id + ".png")).toString());
            imageView = new ImageView(image);
            setImageOptionsH(imageView, 150, 10);

            commonGoalsContainer.getChildren().add(imageView);
            HBox.setHgrow(imageView, Priority.ALWAYS);
            imageView.setFitHeight(Double.MAX_VALUE);
        }
    }

    private void drawHiddenGoal() {
        hiddenGoalContainer.getChildren().clear();
        id = gameView.getPlayerView().getHiddenGoal().getCardId(); //hidden goal
        image = new Image(Objects.requireNonNull(Root.class.getResource("images/front/" + id + ".png")).toString());
        imageView = new ImageView(image);
        setImageOptionsH(imageView,150,25);

        hiddenGoalContainer.getChildren().add(imageView);
        HBox.setHgrow(imageView, Priority.ALWAYS);
        imageView.setFitHeight(Double.MAX_VALUE);
    }

    private void drawResourceCardsTable(boolean clickable) {
        resourceCardsContainer.getChildren().clear();

        switch (gameView.getCommon().getCommonBoardView().getResourceDeck()) {
            case Constants.TEXT_FUNGI -> id = 1;
            case Constants.TEXT_PLANT -> id = 11;
            case Constants.TEXT_ANIMAL -> id = 21;
            case Constants.TEXT_INSECT -> id = 31;
        }

        image = new Image(Objects.requireNonNull(Root.class.getResource("images/back/" + id + ".png")).toString());
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
            image = new Image(Objects.requireNonNull(Root.class.getResource("images/front/" + id + ".png")).toString());
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

        switch (gameView.getCommon().getCommonBoardView().getGoldDeck()) {
            case Constants.TEXT_FUNGI -> id = 41;
            case Constants.TEXT_PLANT -> id = 51;
            case Constants.TEXT_ANIMAL -> id = 61;
            case Constants.TEXT_INSECT -> id = 71;
        }

        image = new Image(Objects.requireNonNull(Root.class.getResource("images/back/" + id + ".png")).toString());      //TODO create ID for gold and resource deck
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
            image = new Image(Objects.requireNonNull(Root.class.getResource("images/front/" + id + ".png")).toString());
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
        hidden.setPrefSize(800,401.23521);

        Label label = new Label();
        label.setStyle("-fx-font-family: 'Muli'; -fx-font-size: 30; -fx-font-weight: bold;");
        label.setText("Choose your hidden goal");
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
            image = new Image(Objects.requireNonNull(Root.class.getResource("images/front/" + id + ".png")).toString());
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
        errorLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    public void notYourTurn(GameView gameView, String myNickname) {
        this.gameView = gameView;
        actionMessage.setText(gameView.getCurrent() + " is playing. Wait for your turn");
        actionMessage.setAlignment(Pos.CENTER);
        gameViewContainer.setOpacity(0.5);
        receiver.setDisable(false);
        messageText.setDisable(false);

        drawGameBoard(false, myNickname);
        drawGoldCardsTable(false);
        drawResourceCardsTable(false);
        drawGameHand(false);
        drawHiddenGoal();
        drawRotation();
        drawScoreBoard();
        drawVisibleSymbols();
        drawStatus();
    }

    private void drawScoreBoard() {
        image = new Image(Objects.requireNonNull(Root.class.getResource("images/misc/scoreboard.png")).toString());
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

            StackPane stackPane = new StackPane();
            Circle circle = new Circle(12, Paint.valueOf(gameView.getCommon().getPlayerView(p).getColor()));
            stackPane.getChildren().add(circle);

            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Root.class.getResource("images/misc/eye_icon.png")).toString()));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            stackPane.getChildren().add(imageView);
            stackPane.setCursor(Cursor.HAND);

            stackPane.setOnMouseClicked(event -> {
                gameViewContainer.setOpacity(gameView.getCurrent().equals(nickname) && p.equals(nickname) ? 1 : 0.5);
                playerTableContainer.setText(p.equals(nickname) ? "" : (p + "'s table"));
                playerTableContainer.setAlignment(Pos.CENTER);
                drawGameBoard(gameView.getCurrent().equals(nickname) && p.equals(nickname), p);
            });

            Text playerName = new Text(p);
            playerName.setFont(p.equals(gameView.getCurrent()) ? bold : normal);
            hbox.getChildren().addAll(stackPane, playerName);
            rotationContainer.getChildren().add(hbox);
        }
    }

    private void drawVisibleSymbols() {
        visibleSymbolsContainer.getChildren().clear();
        HashMap<String,Integer> visibleSymbols = gameView.getCommon().getPlayerView(nickname).getVisibleSymbols();

        for (String ip: iconsOrder) {
            VBox vbox = new VBox(5);
            vbox.setAlignment(Pos.CENTER);

            Image image = new Image(Objects.requireNonNull(Root.class.getResource("images/icons/" + ip + ".png")).toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);

            vbox.getChildren().add(imageView);

            Text number = new Text("" + visibleSymbols.get(ip));
            number.setStyle("-fx-font-family: 'Muli'; -fx-font-size: 15; -fx-font-weight: bold;");

            vbox.getChildren().add(number);

            visibleSymbolsContainer.getChildren().add(vbox);
        }
    }

    private void drawStatus() {
        if(!gameView.getGameStatus().equals(status)) {
            status = gameView.getGameStatus();
            if(status.equals(GameStatus.LAST_LAST_ROUND))
                statusContainer.setText("Status: 20 points have been reached\nthe next to last round has begun");
            else
                statusContainer.setText("Status: the last round has finally begun");
        }
    }

    public void addMessage(String message) {
        Text text = new Text(message);
        text.setStyle("-fx-font-family: 'Muli'");
        grid.add(text,0,i);
        i++;
        chatMessagesReceiver.setContent(grid);
        Platform.runLater(() -> {
            chatMessagesReceiver.layout();
            chatMessagesReceiver.setVvalue(1.0);
        });
    }
}
