package hw6.chat;

import java.time.LocalDateTime;

public class MessageMemento {
    private String message;
    private LocalDateTime timestamp;

    public MessageMemento(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() { return this.message; }
    public LocalDateTime getTimeStamp() { return this.timestamp; }

    public void setMessage(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
