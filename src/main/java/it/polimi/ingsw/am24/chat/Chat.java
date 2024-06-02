package it.polimi.ingsw.am24.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * The Chat class represents the functions connected to the chat present in the game
 **/
public class Chat implements Serializable {
    private List<ChatMessage> messages;

    /**
     * Constructor for the Chat class.
     * Initializes the list of chat messages.
     */
    public Chat() {
        this.messages = new ArrayList<ChatMessage>();
    }

    /**
     * Returns a list of chat messages as strings.
     * @return a list of chat messages.
     */
    public List<String> getMessages() {
        return messages.stream().map(m -> m.getMessage()).toList();
    }

    /**
     * Adds a public message to the chat.
     * @param sender the sender of the message.
     * @param message the content of the message.
     */
    public void addPublicMessage(String sender, String message) {
        /*if (messages.size() > maxNumMessages)
            messages.removeFirst();*/
        messages.add(new ChatMessage(sender,message));
    }

    /**
     * Adds a private message to a specific user to the chat.
     * @param sender the sender of the message.
     * @param receiver the receiver of the message.
     * @param message the content of the message.
     */
    public void addPrivateMessage(String sender, String receiver, String message) {
        /*if (messages.size() > maxNumMessages)
            messages.removeFirst();*/
        messages.add(new ChatMessage(sender,receiver,message));
    }

    /**
     * Returns the last message in the chat.
     * @return the last ChatMessage object.
     */
    public ChatMessage getLast() {
        return messages.get(messages.size() - 1);
    }
}
