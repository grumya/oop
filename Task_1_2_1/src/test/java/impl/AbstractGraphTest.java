package impl;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractGraphTest {

    /**
     * Конкретная реализация-заглушка для тестирования защищенных методов AbstractGraph.
     */
    private static final class TestGraph<T> extends AbstractGraph<T> {
        @Override
        public void addVertex(T vertex) {
            validateVertex(vertex);
        }

        @Override
        public void removeVertex(T vertex) {
            validateVertex(vertex);
        }

        @Override
        public void addEdge(T source, T destination) {
            validateVertex(source);
            validateVertex(destination);
        }

        @Override
        public void removeEdge(T source, T destination) {
            validateVertex(source);
            validateVertex(destination);
        }

        @Override
        public List<T> getNeighbors(T vertex) {
            validateVertex(vertex);
            return List.of();
        }

        @Override
        public Set<T> getVertices() {
            return Set.of();
        }

        // Публичный метод для проверки защищенного validateVertex
        public void publicValidate(T vertex) {
            validateVertex(vertex);
        }
    }

    @Test
    void testValidateVertexThrowsExceptionOnNull() {
        TestGraph<String> graph = new TestGraph<>();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> graph.publicValidate(null)
        );

        assertEquals("Vertex cannot be null", exception.getMessage());
    }

    @Test
    void testValidateVertexPassesOnNonNull() {
        TestGraph<String> graph = new TestGraph<>();

        assertDoesNotThrow(() -> graph.publicValidate("A"));
    }
}