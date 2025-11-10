package hw6;

import java.time.LocalDateTime;
import java.util.*;

public class ChatHistory implements IterableByUser {
    private ArrayList<Message> messages = new ArrayList<>();
    
    public void addMessage(Message message) { messages.add(message); }

    public Message getLastMessageSent(User user) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message msg = messages.get(i);
            if (msg.getSender().getName().equals(user.getName())) {
                return messages.get(i);
            }
        }
        return null;
    }

    public int getNumMessages() { return messages.size(); }

    public void undoLastMessageSent(String content, LocalDateTime timestamp) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            Message msg = messages.get(i);
            if (msg.getMessageContent().equals(content) && msg.getTimeStamp().equals(timestamp)) {
                messages.remove(i);
            }
        }
    }

    public String displayMessageHistory() {
        String output = "------------------------------\n";
        for (Message msg : messages) {
            output = output + msg.toString();
            output = output + "------------------------------\n";
        }
        return output;
    }

    public boolean containsMessage(String msgContent) {
        for (Message msg : messages) {
            if (msg.getMessageContent().equals(msgContent)) { return true; }
        }
        return false;
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessagesByUserIterator(userToSearchWith, messages);
    }
}
