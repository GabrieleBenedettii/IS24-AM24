package it.polimi.ingsw.am24.view.flow;

import it.polimi.ingsw.am24.listeners.GameListener;

public abstract class Flow implements GameListener {
    /*protected void resetGameId(DisconnectionFile disconnectionFile, GameController controller) {
        for(String p : controller.getPlayers().keySet()){
            disconnectionFile.setLastGameId(p, -1);
        }
    }
    protected void saveGameId(DisconnectionFile disconnectionFile, String nickname, int gameId){
        disconnectionFile.setLastGameId(nickname, gameId);
    }
    public abstract void noConnectionError();*/
}
