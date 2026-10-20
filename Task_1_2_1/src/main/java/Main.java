import graph.Graph;
import impl.GraphAdjacencyList;
import io.GraphReader;
import io.TextGraphReader;
import java.io.BufferedReader;
import java.io.FileReader;
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

            // Чтение графа из файла input.txt (в корне проекта)
            try (BufferedReader fileReader = new BufferedReader(new FileReader("input.txt"))) {
                reader.read(graph, fileReader);
            }

            System.out.println("Graph loaded successfully: " + graph);

            TopologySort sorter = new DfsTopologySorter();
            List<String> sortedOrder = sorter.sort(graph);

            System.out.println("Topological sort result: " + sortedOrder);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}