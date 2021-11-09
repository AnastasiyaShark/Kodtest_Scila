import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
        this.internalList= new ArrayList<>(maxSize);
    }

    /**
     * Adds an element to this list
     *
     * @param element the element to add to this list
     * @return the elements purged by this call
     */
    public List<T> add(T element) {
        List <T> purgedElements = new ArrayList<>();
        if (internalList.size()>=maxSize){//?

        }
        internalList.add(element);
        for (T el: internalList){
            if (el.getTimestamp().toEpochMilli() > timeSpanMs) {
                purgedElements.add(el);
                internalList.remove(el);
            }
        }

        return purgedElements;
    }


    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {


            @Override
            public boolean hasNext() {
                return (current < maxSize);
            }

            @Override
            public T next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }
                current++;
                return arrayList[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

    }
}
