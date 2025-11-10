package hw6;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private ChatServer server;
    private User alice, bob;

    @BeforeEach
    public void setup() {
        server = new ChatServer();
        alice = new User("Alice");
        bob = new User("Bob");
        server.registerUser(alice);
        server.registerUser(bob);
    }

    @Test
    public void testSendMessageAndReceive() {
        alice.sendMessage(new ArrayList<>() {{ add(bob); }}, "Hi Bob",server);
        assertEquals(1, bob.getChatHistory().getNumMessages());
    }

    @Test
    public void testUndoMessage() {
        alice.sendMessage(new ArrayList<>() {{ add(bob); }}, "Message to Undo",server);
        assertEquals(1, alice.getChatHistory().getNumMessages());

        alice.undoMessageSent();
        assertEquals(0, alice.getChatHistory().getNumMessages());
    }

    @Test
    public void testBlockUserPreventsReceiving() {
        server.blockUser(bob,alice);
        alice.sendMessage(new ArrayList<>() {{ add(bob); }}, "Blocked message",server);
        assertEquals(0, bob.getChatHistory().getNumMessages());
    }
}