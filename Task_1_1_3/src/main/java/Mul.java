package expression;

import java.util.Map;

/**
 * Класс, представляющий операцию умножения двух выражений (left * right).
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор умножения.
     *
     * @param left  левый множитель
     * @param right правый множитель
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(Map<String, Integer> env) {
        // Перемножаем результаты левой и правой частей
        return left.evaluate(env) * right.evaluate(env);
    }

    @Override
    public Expression derivative(String variable) {
        // Правило произведения: (u * v)' = u' * v + u * v'
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }
}