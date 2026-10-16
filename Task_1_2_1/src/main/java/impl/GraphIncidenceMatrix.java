package impl;

import graph.Edge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация графа на основе матрицы инцидентности (Incidence Matrix).
 * Строки соответствуют вершинам, столбцы — рёбрам (1 — источник, -1 — сток).
 *
 * @param <T> тип данных вершин графа
 */
public class GraphIncidenceMatrix<T> extends AbstractGraph<T> {
    private final List<T> vertices = new ArrayList<>();
    private final List<Edge<T>> edges = new ArrayList<>();
    private final List<List<Integer>> matrix = new ArrayList<>();

    @Override
    public void addVertex(T vertex) {
        validateVertex(vertex);
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            List<Integer> newRow = new ArrayList<>(Collections.nCopies(edges.size(), 0));
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
            Iterator<Edge<T>> iterator = edges.iterator();
            int edgeIdx = 0;
            while (iterator.hasNext()) {
                Edge<T> edge = iterator.next();
                if (edge.getSource().equals(vertex) || edge.getDestination().equals(vertex)) {
                    iterator.remove();
                    for (List<Integer> row : matrix) {
                        row.remove(edgeIdx);
                    }
                } else {
                    edgeIdx++;
                }
            }
        }
    }

    @Override
    public void addEdge(T source, T destination) {
        validateVertex(source);
        validateVertex(destination);
        addVertex(source);
        addVertex(destination);
        Edge<T> newEdge = new Edge<>(source, destination);
        if (edges.contains(newEdge)) {
            return;
        }

        edges.add(newEdge);
        int destIdx = vertices.indexOf(destination);

        for (int i = 0; i < vertices.size(); i++) {
            List<Integer> row = matrix.get(i);
            if (vertices.get(i).equals(source)) {
                row.add(1);
            } else if (i == destIdx) {
                row.add(-1);
            } else {
                row.add(0);
            }
        }
    }

    @Override
    public void removeEdge(T source, T destination) {
        if (source == null || destination == null) {
            return;
        }
        Edge<T> targetEdge = new Edge<>(source, destination);
        int edgeIdx = edges.indexOf(targetEdge);
        if (edgeIdx != -1) {
            edges.remove(edgeIdx);
            for (List<Integer> row : matrix) {
                row.remove(edgeIdx);
            }
        }
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        validateVertex(vertex);
        List<T> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex != -1) {
            for (int e = 0; e < edges.size(); e++) {
                if (matrix.get(vertexIndex).get(e) == 1) {
                    neighbors.add(edges.get(e).getDestination());
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
        if (!(o instanceof GraphIncidenceMatrix)) {
            return false;
        }
        GraphIncidenceMatrix<?> that = (GraphIncidenceMatrix<?>) o;
        return Objects.equals(vertices, that.vertices) && Objects.equals(edges, that.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, edges);
    }

    @Override
    public String toString() {
        return "GraphIncidenceMatrix{vertices=" + vertices + ", edges=" + edges + "}";
    }
}