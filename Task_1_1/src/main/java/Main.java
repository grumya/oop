import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = {3, 2, 1};

        HeapSort heapSort = new HeapSort();
        heapSort.heapsort(array);

        System.out.println(Arrays.toString(array));
    }
}
