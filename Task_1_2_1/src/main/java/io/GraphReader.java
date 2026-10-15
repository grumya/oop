package io;

import graph.Graph;
import java.io.IOException;
import java.io.Reader;

/**
 * Интерфейс для чтения данных графа из текстового источника.
 */
public interface GraphReader {

    /**
     * Читает рёбра графа из потока символов.
     *
     * @param graph  экземпляр графа, который нужно заполнить
     * @param reader поток символов для чтения
     * @param <T>    тип данных вершин графа
     * @throws IOException если произошла ошибка ввода-вывода
     */
    <T> void read(Graph<T> graph, Reader reader) throws IOException;
}