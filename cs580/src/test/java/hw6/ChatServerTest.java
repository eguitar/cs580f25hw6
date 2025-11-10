package hw6;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ChatServerTest {
    
    private ChatServer server;
    private User alice, bob, carol;

    @BeforeEach
    public void setup() {
        server = new ChatServer();
        alice = new User("Alice");
        bob = new User("Bob");
        carol = new User("Carol");

        server.registerUser(alice);
        server.registerUser(bob);
        server.registerUser(carol);
    }

    @Test
    public void testRegisterAndUnregisterUser() {
        alice.sendMessage(new ArrayList<>() {{ add(bob); }}, "Hello Bob!", server);
        assertEquals(1, bob.getChatHistory().getNumMessages());
        server.unregisterUser(alice);
        alice.sendMessage(new ArrayList<>() {{ add(bob); }}, "Are you there?", server);
        assertEquals(1, bob.getChatHistory().getNumMessages());
    }

    @Test
    public void testSendMessageDelivered() {
        alice.sendMessage(new ArrayList<>() {{ add(bob); }}, "Hey Bob!", server);
        assertEquals(1, bob.getChatHistory().getNumMessages());
        assertTrue(bob.getChatHistory().containsMessage("Hey Bob!"));
    }

    @Test
    public void testBlockingPreventsMessage() {
        server.blockUser(bob, alice);
        alice.sendMessage(new ArrayList<>() {{ add(bob); }}, "Blocked Message", server);
        assertEquals(0, bob.getChatHistory().getNumMessages());
    }

    @Test
    public void testSendMessageToMultipleRecipients() {
        alice.sendMessage(new ArrayList<>() {{ add(bob); add(carol); }}, "Group Message", server);
        
        assertEquals(1, bob.getChatHistory().getNumMessages());
        assertEquals(1, carol.getChatHistory().getNumMessages());
        assertTrue(bob.getChatHistory().containsMessage("Group Message"));
        assertTrue(carol.getChatHistory().containsMessage("Group Message"));
    }

    @Test
    public void testBlockUnregisteredUser() {
        server.unregisterUser(carol);
        server.blockUser(bob, carol);
        alice.sendMessage(new ArrayList<>() {{ add(carol); }}, "Hello Carol?", server);
        assertEquals(0, carol.getChatHistory().getNumMessages());
    }
}