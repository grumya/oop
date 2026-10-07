package expression.operation;

import expression.core.Expression;

import java.util.Map;


/**
 * Класс, представляющий операцию сложения двух выражений (left + right).
 */
public class Add extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор сложения.
     *
     * @param left  левое подвыражение
     * @param right правое подвыражение
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(Map<String, Integer> env) {
        // Вычисляем левую и правую части и складываем
        return left.evaluate(env) + right.evaluate(env);
    }

    @Override
    public Expression derivative(String variable) {
        // Правило суммы: (u + v)' = u' + v'
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }
}