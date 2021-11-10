import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klassen som ska implementeras:
 * Time bound list for elements added in time order
 * The list holds a limited number of elements that has a timestamp based on:
 * - a time span, when the time span is exceeded, the oldest element(s) are purged
 * - a maximum number of elements, when exceeded the oldest element is purged
 *
 * @param <T>
 */
public class TimeBoundList<T extends HasTimestamp> implements Iterable<T> {
    private final long timeSpanMs;
    private int maxSize;
    private final List<T> internalList;


    /**
     * Creates a time bound list
     *
     * @param timeSpanMs the time span in milliseconds
     * @param maxSize    the maximum number of events
     */
    public TimeBoundList(long timeSpanMs, int maxSize) {
        this.timeSpanMs = timeSpanMs;
        this.maxSize = maxSize;
        this.internalList = new ArrayList<>(maxSize);
    }

    /**
     * Adds an element to this list
     *
     * @param element the element to add to this list
     * @return the elements purged by this call
     */
    public List<T> add(T element) {

        List<T> purgedElements = new ArrayList<>();
        if (internalList.size() >= maxSize ) {
            T oldestElement = internalList.stream().sorted((e1, e2) -> (e1.getTimestamp().compareTo(e2.getTimestamp())))
                    .collect(Collectors.toList()).get(0);
            purgedElements.add(oldestElement);
//            internalList.remove(oldestElement);
        }
//        internalList.add(element);
//        Instant now = Instant.now();
        for (T el : internalList) {
//            if (ChronoUnit.MILLIS.between(now,el.getTimestamp() ) > timeSpanMs){
//                purgedElements.add(el);
//            }
//            if ((now.toEpochMilli() - el.getTimestamp().toEpochMilli()) > timeSpanMs) {
//                System.out.println((now.toEpochMilli() - el.getTimestamp().toEpochMilli()));
//                purgedElements.add(el);
//            }

//            if ((Instant.now().toEpochMilli() - el.getTimestamp().toEpochMilli()) > timeSpanMs) {
//                purgedElements.add(el);
//            }


            if (el.getTimestamp().toEpochMilli() > timeSpanMs) {
                purgedElements.add(el);
            }

        }
        System.out.println(purgedElements);
        internalList.removeAll(purgedElements);
        internalList.add(element);
        return purgedElements;
    }


    @Override
    public Iterator<T> iterator() {

        return internalList.iterator();
    }
}
