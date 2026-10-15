package sort;

/**
 * Исключение, выбрасываемое при обнаружении цикла в ориентированном графе,
 * что делает невозможным выполнение топологической сортировки.
 */
public class GraphCycleException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message описание ошибки
     */
    public GraphCycleException(String message) {
        super(message);
    }
}