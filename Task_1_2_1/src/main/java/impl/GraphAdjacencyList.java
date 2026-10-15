package impl;

import java.util.*;

/**
 * Реализация графа на основе списка смежности (Adjacency List).
 * Самый эффективный способ хранения разреженных графов в памяти.
 *
 * @param <T> тип данных вершин графа
 */
public class GraphAdjacencyList<T> extends AbstractGraph<T> {
    private final Map<T, List<T>> adjacencyMap = new HashMap<>();

    @Override
    public void addVertex(T vertex) {
        validateVertex(vertex);
        adjacencyMap.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(T vertex) {
        if (vertex == null) return;
        adjacencyMap.remove(vertex);
        for (List<T> neighbors : adjacencyMap.values()) {
            neighbors.remove(vertex);
        }
    }

    @Override
    public void addEdge(T source, T destination) {
        validateVertex(source);
        validateVertex(destination);
        addVertex(source);
        addVertex(destination);
        List<T> neighbors = adjacencyMap.get(source);
        if (!neighbors.contains(destination)) {
            neighbors.add(destination);
        }
    }

    @Override
    public void removeEdge(T source, T destination) {
        if (source == null || destination == null) return;
        if (adjacencyMap.containsKey(source)) {
            adjacencyMap.get(source).remove(destination);
        }
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        validateVertex(vertex);
        return adjacencyMap.getOrDefault(vertex, Collections.emptyList());
    }

    @Override
    public Set<T> getVertices() {
        return adjacencyMap.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphAdjacencyList)) return false;
        GraphAdjacencyList<?> that = (GraphAdjacencyList<?>) o;
        return Objects.equals(adjacencyMap, that.adjacencyMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjacencyMap);
    }

    @Override
    public String toString() {
        return "GraphAdjacencyList{" + adjacencyMap + "}";
    }
}