package it.polimi.ingsw.am24.chat;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The ChatMessage class represents a message in the chat.
 * It contains information about the sender, receiver, message content, and the time the message was sent.
 */
public class ChatMessage implements Serializable {
    private String sender;
    private String receiver;
    private String message;
    private LocalTime time;

    /**
     * Constructor for creating a public chat message.
     * @param sender the sender of the message.
     * @param message the content of the message.
     */
    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.receiver = "";
        this.message = message;
        this.time = java.time.LocalTime.now();
    }

    /**
     * Constructor for creating a private chat message.
     * @param sender the sender of the message.
     * @param receiver the receiver of the message.
     * @param message the content of the message.
     */
    public ChatMessage(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = java.time.LocalTime.now();
    }

    /**
     * Returns the content of the message.
     * @return the message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the sender of the message.
     * @return the message sender.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns the receiver of the message.
     * @return the message receiver.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Returns the timestamp of the message.
     * @return the message timestamp.
     */
    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
