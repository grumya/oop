package io_test;

import graph.Graph;
import impl.GraphAdjacencyList;
import io.GraphReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphReaderTest {

    @Test
    void testGraphReaderInterfaceContract() throws IOException {
        GraphReader customReader = new GraphReader() {
            @Override
            public <T> void read(Graph<T> graph, Reader reader) throws IOException {
                @SuppressWarnings("unchecked")
                T src = (T) "Start";
                @SuppressWarnings("unchecked")
                T dest = (T) "End";
                graph.addEdge(src, dest);
            }
        };

        Graph<String> graph = new GraphAdjacencyList<>();
        customReader.read(graph, new StringReader(""));

        assertTrue(graph.getVertices().contains("Start"));
        assertTrue(graph.getNeighbors("Start").contains("End"));
    }
}