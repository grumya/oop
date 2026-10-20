package impl;

import java.util.List;
import java.util.Set;

import graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphAdjacencyListTest {

    private GraphAdjacencyList<String> graph;

    @BeforeEach
    void setUp() {
        graph = new GraphAdjacencyList<>();
    }

    @Test
    void testAddVertexAndGetVertices() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("A"); // дубликат не должен ломать структуру

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
    void testAddEdgeImplicitlyAddsVertices() {
        graph.addEdge("A", "B");

        assertTrue(graph.getVertices().contains("A"));
        assertTrue(graph.getVertices().contains("B"));
        assertEquals(List.of("B"), graph.getNeighbors("A"));
    }

    @Test
    void testAddDuplicateEdgeDoesNotDuplicate() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");

        assertEquals(1, graph.getNeighbors("A").size());
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
    void testRemoveVertexCleansUpIncomingEdges() {
        graph.addEdge("A", "B");
        graph.addEdge("C", "B");
        graph.addEdge("B", "D");

        graph.removeVertex("B");

        assertFalse(graph.getVertices().contains("B"));
        assertTrue(graph.getNeighbors("A").isEmpty());
        assertTrue(graph.getNeighbors("C").isEmpty());
        assertEquals(Set.of("A", "C", "D"), graph.getVertices());
    }

    @Test
    void testRemoveVertexNullSafe() {
        assertDoesNotThrow(() -> graph.removeVertex(null));
    }

    @Test
    void testGetNeighborsNonExistent() {
        assertTrue(graph.getNeighbors("X").isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        GraphAdjacencyList<String> g1 = new GraphAdjacencyList<>();
        GraphAdjacencyList<String> g2 = new GraphAdjacencyList<>();

        g1.addEdge("A", "B");
        g2.addEdge("A", "B");

        assertEquals(g1, g1);
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());

        GraphAdjacencyList<String> g3 = new GraphAdjacencyList<>();
        g3.addEdge("A", "C");

        assertNotEquals(g1, g3);
        assertNotEquals(g1, null);
        assertNotEquals(g1, "SomeString");
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B");
        String str = graph.toString();
        assertTrue(str.contains("GraphAdjacencyList"));
        assertTrue(str.contains("A"));
        assertTrue(str.contains("B"));
    }
}