import java.util.ArrayList;
import java.util.Comparator;
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
    private final int maxSize;
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
        if (internalList.size() >= maxSize) {
            addOldestElement(purgedElements);
        } else {
            for (T el : internalList) {
                if (el.getTimestamp().toEpochMilli() > timeSpanMs) {
                    addOldestElement(purgedElements);
                    break;
                }
            }
        }
        internalList.removeAll(purgedElements);
        internalList.add(element);
        return purgedElements;
    }

    public void addOldestElement(List<T> purgedElements) {
        T oldestElement = internalList.stream().sorted(Comparator.comparing(HasTimestamp::getTimestamp))
                .collect(Collectors.toList()).get(0);
        purgedElements.add(oldestElement);
    }


    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }


}
