package impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphIncidenceMatrixTest {

    private GraphIncidenceMatrix<String> graph;

    @BeforeEach
    void setUp() {
        graph = new GraphIncidenceMatrix<>();
    }

    @Test
    void testAddVertexAndGetVertices() {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("A"); // дубликат

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
        graph.addEdge("A", "A"); // петля тоже должна корректно обрабатываться

        List<String> neighbors = graph.getNeighbors("A");
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
        assertTrue(neighbors.contains("A"));
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
    void testRemoveVertexRemovesIncidentEdges() {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");

        // Удаляем вершину B, при этом ребра (A, B) и (B, C) должны удалиться
        graph.removeVertex("B");

        Set<String> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertFalse(vertices.contains("B"));

        // У вершины A останется только ребро (A, C)
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
        GraphIncidenceMatrix<String> g1 = new GraphIncidenceMatrix<>();
        GraphIncidenceMatrix<String> g2 = new GraphIncidenceMatrix<>();

        g1.addEdge("A", "B");
        g2.addEdge("A", "B");

        assertEquals(g1, g1);
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());

        GraphIncidenceMatrix<String> g3 = new GraphIncidenceMatrix<>();
        g3.addEdge("A", "C");

        assertNotEquals(g1, g3);
        assertNotEquals(g1, null);
        assertNotEquals(g1, "NotAnIncidenceMatrix");
    }

    @Test
    void testToString() {
        graph.addEdge("A", "B");
        String str = graph.toString();
        assertTrue(str.contains("GraphIncidenceMatrix"));
        assertTrue(str.contains("A"));
        assertTrue(str.contains("B"));
    }
}