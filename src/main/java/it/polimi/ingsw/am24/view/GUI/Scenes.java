package it.polimi.ingsw.am24.view.GUI;

public enum Scenes {
    MENU("Menu.fxml"),
    NICKNAMESELECT("NicknameSelect.fxml"),
    CREATEGAMENICKNAMESELECT("CreateGameNicknameSelect.fxml"),
    ENDGAMESCREEN("EndGameScreen.fxml"),
    SECRETGOALCARDSELECTOR("SecretGoalCardSelector.fxml"),
    LOBBYVIEW("LobbyView.fxml"),
    LOGO("Logo.fxml"),
    COLORSELECTOR("ColorSelector.fxml"),
    GAME("game.fxml"),
    INITIALCARDSELECTOR("InitialCardSelector.fxml"),
    SERVERDISCONNECTION("ServerDisconnection.fxml");

    private final String fxmlFile;

    Scenes(final String sceneName) {
        this.fxmlFile = sceneName;
    }
    public String getFxmlFile() {return this.fxmlFile;}
}
