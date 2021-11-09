import java.time.Instant;

public class Event implements HasTimestamp {
    private final Instant timestamp;

    public Event(long epochMs) {
        timestamp = Instant.ofEpochMilli(epochMs);
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }
}
