/**
 * сортировка массива целых чисел методом пирамидальной сортировки
 */
public class HeapSort {

    /**
     * сортирует массив по возрастанию с помощью пирамиды
     *
     * @param array массив для сортировки
     * @return отсортированный массив
     */
    public static int[] heapsort(int[] array) {
        int n = array.length;

        // строим максимальную кучу
        for (int i = n / 2 - 1; i >= 0; i--) {
            fixHeap(array, n, i);
        }

        // переносим максимальные элементы в конец массива
        for (int i = n - 1; i > 0; i--) {

            // меняем первый и последний элементы кучи местами
            int tmp = array[0];
            array[0] = array[i];
            array[i] = tmp;

            // восстанавливаем кучу
            fixHeap(array, i, 0);
        }

        return array;
    }

    /**
     * восстанавливает свойства максимальной кучи.
     *
     * @param arr массив
     * @param size размер кучи
     * @param i индекс текущего элемента
     */
    private static void fixHeap(int[] arr, int size, int i) {
        while (true) {
            // считаем текущий элемент максимальным
            int biggest = i;

            // находим индексы потомков
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            // проверяем левого потомка
            if (left < size && arr[left] > arr[biggest]) {
                biggest = left;
            }

            // проверяем правого потомка
            if (right < size && arr[right] > arr[biggest]) {
                biggest = right;
            }

            // если текущий элемент уже максимальный, заканчиваем
            if (biggest == i) {
                break;
            }

            // меняем текущий элемент с максимальным потомком
            int tmp = arr[i];
            arr[i] = arr[biggest];
            arr[biggest] = tmp;

            // продолжаем проверку ниже
            i = biggest;
        }
    }
}