package it.polimi.ingsw.am24;

import it.polimi.ingsw.am24.Controller.Controller;

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
        Controller controller = new Controller();
        //launch();
    }
}