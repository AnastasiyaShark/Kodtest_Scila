import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeBoundListTest {
    @Test
    public void withinWindowAndSizeTest() {
        TimeBoundList<Event> timeBoundList = new TimeBoundList<>(1000, 10);
        List<Event> eventList = IntStream.range(0, 10)
                .mapToObj(Event::new)
                .collect(Collectors.toList());
        eventList.forEach(timeBoundList::add);
        AtomicInteger count = new AtomicInteger();
        timeBoundList.forEach(e -> assertEquals(eventList.get(count.getAndIncrement()), e));
    }

    @Test
    public void exceedWindowTest() {
        TimeBoundList<Event> timeBoundList = new TimeBoundList<>(5, 10);
        List<Event> eventList = IntStream.range(0, 10)
                .mapToObj(Event::new)
                .collect(Collectors.toList());
        eventList.forEach(timeBoundList::add);
        AtomicInteger count = new AtomicInteger(5);
        timeBoundList.forEach(e -> assertEquals(eventList.get(count.getAndIncrement()), e));
    }

    @Test
    public void exceedSizeTest() {
        TimeBoundList<Event> timeBoundList = new TimeBoundList<>(1000, 5);
        List<Event> eventList = IntStream.range(0, 10)
                .mapToObj(Event::new)
                .collect(Collectors.toList());
        eventList.forEach(timeBoundList::add);
        AtomicInteger count = new AtomicInteger(5);
        timeBoundList.forEach(e -> assertEquals(eventList.get(count.getAndIncrement()), e));
    }

    @Test
    public void purgeTest() {
        TimeBoundList<Event> timeBoundList = new TimeBoundList<>(1000, 5);
        List<Event> eventList = IntStream.range(0, 10)
                .mapToObj(Event::new)
                .collect(Collectors.toList());
        List<Event> purgedList = new ArrayList<>();
        eventList.forEach(e -> purgedList.addAll(timeBoundList.add(e)));
        AtomicInteger count = new AtomicInteger(5);
        assertEquals(purgedList, eventList.subList(0, 5));
    }


    @Test
    public void todoTest() {
        //TODO
    }


}
