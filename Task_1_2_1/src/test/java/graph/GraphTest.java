package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import impl.GraphAdjacencyList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

    private Graph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new GraphAdjacencyList<>();
    }

    @Test
    void testAddVertexAndGetVertices() {
        graph.addVertex("A");
        graph.addVertex("B");

        Set<String> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
    }

    @Test
    void testAddEdgeAndGetNeighbors() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        List<String> neighbors = graph.getNeighbors("A");
        assertNotNull(neighbors);
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
    void testRemoveVertex() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        graph.removeVertex("B");

        Set<String> vertices = graph.getVertices();
        assertFalse(vertices.contains("B"));
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("C"));
        assertTrue(graph.getNeighbors("A").isEmpty());
    }
}