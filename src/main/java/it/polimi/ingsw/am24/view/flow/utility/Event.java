package it.polimi.ingsw.am24.view.flow.utility;

public class Event {
    private EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
