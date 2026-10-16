package expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
 * вычисления значений, дифференцирования, парсинга строк и методов эквивалентности.
 */
public class ExpressionTest {

    /**
     * Тестирует функциональность числовой константы (Number):
     * вычисление значения, строковое представление и взятие производной.
     */
    @Test
    public void testNumber() {
        Expression n = new Number(10);
        assertEquals(10, n.eval(""));
        assertEquals("10", n.toString());
        assertEquals(new Number(0), n.derivative("x"));
    }

    /**
     * Тестирует корректность реализации equals и hashCode для класса Number.
     * Покрывает ветки сравнения с собой, аналогичным объектом, другим числом, null и объектом другого типа.
     */
    @Test
    public void testNumberEqualsAndHashCode() {
        Number n1 = new Number(10);
        Number n2 = new Number(10);
        Number n3 = new Number(5);

        // Проверка корректности сравнения объектов (equals)
        assertEquals(n1, n1);
        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
        assertNotEquals(n1, null);
        assertNotEquals(n1, "10");

        // Проверка равенства хэш-кодов для одинаковых значений
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    /**
     * Тестирует функциональность переменной (Variable):
     * строковое представление и взятие производной по совпадающим и не совпадающим переменным.
     */
    @Test
    public void testVariable() {
        Expression x = new Variable("x");
        assertEquals("x", x.toString());
        assertEquals(new Number(1), x.derivative("x"));
        assertEquals(new Number(0), x.derivative("y"));
    }

    /**
     * Тестирует корректность реализации equals, hashCode и выброс исключений для Variable.
     * Покрывает случай вычисления значения, когда переменная отсутствует в контексте подстановки.
     */
    @Test
    public void testVariableEqualsHashCodeAndExceptions() {
        Variable x1 = new Variable("x");
        Variable x2 = new Variable("x");
        Variable y = new Variable("y");

        // Проверка корректности сравнения объектов (equals)
        assertEquals(x1, x1);
        assertEquals(x1, x2);
        assertNotEquals(x1, y);
        assertNotEquals(x1, null);
        assertNotEquals(x1, 10);

        // Проверка равенства хэш-кодов
        assertEquals(x1.hashCode(), x2.hashCode());

        // Проверка выброса ошибки при отсутствии переданного значения переменной
        assertThrows(Exception.class, () -> x1.eval("y = 5"));
    }

    /**
     * Тестирует операцию сложения (Add):
     * строковый формат, вычисление со строкой параметров и вычисление производной.
     */
    @Test
    public void testAdd() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", add.toString());
        assertEquals(13, add.eval("x = 10"));
        assertEquals(1, add.derivative("x").eval("x = 10"));
    }

    /**
     * Тестирует операцию вычитания (Sub):
     * строковый формат, вычисление значения и производной.
     */
    @Test
    public void testSub() {
        Expression sub = new Sub(new Variable("x"), new Number(5));
        assertEquals("(x-5)", sub.toString());
        assertEquals(5, sub.eval("x = 10"));
        assertEquals(1, sub.derivative("x").eval("x = 10"));
    }

    /**
     * Тестирует операцию умножения (Mul):
     * строковый формат, вычисление значения и производной произведения.
     */
    @Test
    public void testMul() {
        Expression mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", mul.toString());
        assertEquals(20, mul.eval("x = 10"));
        assertEquals(2, mul.derivative("x").eval("x = 10"));
    }

    /**
     * Тестирует операцию деления (Div) и обработку исключения деления на ноль.
     */
    @Test
    public void testDiv() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2)", div.toString());
        assertEquals(5, div.eval("x = 10"));

        Expression divZero = new Div(new Variable("x"), new Number(0));
        assertThrows(ArithmeticException.class, () -> divZero.eval("x = 10"));
    }

    /**
     * Тестирует составное выражение с несколькими переменными и операциями.
     */
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

    /**
     * Тестирует парсинг строк в объекты Expression с учетом приоритетов и пробелов.
     */
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