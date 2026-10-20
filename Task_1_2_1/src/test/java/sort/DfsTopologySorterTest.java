package sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import graph.Graph;
import impl.GraphAdjacencyList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DfsTopologySorterTest {

    @Test
    void testSuccessfulTopologySort() {
        Graph<String> graph = new GraphAdjacencyList<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "D");

        TopologySort sorter = new DfsTopologySorter();
        List<String> sorted = sorter.sort(graph);

        assertNotNull(sorted);
        assertEquals(4, sorted.size());

        int indexA = sorted.indexOf("A");
        int indexB = sorted.indexOf("B");
        int indexC = sorted.indexOf("C");
        int indexD = sorted.indexOf("D");

        assertTrue(indexA < indexB);
        assertTrue(indexB < indexC);
        assertTrue(indexA < indexD);
    }

    @Test
    void testCycleDetectionThrowsException() {
        Graph<String> graph = new GraphAdjacencyList<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        TopologySort sorter = new DfsTopologySorter();

        GraphCycleException exception = assertThrows(
                GraphCycleException.class,
                () -> sorter.sort(graph)
        );

        assertTrue(exception.getMessage().contains("Cycle detected"));
    }

    @Test
    void testDfsColorsEnumValues() {
        DfsColors[] colors = DfsColors.values();
        assertEquals(3, colors.length);
        assertEquals(DfsColors.WHITE, DfsColors.valueOf("WHITE"));
        assertEquals(DfsColors.GREY, DfsColors.valueOf("GREY"));
        assertEquals(DfsColors.BLACK, DfsColors.valueOf("BLACK"));
    }
}