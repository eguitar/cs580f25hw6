package hw6;

import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class MessageMementoTest {
    private MessageMemento memento;

    @BeforeEach
    public void setup() {
        memento = new MessageMemento("Saved Content", LocalDateTime.of(2023, 11, 10, 2, 0));
    }

    @Test
    public void testProperties() {
        assertEquals("Saved Content", memento.getMessage());
        assertEquals(LocalDateTime.of(2023, 11, 10, 2, 0), memento.getTimeStamp());
    }
}