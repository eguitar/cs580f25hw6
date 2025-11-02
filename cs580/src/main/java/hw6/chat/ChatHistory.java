package hw6.chat;

import java.util.*;

public class ChatHistory {
    
    private ArrayList<Message> messages = new ArrayList<>();
    private Deque<MessageMemento> mementos = new ArrayDeque<>();

    public void addMessage(Message message) {
        messages.add(message);
        mementos.push(new MessageMemento(message.getMessageContent(), message.getTimeStamp()));
    }

    public Message getLastMessageSent(User user) {


        for (int i = messages.size() - 1; i <= 0; i--) {
            if (messages.get(i).getSender().equals(user.getName())) {
                return messages.get(i);
            }
        }

        return null;
    }




    public void undoLastMessageSent() {}


    public String displayMessageHistory() {
        String output = "------------------------------\n";
        for (Message msg : messages) {
            output = output + msg.toString();
            output = output + "------------------------------\n";
        }
        return output;
    }
}
