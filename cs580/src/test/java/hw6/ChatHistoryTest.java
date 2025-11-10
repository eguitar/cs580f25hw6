package hw6;

import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ChatHistoryTest {
    private ChatHistory chatHistory;
    private User sender, recipient;
    private Message message;

    @BeforeEach
    public void setup() {
        chatHistory = new ChatHistory();
        sender = new User("Alice");
        recipient = new User("Bob");
        message = new Message(sender, new ArrayList<>() {{ add(recipient); }}, LocalDateTime.now(), "Hello World");
    }

    @Test
    public void testAddAndGetLastMessage() {
        chatHistory.addMessage(message);
        Message last = chatHistory.getLastMessageSent(sender);
        assertNotNull(last);
        assertEquals("Hello World", last.getMessageContent());
        assertEquals(sender.getName(), last.getSender().getName());
    }

    @Test
    public void testUndoLastMessageSent() {
        chatHistory.addMessage(message);
        chatHistory.undoLastMessageSent(message.getMessageContent(), message.getTimeStamp());
        Message last = chatHistory.getLastMessageSent(sender);
        assertNull(last);
    }
}

