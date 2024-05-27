package it.polimi.ingsw.am24.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private List<ChatMessage> messages;

    public Chat() {
        this.messages = new ArrayList<ChatMessage>();
    }

    public List<String> getMessages() {
        return messages.stream().map(m -> m.getMessage()).toList();
    }

    public void addPublicMessage(String sender, String message) {
        /*if (messages.size() > maxNumMessages)
            messages.removeFirst();*/
        messages.add(new ChatMessage(sender,message));
    }

    public void addPrivateMessage(String sender, String receiver, String message) {
        /*if (messages.size() > maxNumMessages)
            messages.removeFirst();*/
        messages.add(new ChatMessage(sender,receiver,message));
    }

    public ChatMessage getLast() {
        return messages.get(messages.size() - 1);
    }
}
