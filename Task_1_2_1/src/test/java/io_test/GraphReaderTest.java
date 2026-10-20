package io;

import graph.Graph;
import impl.GraphAdjacencyList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class GraphReaderTest {

    @Test
    void testGraphReaderInterfaceContract() throws IOException {
        // Проверяем контракт интерфейса через анонимную реализацию
        GraphReader customReader = new GraphReader() {
            @Override
            public <T> void read(Graph<T> graph, Reader reader) throws IOException {
                // Добавляем тестовое ребро вручную для проверки контракта
                @SuppressWarnings("unchecked")
                T src = (T) "Start";
                @SuppressWarnings("unchecked")
                T dest = (T) "End";
                graph.addEdge(src, dest);
            }
        };

        Graph<String> graph = new GraphAdjacencyList<>();
        customReader.read(graph, new StringReader(""));

        assertTrue(graph.hasVertex("Start") || graph.getVertices().contains("Start"));
        assertTrue(graph.getNeighbors("Start").contains("End"));
    }
}