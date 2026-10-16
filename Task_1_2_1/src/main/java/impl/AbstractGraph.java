package impl;

import graph.Graph;
import java.util.List;
import java.util.Set;

/**
 * Абстрактный базовый класс для реализации графа.
 * Реализует общую логику валидации (например, проверку на null),
 * следуя принципу DRY (Don't Repeat Yourself).
 *
 * @param <T> тип данных вершин графа
 */
public abstract class AbstractGraph<T> implements Graph<T> {

    /**
     * Проверяет, что переданная вершина не равна null.
     *
     * @param vertex вершина для проверки
     * @throws IllegalArgumentException если вершина равна null
     */
    protected void validateVertex(T vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
    }

    @Override
    public abstract void addVertex(T vertex);

    @Override
    public abstract void removeVertex(T vertex);

    @Override
    public abstract void addEdge(T source, T destination);

    @Override
    public abstract void removeEdge(T source, T destination);

    @Override
    public abstract List<T> getNeighbors(T vertex);

    @Override
    public abstract Set<T> getVertices();
}