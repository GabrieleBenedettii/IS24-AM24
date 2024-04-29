package it.polimi.ingsw.am24.chat;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage implements Serializable {
    private String sender;
    private String receiver;
    private String message;
    private LocalTime time;

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.receiver = "";
        this.message = message;
        this.time = java.time.LocalTime.now();;
    }

    public ChatMessage(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = java.time.LocalTime.now();;
    }

    public String getMessage() {
        String ret = !receiver.isEmpty() ? "[PRIVATE]" : "[PUBLIC]";
        ret += " [" + time.format(DateTimeFormatter.ofPattern("HH:mm")) + "]";
        ret += " from " + sender + ": " + message;
        return ret;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
