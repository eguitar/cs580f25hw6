package hw6;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

        User alice = new User("Alice");
        User bob = new User("Bob");
        User john = new User("John");

        chatServer.registerUser(alice);
        chatServer.registerUser(bob);
        chatServer.registerUser(john);
        // ------------------------------------------------------------------------
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
        // ------------------------------------------------------------------------
        // alice.undoMessageSent();
        // ------------------------------------------------------------------------
        chatServer.blockUser(bob, john);

        ArrayList<User> recipients4 = new ArrayList<>();
        recipients4.add(bob);
        john.sendMessage(recipients4, "Hey Bob, can you see this?", chatServer);
        // ------------------------------------------------------------------------
        System.out.println(alice.displayUserChatHistory());
        System.out.println(bob.displayUserChatHistory());
        System.out.println(john.displayUserChatHistory());
        // ------------------------------------------------------------------------
        System.out.println("Alice's messages with Bob:");

        Iterator<Message> iterator = alice.iterator(bob);
        System.out.println("#############################################");
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            System.out.println(msg);
            System.out.println("---------------------");
        }
        System.out.println("#############################################");
    }

}
