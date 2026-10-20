package sort;

import graph.Graph;
import impl.GraphAdjacencyList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DfsTopologySorterTest {

    @Test
    void testSuccessfulTopologySort() {
        Graph<String> graph = new GraphAdjacencyList<>();
        // Строим ацикличный граф: A -> B -> C, а также A -> D
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "D");

        TopologySort sorter = new DfsTopologySorter();
        List<String> sorted = sorter.sort(graph);

        assertNotNull(sorted);
        assertEquals(4, sorted.size());

        // Проверяем корректность топологического порядка (источники раньше стоков)
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
        // Создаем цикл: A -> B -> C -> A
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
        // Проверяем наличие всех констант в DfsColors для полного покрытия
        DfsColors[] colors = DfsColors.values();
        assertEquals(3, colors.length);
        assertEquals(DfsColors.WHITE, DfsColors.valueOf("WHITE"));
        assertEquals(DfsColors.GREY, DfsColors.valueOf("GREY"));
        assertEquals(DfsColors.BLACK, DfsColors.valueOf("BLACK"));
    }
}