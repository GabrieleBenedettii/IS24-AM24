package it.polimi.ingsw.am24.view;

import it.polimi.ingsw.am24.listeners.ViewListener;
import it.polimi.ingsw.am24.messages.Message;

public interface View {
    void update(Message m);

    void notifyListeners(Message m);

    void addListener(ViewListener listener);

    void run();
}
