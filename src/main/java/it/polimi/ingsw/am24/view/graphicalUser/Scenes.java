package it.polimi.ingsw.am24.view.graphicalUser;

public enum Scenes {
    MENU("/Menu.fxml"),
    NICKNAMESELECT("/NicknameSelect.fxml"),
    REDPLAYERICON("/RedPlayerIcon.fxml"),
    BLUEPLAYERICON("/BluePlayerIcon.fxml"),
    YELLOWPLAYERICON("/YellowPlayerIcon.fxml"),
    GREENPLAYERICON("/GreenPlayerIcon.fxml"),
    DISPLAY("/display.fxml"),
    LOBBYVIEW("/LobbyView.fxml"),
    LOGO("/Logo.fxml");
    private final String fxmlFile;

    Scenes(final String sceneName) {
        this.fxmlFile = sceneName;
    }
    public String getFxmlFile() {return this.fxmlFile;}
}
