package io;

import graph.Graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

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
                List<String> parts = List.of(line.split("\\s+"));
                if (parts.size() >= 2) {
                    T source = parseVertex(parts.get(0));
                    T destination = parseVertex(parts.get(1));
                    graph.addEdge(source, destination);
                }
            }
        }
    }

    /**
     * Безопасное приведение строки к типу T (поддерживает Integer и String).
     */
    @SuppressWarnings("unchecked")
    private <T> T parseVertex(String token) {
        try {
            // Если вершина — число, возвращаем как Integer
            return (T) Integer.valueOf(token);
        } catch (NumberFormatException e) {
            // Иначе возвращаем как текст (String)
            return (T) token;
        }
    }
}