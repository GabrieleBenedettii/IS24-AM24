package it.polimi.ingsw.am24;

import it.polimi.ingsw.am24.model.Game;
import it.polimi.ingsw.am24.model.Player;
import it.polimi.ingsw.am24.model.PlayerColor;
import it.polimi.ingsw.am24.model.card.GoldCard;
import it.polimi.ingsw.am24.model.card.InitialCard;
import it.polimi.ingsw.am24.model.card.ResourceCard;
import it.polimi.ingsw.am24.model.deck.InitialDeck;
import it.polimi.ingsw.am24.model.goal.GoalCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication /*extends Application*/ {
    /*@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }*/

    public static void main(String[] args) {
        Game game = new Game();
        game.addFirstPlayer(new Player("Gabriele", PlayerColor.RED),2);
        game.addNewPlayer(new Player("Michele", PlayerColor.BLUE));
        game.start();
        //launch();
    }
}