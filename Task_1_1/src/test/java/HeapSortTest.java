import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
public class HeapSortTest {
    @Test
    void heapsort() {
        int[] array = {5, 4, 3, 2, 1};
        int[] result = HeapSort.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }

    @Test
    void sortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        int[] result = HeapSort.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }

    @Test
    void equalElements() {
        int[] array = {3, 3, 3, 3};
        int[] result = HeapSort.heapsort(array);
        assertArrayEquals(new int[]{3, 3, 3, 3}, result);
    }

    @Test
    void negativeNumbers() {
        int[] array = {-2, 5, -10, 3, 0};
        int[] result = HeapSort.heapsort(array);
        assertArrayEquals(new int[]{-10, -2, 0, 3, 5}, result);
    }

    @Test
    void emptyArray() {
        int[] array = {};
        int[] result = HeapSort.heapsort(array);
        assertArrayEquals(new int[]{}, result);
    }
}
