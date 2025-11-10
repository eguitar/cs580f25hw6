package hw6;

import java.util.*;
import java.time.LocalDateTime;

public class User implements IterableByUser {
    private String name;
    private ChatHistory chatHistory;
    private MessageMemento memento;
    
    public User(String name) {
        this.name = name;
        this.chatHistory = new ChatHistory();
        this.memento = null;
    }

    public String getName() { return name; }

    public void sendMessage(ArrayList<User> recipients, String content, ChatServer chatServer) {
        if (chatServer == null) return;
        if (!chatServer.validateUser(this.name)) return;

        LocalDateTime timestamp = LocalDateTime.now();
        ArrayList<User> validRecipients = new ArrayList<>();
        for (User user : recipients) {
            if (chatServer.validateUser(user.getName())) {
                validRecipients.add(user);
            }
        }
        if (validRecipients.isEmpty()) return;

        Message msg = new Message(this, validRecipients, timestamp, content);
        chatServer.sendMessage(msg);
        chatHistory.addMessage(msg);
        memento = new MessageMemento(content, timestamp);
    }

    public void receiveMessage(Message message) {
        chatHistory.addMessage(message);
    }

    public void undoMessageSent() {
        if (memento != null) {
            chatHistory.undoLastMessageSent(memento.getMessage(), memento.getTimeStamp());
            memento = null;
        }
    }

    public String displayUserChatHistory() {
        return name + "'s Chat History:\n" + 
                "#############################################\n" + 
                chatHistory.displayMessageHistory() +
                "#############################################\n";
    }

    public ChatHistory getChatHistory() { return chatHistory; }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return chatHistory.iterator(userToSearchWith);
    }
}
