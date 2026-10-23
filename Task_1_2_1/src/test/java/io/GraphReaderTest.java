package io;

import static org.junit.jupiter.api.Assertions.assertTrue;

import graph.Graph;
import impl.GraphAdjacencyList;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

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