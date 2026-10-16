package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация графа на основе матрицы смежности (Adjacency Matrix).
 * Связи между вершинами хранятся в виде двумерной матрицы чисел (0 и 1).
 *
 * @param <T> тип данных вершин графа
 */
public class GraphAdjacencyMatrix<T> extends AbstractGraph<T> {
    private final List<T> vertices = new ArrayList<>();
    private final List<List<Integer>> matrix = new ArrayList<>();

    @Override
    public void addVertex(T vertex) {
        validateVertex(vertex);
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            for (List<Integer> row : matrix) {
                row.add(0);
            }
            List<Integer> newRow = new ArrayList<>(Collections.nCopies(vertices.size(), 0));
            matrix.add(newRow);
        }
    }

    @Override
    public void removeVertex(T vertex) {
        if (vertex == null) {
            return;
        }
        int index = vertices.indexOf(vertex);
        if (index != -1) {
            vertices.remove(index);
            matrix.remove(index);
            for (List<Integer> row : matrix) {
                row.remove(index);
            }
        }
    }

    @Override
    public void addEdge(T source, T destination) {
        validateVertex(source);
        validateVertex(destination);
        addVertex(source);
        addVertex(destination);
        int srcIdx = vertices.indexOf(source);
        int destIdx = vertices.indexOf(destination);
        matrix.get(srcIdx).set(destIdx, 1);
    }

    @Override
    public void removeEdge(T source, T destination) {
        if (source == null || destination == null) {
            return;
        }
        int srcIdx = vertices.indexOf(source);
        int destIdx = vertices.indexOf(destination);
        if (srcIdx != -1 && destIdx != -1) {
            matrix.get(srcIdx).set(destIdx, 0);
        }
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        validateVertex(vertex);
        List<T> neighbors = new ArrayList<>();
        int index = vertices.indexOf(vertex);
        if (index != -1) {
            List<Integer> row = matrix.get(index);
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i) == 1) {
                    neighbors.add(vertices.get(i));
                }
            }
        }
        return neighbors;
    }

    @Override
    public Set<T> getVertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphAdjacencyMatrix)){
            return false;
        }
        GraphAdjacencyMatrix<?> that = (GraphAdjacencyMatrix<?>) o;
        return Objects.equals(vertices, that.vertices) && Objects.equals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, matrix);
    }

    @Override
    public String toString() {
        return "GraphAdjacencyMatrix{vertices=" + vertices + ", matrix=" + matrix + "}";
    }
}