package hw6.chat;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Message {
    private User sender;
    private ArrayList<User> recipients;
    private LocalDateTime timestamp;
    private String content;

    public Message(User sender, ArrayList<User> recipients,
                    LocalDateTime timestamp, String content) {
        this.sender = sender;
        this.recipients = recipients;
        this.timestamp = timestamp;
        this.content = content;
    }

    public User getSender() { return sender; }
    public ArrayList<User> getRecipients() { return recipients; }
    public LocalDateTime getTimeStamp() { return timestamp; }
    public String getMessageContent() { return content; }

    @Override
    public String toString() {
        return "Timestamp: " + timestamp + "\nSender: " + sender + "\nMessage: \n" + content;
    }
}
