package circularbuffer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.System.arraycopy;
import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class CircularBuffer<T> {

    private int bufferSize;

    private T[] circularBuffer;

    private int head = 0;

    private int tail = 0;

    private int currentSize = 0;

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public CircularBuffer(int bufferSize, Class<T> type) {
        if (bufferSize <= 0)
            throw new RuntimeException("Buffer size should be greater than 0");
        this.bufferSize = bufferSize;
        this.type = type;
        circularBuffer = (T[]) newInstance(type, bufferSize);
    }

    public void put(T t) {
        if (head == tail && !isNull(circularBuffer[tail])) {
            throw new RuntimeException("Buffer is full");
        }
        circularBuffer[head++] = t;
        head %= bufferSize;
        currentSize++;
    }

    public T get() {
        if (head == tail && !(currentSize == bufferSize)) {
            throw new RuntimeException("Unable to retrieve not existing element");
        }
        tail %= bufferSize;
        return circularBuffer[tail++];
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] newArray = (T[]) newInstance(type, currentSize);
        Iterator<T> iterator = new CircularBufferIterator();
        range(0, currentSize).forEach(i -> newArray[i] = iterator.next());
        return newArray;
    }

    public List<T> asList() {
        return stream(toArray()).collect(toList());
    }

    public void addAll(List<? extends T> toAdd) {
        if (toAdd.size() > bufferSize - currentSize)
            throw new RuntimeException("There is no free space in the buffer");
        for (T element : toAdd) {
            circularBuffer[++currentSize] = element;
        }
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> comparator) {
        if (!isEmpty()) {
            T[] sortedBuffer = (T[]) newInstance(type, currentSize);
            arraycopy(circularBuffer, 0, sortedBuffer, 0, currentSize);
            Arrays.sort(sortedBuffer, 0, currentSize, comparator);
            System.arraycopy(sortedBuffer, 0, circularBuffer, 0, currentSize);
            tail = 0;
            head = currentSize;
        }
    }

    private class CircularBufferIterator implements Iterator<T> {

        private int pointer = tail;

        public boolean hasNext() {
            return pointer < currentSize;
        }

        public T next() {
            T element = circularBuffer[pointer];
            if (tail == head)
                pointer = (pointer - 1) % currentSize;
            else
                pointer = (pointer + 1) % currentSize;
            if (!hasNext())
                throw new NoSuchElementException();
            return element;
        }
    }
}
