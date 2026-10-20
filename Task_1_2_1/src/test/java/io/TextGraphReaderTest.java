package io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import graph.Graph;
import impl.GraphAdjacencyList;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class TextGraphReaderTest {

    @Test
    void testReadStringVertices() throws IOException {
        Graph<String> graph = new GraphAdjacencyList<>();
        GraphReader reader = new TextGraphReader();

        String data = "TaskA TaskB\nTaskB TaskC";
        reader.read(graph, new StringReader(data));

        assertTrue(graph.getVertices().contains("TaskA"));
        assertTrue(graph.getNeighbors("TaskA").contains("TaskB"));
        assertTrue(graph.getNeighbors("TaskB").contains("TaskC"));
    }

    @Test
    void testReadIntegerVertices() throws IOException {
        Graph<Integer> graph = new GraphAdjacencyList<>();
        GraphReader reader = new TextGraphReader();

        String data = "1 2\n2 3";
        reader.read(graph, new StringReader(data));

        assertTrue(graph.getVertices().contains(1));
        assertTrue(graph.getNeighbors(1).contains(2));
        assertTrue(graph.getNeighbors(2).contains(3));
    }

    @Test
    void testReadWithEmptyLinesAndSpaces() throws IOException {
        Graph<String> graph = new GraphAdjacencyList<>();
        GraphReader reader = new TextGraphReader();

        String data = "   \n\nA B   \n   \nB C\n";
        reader.read(graph, new StringReader(data));

        assertEquals(3, graph.getVertices().size());
        assertTrue(graph.getNeighbors("A").contains("B"));
        assertTrue(graph.getNeighbors("B").contains("C"));
    }
}