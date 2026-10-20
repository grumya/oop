import graph.Graph;
import impl.GraphAdjacencyList;
import io.GraphReader;
import io.TextGraphReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sort.DfsTopologySorter;
import sort.TopologySort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final String TEST_FILE_NAME = "input.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Создаем тестовый файл input.txt перед каждым тестом
        try (FileWriter writer = new FileWriter(TEST_FILE_NAME)) {
            writer.write("TaskA TaskB\n");
            writer.write("TaskB TaskC\n");
        }
    }

    @AfterEach
    void tearDown() {
        // Удаляем временный файл после тестов, чтобы не засорять проект
        File file = new File(TEST_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testMainReadsFromFileAndSorts() {
        // Проверяем ровно ту логику, которая заложена в Main.java
        try {
            Graph<String> graph = new GraphAdjacencyList<>();
            GraphReader reader = new TextGraphReader();

            try (BufferedReader fileReader = new BufferedReader(new FileReader(TEST_FILE_NAME))) {
                reader.read(graph, fileReader);
            }

            assertFalse(graph.getVertices().isEmpty(), "Граф не должен быть пустым после чтения из файла");
            assertTrue(graph.getVertices().contains("TaskA"));
            assertTrue(graph.getVertices().contains("TaskB"));
            assertTrue(graph.getVertices().contains("TaskC"));

            TopologySort sorter = new DfsTopologySorter();
            List<String> sortedOrder = sorter.sort(graph);

            assertNotNull(sortedOrder);
            assertEquals(3, sortedOrder.size());
            assertTrue(sortedOrder.indexOf("TaskA") < sortedOrder.indexOf("TaskB"));
            assertTrue(sortedOrder.indexOf("TaskB") < sortedOrder.indexOf("TaskC"));

        } catch (Exception e) {
            fail("Main workflow threw an unexpected exception: " + e.getMessage());
        }
    }
}