package graph;

import java.util.List;
import java.util.Set;

/**
 * Общий интерфейс графа, поддерживающий вершины произвольного типа T.
 * Задает базовый контракт для всех реализаций (список смежности, матрицы).
 *
 * @param <T> тип данных вершин графа
 */
public interface Graph<T> {

    /**
     * Добавляет новую вершину в граф.
     *
     * @param vertex вершина для добавления
     */
    void addVertex(T vertex);

    /**
     * Удаляет вершину из графа и все инцидентные ей рёбра.
     *
     * @param vertex вершина для удаления
     */
    void removeVertex(T vertex);

    /**
     * Добавляет ориентированное ребро между двумя вершинами.
     *
     * @param source вершина-источник
     * @param destination вершина-назначение
     */
    void addEdge(T source, T destination);

    /**
     * Удаляет ориентированное ребро между вершинами.
     *
     * @param source вершина-источник
     * @param destination вершина-назначение
     */
    void removeEdge(T source, T destination);

    /**
     * Возвращает список соседей (вершин, в которые ведет ребро из данной).
     *
     * @param vertex вершина, для которой ищутся соседи
     * @return список соседних вершин
     */
    List<T> getNeighbors(T vertex);

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return множество вершин
     */
    Set<T> getVertices();
}