package hw6.chat;

import java.util.*;

public class ChatServer {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Set<String>> blockedUsers = new HashMap<>();

    public void registerUser(User user) {
        users.put(user.getName(), user);
        blockedUsers.putIfAbsent(user.getName(), new HashSet<>());
    }

    public void unregisterUser(User user) {
        users.remove(user.getName());
        blockedUsers.remove(user.getName());
    }

    public void sendMessage(Message message) {
        for (User recipient : message.getRecipients()) {
            Set<String> blocked = blockedUsers.getOrDefault(recipient.getName(), Collections.emptySet());
            if (!blocked.contains(message.getSender().getName())) {
                recipient.receiveMessage(message);
            }
        }
    }

    public void blockUser(User user, User blockedUser){
        blockedUsers.get(user.getName()).add(blockedUser.getName());
    }
}
