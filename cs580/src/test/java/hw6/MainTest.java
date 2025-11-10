package hw6;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    
    private ChatServer chatServer;
    private User alice, bob, john;

    @BeforeEach
    public void setup() {
        chatServer = new ChatServer();
        alice = new User("Alice");
        bob = new User("Bob");
        john = new User("John");

        chatServer.registerUser(alice);
        chatServer.registerUser(bob);
        chatServer.registerUser(john);
    }

    @Test
    public void testBasicMessageSending() {
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        recipients.add(john);
        
        alice.sendMessage(recipients, "Hello Bob and John!", chatServer);
        
        assertEquals(1, bob.getChatHistory().getNumMessages());
        assertEquals(1, john.getChatHistory().getNumMessages());
        assertTrue(bob.getChatHistory().containsMessage("Hello Bob and John!"));
        assertTrue(john.getChatHistory().containsMessage("Hello Bob and John!"));
    }

    @Test
    public void testMultipleMessagesExchange() {
        ArrayList<User> recipients1 = new ArrayList<>();
        recipients1.add(bob);
        recipients1.add(john);
        alice.sendMessage(recipients1, "Hello Bob and John!", chatServer);

        ArrayList<User> recipients2 = new ArrayList<>();
        recipients2.add(alice);
        bob.sendMessage(recipients2, "Hi Alice!", chatServer);

        ArrayList<User> recipients3 = new ArrayList<>();
        recipients3.add(alice);
        john.sendMessage(recipients3, "Good morning Alice!", chatServer);

        assertEquals(3, alice.getChatHistory().getNumMessages());
        assertTrue(alice.getChatHistory().containsMessage("Hello Bob and John!"));
        assertTrue(alice.getChatHistory().containsMessage("Hi Alice!"));
        assertTrue(alice.getChatHistory().containsMessage("Good morning Alice!"));
    }

    @Test
    public void testUndoMessageSent() {
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        
        alice.sendMessage(recipients, "Message to undo", chatServer);
        assertEquals(1, alice.getChatHistory().getNumMessages());
        assertTrue(alice.getChatHistory().containsMessage("Message to undo"));
        
        alice.undoMessageSent();
        assertEquals(0, alice.getChatHistory().getNumMessages());
        assertFalse(alice.getChatHistory().containsMessage("Message to undo"));
    }

    @Test
    public void testBlockingPreventsMessageDelivery() {
        chatServer.blockUser(bob, john);

        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        john.sendMessage(recipients, "Hey Bob, can you see this?", chatServer);

        assertEquals(0, bob.getChatHistory().getNumMessages());
        assertFalse(bob.getChatHistory().containsMessage("Hey Bob, can you see this?"));
        
        assertEquals(1, john.getChatHistory().getNumMessages());
        assertTrue(john.getChatHistory().containsMessage("Hey Bob, can you see this?"));
    }

    @Test
    public void testIteratorWithSpecificUser() {
        ArrayList<User> recipients1 = new ArrayList<>();
        recipients1.add(bob);
        alice.sendMessage(recipients1, "Message 1 to Bob", chatServer);

        ArrayList<User> recipients2 = new ArrayList<>();
        recipients2.add(john);
        alice.sendMessage(recipients2, "Message to John", chatServer);

        ArrayList<User> recipients3 = new ArrayList<>();
        recipients3.add(alice);
        bob.sendMessage(recipients3, "Reply from Bob", chatServer);

        Iterator<Message> iterator = alice.iterator(bob);
        
        int count = 0;
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            count++;
            assertTrue(msg.getSender().equals(alice) || msg.getSender().equals(bob) ||
                      msg.getRecipients().contains(alice) || msg.getRecipients().contains(bob));
        }
        
        assertEquals(2, count);
    }

    @Test
    public void testIteratorNoMessagesWithUser() {
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(john);
        alice.sendMessage(recipients, "Only to John", chatServer);

        Iterator<Message> iterator = bob.iterator(alice);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testUnregisterUserPreventsMessageSending() {
        chatServer.unregisterUser(alice);
        
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        alice.sendMessage(recipients, "Message after unregister", chatServer);
        
        assertEquals(0, bob.getChatHistory().getNumMessages());
        assertFalse(bob.getChatHistory().containsMessage("Message after unregister"));
    }

    @Test
    public void testSendMessageToUnregisteredRecipient() {
        chatServer.unregisterUser(bob);
        
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        recipients.add(john);
        alice.sendMessage(recipients, "Partial delivery", chatServer);
        
        assertEquals(0, bob.getChatHistory().getNumMessages());
        
        assertEquals(1, john.getChatHistory().getNumMessages());
        assertTrue(john.getChatHistory().containsMessage("Partial delivery"));
    }

    @Test
    public void testSendMessageWithNullChatServer() {
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        
        alice.sendMessage(recipients, "This won't send", null);
        
        assertEquals(0, bob.getChatHistory().getNumMessages());
        assertEquals(0, alice.getChatHistory().getNumMessages());
    }

    @Test
    public void testSendMessageWithNoValidRecipients() {
        User unregistered = new User("Unregistered");
        
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(unregistered);
        
        alice.sendMessage(recipients, "No valid recipients", chatServer);
        
        assertEquals(0, alice.getChatHistory().getNumMessages());
    }

    @Test
    public void testUndoWithNoMessageSent() {
        assertDoesNotThrow(() -> alice.undoMessageSent());
        assertEquals(0, alice.getChatHistory().getNumMessages());
    }

    @Test
    public void testBlockingDoesNotAffectOtherUsers() {
        chatServer.blockUser(bob, john);

        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        recipients.add(alice);
        john.sendMessage(recipients, "Message to both", chatServer);

        assertEquals(0, bob.getChatHistory().getNumMessages());
        assertFalse(bob.getChatHistory().containsMessage("Message to both"));
        
        assertEquals(1, alice.getChatHistory().getNumMessages());
        assertTrue(alice.getChatHistory().containsMessage("Message to both"));
    }

    @Test
    public void testMultipleUndoOperations() {
        ArrayList<User> recipients = new ArrayList<>();
        recipients.add(bob);
        
        alice.sendMessage(recipients, "First message", chatServer);
        alice.sendMessage(recipients, "Second message", chatServer);
        
        assertEquals(2, alice.getChatHistory().getNumMessages());
        
        alice.undoMessageSent();
        assertEquals(1, alice.getChatHistory().getNumMessages());
        assertTrue(alice.getChatHistory().containsMessage("First message"));
        assertFalse(alice.getChatHistory().containsMessage("Second message"));
        
        alice.undoMessageSent();
        assertEquals(1, alice.getChatHistory().getNumMessages());
    }

    @Test
    public void testValidateUser() {
        assertTrue(chatServer.validateUser("Alice"));
        assertTrue(chatServer.validateUser("Bob"));
        assertFalse(chatServer.validateUser("NonExistent"));
        
        chatServer.unregisterUser(alice);
        assertFalse(chatServer.validateUser("Alice"));
    }

    @Test
    public void testIteratorWithMultipleMessages() {
        ArrayList<User> recipients1 = new ArrayList<>();
        recipients1.add(bob);
        alice.sendMessage(recipients1, "Alice to Bob 1", chatServer);

        ArrayList<User> recipients2 = new ArrayList<>();
        recipients2.add(alice);
        bob.sendMessage(recipients2, "Bob to Alice 1", chatServer);

        ArrayList<User> recipients3 = new ArrayList<>();
        recipients3.add(bob);
        alice.sendMessage(recipients3, "Alice to Bob 2", chatServer);

        Iterator<Message> iterator = alice.iterator(bob);
        
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        
        assertEquals(3, count);
    }
}