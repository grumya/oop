/**
 * Сортировка массива целых чисел методом пирамидальной сортировки.
 */
public class HeapSort {

    private HeapSort() {
    }

    /**
     * Сортирует массив по возрастанию с помощью пирамиды.
     *
     * @param array массив для сортировки
     * @return отсортированный массив
     */
    public static int[] heapsort(int[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            fixHeap(array, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int tmp = array[0];
            array[0] = array[i];
            array[i] = tmp;

            fixHeap(array, i, 0);
        }

        return array;
    }

    /**
     * Восстанавливает свойства максимальной кучи.
     *
     * @param arr массив
     * @param size размер кучи
     * @param i индекс текущего элемента
     */
    private static void fixHeap(int[] arr, int size, int i) {
        while (true) {
            int biggest = i;

            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && arr[left] > arr[biggest]) {
                biggest = left;
            }

            if (right < size && arr[right] > arr[biggest]) {
                biggest = right;
            }

            if (biggest == i) {
                break;
            }

            int tmp = arr[i];
            arr[i] = arr[biggest];
            arr[biggest] = tmp;

            i = biggest;
        }
    }
}