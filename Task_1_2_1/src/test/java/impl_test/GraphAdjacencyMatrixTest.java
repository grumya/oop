package impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GraphAdjacencyMatrixTest {

    private GraphAdjacencyMatrix<String> graph;

    @BeforeEach
    void setUp() {
        graph = new GraphAdjacencyMatrix<>();
    }

    @Test
    void testAddVertexAndGetVertices() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("A"); // дубликат не должен плодить строки/столбцы

        Set<String> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
    }

    @Test
    void testAddVertexNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex(null));
    }

    @Test
    void testAddEdgeAndGetNeighbors() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        graph.removeEdge("A", "B");

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains("C"));
        assertFalse(neighbors.contains("B"));
    }

    @Test
    void testRemoveEdgeNullSafe() {
        assertDoesNotThrow(() -> graph.removeEdge(null, "B"));
        assertDoesNotThrow(() -> graph.removeEdge("A", null));
    }

    @Test
    void testRemoveVertexResizesMatrix() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");

        // Удаляем среднюю вершину B, матрица должна перестроиться
        graph.removeVertex("B");

        Set<String> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertFalse(vertices.contains("B"));

        // У вершины A останется связь только с C
        List<String> neighborsA = graph.getNeighbors("A");
        assertEquals(1, neighborsA.size());
        assertTrue(neighborsA.contains("C"));
    }

    @Test
    void testRemoveVertexNullSafe() {
        assertDoesNotThrow(() -> graph.removeVertex(null));
    }

    @Test
    void testGetNeighborsNonExistentOrNull() {
        assertTrue(graph.getNeighbors("X").isEmpty());
        assertThrows(IllegalArgumentException.class, () -> graph.getNeighbors(null));
    }

    @Test
    void testEqualsAndHashCode() {
        GraphAdjacencyMatrix<String> g1 = new GraphAdjacencyMatrix<>();
        GraphAdjacencyMatrix<String> g2 = new GraphAdjacencyMatrix<>();

        g1.addEdge("A", "B");
        g2.addEdge("A", "B");

        assertEquals(g1, g1);
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());

        GraphAdjacencyMatrix<String> g3 = new GraphAdjacencyMatrix<>();
        g3.addEdge("A", "C");

        assertNotEquals(g1, g3);
        assertNotEquals(g1, null);
        assertNotEquals(g1, "NotAMatrix");
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B");
        String str = graph.toString();
        assertTrue(str.contains("GraphAdjacencyMatrix"));
        assertTrue(str.contains("A"));
        assertTrue(str.contains("B"));
    }
}