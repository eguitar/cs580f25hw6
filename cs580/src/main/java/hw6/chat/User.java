package hw6.chat;

import java.util.*;
import java.time.LocalDateTime;

public class User {
    private String name;
    private ChatServer chatServer;
    private ChatHistory chatHistory;
    
    public User(String name, ChatServer chatServer) {
        this.name = name;
        this.chatServer = chatServer;
        this.chatHistory = new ChatHistory();
    }

    public String getName() { return name; }

    public void sendMessage(ArrayList<User> recipients, String content) {
        LocalDateTime timestamp = LocalDateTime.now();
        Message msg = new Message(this, recipients, timestamp, content);
        chatServer.sendMessage(msg);
        chatHistory.addMessage(msg);
    }

    public void receiveMessage(Message message) {
        
    }

    public void undoMessageSent() {

    }

    public String displayUserChatHistory() {
        return name + "'s Chat History:\n" + chatHistory.displayMessageHistory();
    }
}
