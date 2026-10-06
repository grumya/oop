package expression;

import java.util.Map;

/**
 * Класс, представляющий операцию вычитания двух выражений (left - right).
 */
public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор вычитания.
     *
     * @param left  уменьшаемое выражение
     * @param right вычитаемое выражение
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(Map<String, Integer> env) {
        return left.evaluate(env) - right.evaluate(env);
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }
}