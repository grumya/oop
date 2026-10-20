package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void testEdgeCreationAndGetters() {
        Edge<String> edge = new Edge<>("A", "B");
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getDestination());
    }

    @Test
    void testEqualsAndHashCode() {
        Edge<String> edge1 = new Edge<>("A", "B");
        Edge<String> edge2 = new Edge<>("A", "B");
        Edge<String> edge3 = new Edge<>("A", "C");

        // Рефлексивность, симметричность
        assertEquals(edge1, edge1);
        assertEquals(edge1, edge2);
        assertEquals(edge1.hashCode(), edge2.hashCode());

        // Неравенство
        assertNotEquals(edge1, edge3);
        assertNotEquals(edge1, null);
        assertNotEquals(edge1, "NotAnEdge");
    }

    @Test
    void testEdgeWithIntegers() {
        Edge<Integer> edge = new Edge<>(1, 2);
        assertEquals(1, edge.getSource());
        assertEquals(2, edge.getDestination());
    }
}