package sort;

import graph.Graph;
import java.util.List;

/**
 * Интерфейс для алгоритмов топологической сортировки графа.
 */
public interface TopologySort {

    /**
     * Выполняет топологическую сортировку заданного графа.
     *
     * @param graph граф для сортировки
     * @param <T>   тип данных вершин
     * @return отсортированный список вершин
     * @throws GraphCycleException если в графе найден цикл
     */
    <T> List<T> sort(Graph<T> graph) throws GraphCycleException;
}