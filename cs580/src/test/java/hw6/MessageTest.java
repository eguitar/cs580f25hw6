package hw6;

import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    private Message message;
    private User sender, recipient;

    @BeforeEach
    public void setup() {
        sender = new User("Alice");
        recipient = new User("Bob");
        message = new Message(sender, new ArrayList<>() {{ add(recipient); }},
            LocalDateTime.of(2023, 11, 10, 1, 0), "Test Message");
    }

    @Test
    public void testProperties() {
        assertEquals("Test Message", message.getMessageContent());
        assertEquals(sender, message.getSender());
        assertTrue(message.getRecipients().contains(recipient));
        assertEquals(LocalDateTime.of(2023, 11, 10, 1, 0), message.getTimeStamp());
    }

    @Test
    public void testToStringFormat() {
        String str = message.toString();
        assertTrue(str.contains("Alice"));
        assertTrue(str.contains("Test Message"));
    }
}