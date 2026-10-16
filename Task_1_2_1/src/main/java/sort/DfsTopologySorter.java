package sort;

import graph.Graph;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Реализация топологической сортировки на основе поиска в глубину (DFS).
 */
public class DfsTopologySorter implements TopologySort {
    @Override
    public <T> List<T> sort(Graph<T> graph) throws GraphCycleException {
        Map<T, DfsColors> colors = new HashMap<>();
        for (T vertex : graph.getVertices()) {
            colors.put(vertex, DfsColors.WHITE);
        }

        LinkedList<T> stack = new LinkedList<>();

        for (T vertex : graph.getVertices()) {
            if (colors.get(vertex) == DfsColors.WHITE) {
                dfs(vertex, graph, colors, stack);
            }
        }
        return stack;
    }

    private <T> void dfs(T vertex, Graph<T> graph, Map<T, DfsColors> colors, LinkedList<T> stack) {
        colors.put(vertex, DfsColors.GREY);

        for (T neighbor : graph.getNeighbors(vertex)) {
            DfsColors neighborColor = colors.getOrDefault(neighbor, DfsColors.WHITE);
            if (neighborColor == DfsColors.GREY) {
                throw new GraphCycleException("Cycle detected involving vertex: " + neighbor);
            }
            if (neighborColor == DfsColors.WHITE) {
                dfs(neighbor, graph, colors, stack);
            }
        }

        colors.put(vertex, DfsColors.BLACK);
        stack.addFirst(vertex);
    }
}