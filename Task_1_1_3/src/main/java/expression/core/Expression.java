package expression.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный базовый класс для всех математических выражений.
 * Служит корнем иерархии Expression Tree.
 */
public abstract class Expression {

    /**
     * Печатает текстовое представление выражения в стандартный поток вывода.
     */
    public void print() {
        System.out.println(this.toString());
    }

    /**
     * Вычисляет числовое значение выражения при заданных значениях переменных.
     *
     * @param assignments строка вида "x = 10; y = 13"
     * @return результат вычисления типа int
     */
    public int eval(String assignments) {
        Map<String, Integer> env = parseAssignments(assignments);
        return evaluate(env);
    }

    /**
     * Внутренний метод для рекурсивного вычисления выражения по дереву.
     *
     * @param env карта значений переменных (переменная -> значение)
     * @return вычисленное целочисленное значение
     */
    public abstract int evaluate(Map<String, Integer> env);

    /**
     * Возвращает символьную производную выражения по заданной переменной.
     *
     * @param variable имя переменной, по которой берется дифференцирование
     * @return новое выражение Expression, представляющее производную
     */
    public abstract Expression derivative(String variable);

    /**
     * Вспомогательный метод парсинга строки переменных вида "x = 10; y = 13".
     * Использует ArrayList согласно требованиям преподавателя.
     */
    private Map<String, Integer> parseAssignments(String assignments) {
        Map<String, Integer> env = new HashMap<>();
        if (assignments == null || assignments.trim().isEmpty()) {
            return env;
        }

        // Разбиваем строку по разделителю ';' и сохраняем в ArrayList
        List<String> pairs = new ArrayList<>(Arrays.asList(assignments.split(";")));

        for (String pair : pairs) {
            // Разбиваем пару "переменная = значение" по символу '='
            List<String> kv = new ArrayList<>(Arrays.asList(pair.split("=")));
            if (kv.size() == 2) {
                String varName = kv.get(0).trim();
                int value = Integer.parseInt(kv.get(1).trim());
                env.put(varName, value);
            }
        }
        return env;
    }
}