package it.polimi.ingsw.am24.view.GUI;

public enum Scenes {
    MENU("Menu.fxml"),
    CREATE_GAME("CreateGame.fxml"),
    JOIN_GAME("JoinGame.fxml"),
    COLOR_SELECTOR("ColorSelector.fxml"),
    LOBBY("Lobby.fxml"),
    INITIAL_CARD_SELECTOR("InitialCardSelector.fxml"),
    GAME("Game.fxml"),
    END_GAME("EndGame.fxml"),
    SERVER_DISCONNECTION("ServerDisconnection.fxml");

    private final String fxmlFile;

    Scenes(final String sceneName) {
        this.fxmlFile = sceneName;
    }

    public String getFxmlFile() {
        return this.fxmlFile;
    }
}
