package expression;

import java.util.Map;
import java.util.Objects;

/**
 * Класс, представляющий переменная в математическом выражении.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Конструктор переменной.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(Map<String, Integer> env) {
        if (!env.containsKey(name)) {
            throw new IllegalArgumentException("Variable " + name + " is not defined.");
        }
        return env.get(name);
    }

    @Override
    public Expression derivative(String variable) {
        if (this.name.equals(variable)) {
            return new Number(1);
        }
        return new Number(0);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variable)) {
            return false;
        }
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}