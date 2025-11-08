package hw6.chat;

import java.util.*;

public class SearchMessagesByUserIterator implements Iterator<Message> {
    private User user;
    private ArrayList<Message> messages;
    private int nextIndex = 0;

    public SearchMessagesByUserIterator(User user, ArrayList<Message> messages) {
        this.user = user;
        this.messages = messages;
        advanceToNext();
    }

    private void advanceToNext() {
        while (nextIndex < messages.size()) {
            Message msg = messages.get(nextIndex);
            if (msg.getSender().equals(user) || msg.getRecipients().contains(user)) {
                break;
            }
                nextIndex++;
            }
    }

    @Override
    public boolean hasNext() { return nextIndex < messages.size(); }

    @Override
    public Message next() {
        Message result = messages.get(nextIndex);
        nextIndex++;
        advanceToNext();
        return result;
    }
}