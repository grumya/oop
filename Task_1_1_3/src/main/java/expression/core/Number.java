package expression.core;

import java.util.Map;
import java.util.Objects;

/**
 * Класс, представляющий числовую константу в математическом выражении.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Конструктор числовой константы.
     *
     * @param value значение числа
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(Map<String, Integer> env) {
        return value;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Number)) {
            return false;
        }
        Number number = (Number) o;
        return value == number.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}