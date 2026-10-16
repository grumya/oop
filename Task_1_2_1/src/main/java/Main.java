import graph.Graph;
import impl.GraphAdjacencyList;
import io.GraphReader;
import io.TextGraphReader;
import java.io.StringReader;
import java.util.List;
import sort.DfsTopologySorter;
import sort.TopologySort;

/**
 * Главный класс приложения для демонстрации и проверки работоспособности.
 */
public class Main {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        try {
            Graph<String> graph = new GraphAdjacencyList<>();
            GraphReader reader = new TextGraphReader();

            // Имитация чтения из файла графа
            String data = "TaskA TaskB\nTaskB TaskC\nTaskA TaskC";
            reader.read(graph, new StringReader(data));

            System.out.println("Graph loaded successfully: " + graph);

            TopologySort sorter = new DfsTopologySorter();
            List<String> sortedOrder = sorter.sort(graph);

            System.out.println("Topological sort result: " + sortedOrder);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}