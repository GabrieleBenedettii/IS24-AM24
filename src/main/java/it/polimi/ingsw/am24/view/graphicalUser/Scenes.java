package it.polimi.ingsw.am24.view.graphicalUser;

public enum Scenes {
    MENU("Menu.fxml"),
    NICKNAMESELECT("NicknameSelect.fxml"),
    CREATEGAMENICKNAMESELECT("CreateGameNicknameSelect.fxml"),
    /*REDPLAYERICON("RedPlayerIcon.fxml"),
    BLUEPLAYERICON("BluePlayerIcon.fxml"),
    YELLOWPLAYERICON("YellowPlayerIcon.fxml"),
    GREENPLAYERICON("GreenPlayerIcon.fxml"),
    DISPLAY("display.fxml"),*/
    ENDGAMESCREEN("EndGameScreen.fxml"),
    SECRETGOALCARDSELECTOR("SecretGoalCardSelector.fxml"),
    LOBBYVIEW("LobbyView.fxml"),
    LOGO("Logo.fxml"),
    COLORSELECTOR("ColorSelector.fxml"),
    GAME("game.fxml"),
    INITIALCARDSELECTOR("InitialCardSelector.fxml");

    private final String fxmlFile;

    Scenes(final String sceneName) {
        this.fxmlFile = sceneName;
    }
    public String getFxmlFile() {return this.fxmlFile;}
}