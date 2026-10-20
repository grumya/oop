import java.util.Arrays;

/**
 * Главный класс программы для запуска алгоритма сортировки.
 */
public class Main {
    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        int[] array = {10, 3, 7, -5, 100, 0};

        HeapSort.heapsort(array);

        System.out.println(Arrays.toString(array));
    }
}
