import circularbuffer.CircularBuffer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CircularBufferTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    public void createBufferWithInvalidSize(int value) {
        assertThrows(RuntimeException.class, () -> new CircularBuffer<>(value, Integer.class));
    }

    @Test
    public void verifyBufferOverload() {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(1, Integer.class);
        buffer.put(30);
        assertThrows(RuntimeException.class, () -> {
            buffer.put(50);
        });
    }

    @Test
    public void verifyAbilityToAddElements() {
        int bufferSize = 100;
        CircularBuffer<Integer> buffer = new CircularBuffer<>(bufferSize, Integer.class);
        Integer[] expectedBuffer = IntStream.range(0, bufferSize).boxed().toArray(Integer[]::new);
        Arrays.stream(expectedBuffer).forEach(buffer::put);
        assertArrayEquals(expectedBuffer, buffer.toArray());
    }

    @Test
    public void verifyRestrictionToGetNotFilledElement() {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(1, Integer.class);
        assertThrows(RuntimeException.class, buffer::get);
    }

    @Test
    public void verifyRestrictionToGetElement() {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(1, Integer.class);
        buffer.put(4);
        assertEquals(4, buffer.get());
    }

    @Test
    public void verifyEmptyBuffer() {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(1, Integer.class);
        assertThrows(RuntimeException.class, buffer::toArray);
    }

    @Test
    public void verifyConversionToArray() {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(4, Integer.class);
        buffer.put(2);
        buffer.put(3);
        buffer.put(4);
        buffer.get();
        List<Integer> expectedData = Arrays.asList(3, 4, 2);
        assertEquals(expectedData, contains(buffer.toArray()));
    }

    @Test
    public void verifySorting() {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(4, Integer.class);
        buffer.put(8);
        buffer.put(2);
        buffer.put(1);
        buffer.get();
        buffer.sort(Integer::compareTo);
    }
}
