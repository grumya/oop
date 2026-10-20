package sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphCycleExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Test cycle error";
        GraphCycleException exception = new GraphCycleException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }
}