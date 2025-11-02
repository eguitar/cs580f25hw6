package hw6.chat;

import java.time.LocalDateTime;

public class MessageMemento {
    private String content;
    private LocalDateTime timestamp;

    public MessageMemento(String content, LocalDateTime timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getMessage() { return this.content; }
    public LocalDateTime getTimeStamp() { return this.timestamp; }
}
