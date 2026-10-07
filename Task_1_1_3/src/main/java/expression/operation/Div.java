package expression.operation;

import expression.core.Expression;

import java.util.Map;

/**
 * Класс, представляющий операцию деления двух выражений (left / right).
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор деления.
     *
     * @param left  делимое выражение
     * @param right делитель
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(Map<String, Integer> env) {
        return left.evaluate(env) / right.evaluate(env);
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }
}