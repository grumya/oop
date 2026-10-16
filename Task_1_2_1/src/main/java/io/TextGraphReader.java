package io;

import graph.Graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Реализация GraphReader для чтения пар вершин из текстового потока.
 * Каждая строка должна содержать источник и пункт назначения, разделенные пробелом.
 */
public class TextGraphReader implements GraphReader {

    @Override
    public <T> void read(Graph<T> graph, Reader reader) throws IOException {
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    @SuppressWarnings("unchecked")
                    T source = (T) parts[0];
                    @SuppressWarnings("unchecked")
                    T destination = (T) parts[1];
                    graph.addEdge(source, destination);
                }
            }
        }
    }
}