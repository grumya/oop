package graph;

import java.util.Objects;

/**
 * Класс, представляющий направленное ребро графа.
 * Связывает вершину-источник с вершиной-назначением.
 *
 * @param <T> тип значения вершины
 */
public class Edge<T> {
    private final T source;
    private final T destination;

    /**
     * Создает новое ребро графа.
     *
     * @param source вершина-источник
     * @param destination вершина-назначение
     */
    public Edge(T source, T destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Возвращает вершину-источник ребра.
     *
     * @return вершина-источник
     */
    public T getSource() {
        return source;
    }

    /**
     * Возвращает вершину-назначение ребра.
     *
     * @return вершина-назначение
     */
    public T getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(source, edge.source) && Objects.equals(destination, edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}