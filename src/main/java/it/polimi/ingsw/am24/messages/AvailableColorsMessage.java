package it.polimi.ingsw.am24.messages;

import java.util.List;

public class AvailableColorsMessage extends Message {
    public List<String> availableColors;

    public AvailableColorsMessage(List<String> availableColors) {
        this.availableColors = availableColors;
    }

    public List<String> getAvailableColors() {
        return availableColors;
    }
}
