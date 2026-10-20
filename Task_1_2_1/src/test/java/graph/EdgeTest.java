package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        final Edge<String> edge3 = new Edge<>("B", "C");

        assertEquals(edge1, edge2);
        assertEquals(edge1.hashCode(), edge2.hashCode());
        assertNotEquals(edge1, edge3);
        assertNotEquals(edge1, null);
        assertNotEquals(edge1, "Not An Edge");
    }
}