package expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import expression.core.Expression;
import expression.core.Number;
import expression.core.Variable;
import expression.operation.Add;
import expression.operation.Div;
import expression.operation.Mul;
import expression.operation.Sub;
import expression.parser.ExpressionParser;
import org.junit.jupiter.api.Test;

/**
 * Класс юнит-тестов для проверки корректности работы иерархии Expression,
 * вычисления значений, дифференцирования и парсинга строк.
 */
public class ExpressionTest {

    @Test
    public void testNumber() {
        Expression n = new Number(10);
        assertEquals(10, n.eval(""));
        assertEquals("10", n.toString());
        assertEquals(new Number(0), n.derivative("x"));
    }

    @Test
    public void testVariable() {
        Expression x = new Variable("x");
        assertEquals("x", x.toString());
        assertEquals(new Number(1), x.derivative("x"));
        assertEquals(new Number(0), x.derivative("y"));
    }

    @Test
    public void testAdd() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", add.toString());
        assertEquals(13, add.eval("x = 10"));
        assertEquals(new Add(new Number(0), new Number(1)), add.derivative("x"));
    }

    @Test
    public void testSub() {
        Expression sub = new Sub(new Variable("x"), new Number(5));
        assertEquals("(x-5)", sub.toString());
        assertEquals(5, sub.eval("x = 10"));
        assertEquals(new Sub(new Number(1), new Number(0)), sub.derivative("x"));
    }

    @Test
    public void testMul() {
        Expression mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", mul.toString());
        assertEquals(20, mul.eval("x = 10"));

        Expression expectedDerivative = new Add(
                new Mul(new Number(0), new Variable("x")),
                new Mul(new Number(2), new Number(1))
        );
        assertEquals(expectedDerivative, mul.derivative("x"));
    }

    @Test
    public void testDiv() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2)", div.toString());
        assertEquals(5, div.eval("x = 10"));

        Expression divZero = new Div(new Variable("x"), new Number(0));
        assertThrows(ArithmeticException.class, () -> divZero.eval("x = 10"));
    }

    @Test
    public void testComplexExpressions() {
        Expression expr = new Add(
                new Mul(new Number(2), new Variable("x")),
                new Sub(new Variable("y"), new Div(new Variable("x"), new Number(2)))
        );
        assertEquals(23, expr.eval("x = 10; y = 8"));
        assertNotNull(expr.derivative("x"));
        assertNotNull(expr.derivative("y"));
    }

    @Test
    public void testParser() {
        Expression parsed1 = ExpressionParser.parse("(x + 5) * 2");
        assertNotNull(parsed1);
        assertEquals(30, parsed1.eval("x = 10"));

        Expression parsed2 = ExpressionParser.parse("(x / 2) - 3");
        assertNotNull(parsed2);
        assertEquals(2, parsed2.eval("x = 10"));

        Expression parsed3 = ExpressionParser.parse("100");
        assertEquals(100, parsed3.eval(""));

        Expression parsed4 = ExpressionParser.parse("  y  ");
        assertEquals(7, parsed4.eval("y = 7"));
    }
}