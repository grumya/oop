package expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * Юнит-тесты для проверки функциональности классов математических выражений.
 */
public class ExpressionTest {

    /**
     * Тестирование класса Number (вычисление, строковое представление,
     * взятие производной).
     */
    @Test
    public void testNumber() {
        Expression n = new Number(10);
        assertEquals(10, n.eval(""));
        assertEquals("10", n.toString());
        assertEquals("0", n.derivative("x").toString());
    }

    /**
     * Тестирование класса Variable (строковое представление,
     * взятие производной по совпавшей и несовпавшей переменной).
     */
    @Test
    public void testVariable() {
        Expression x = new Variable("x");
        assertEquals("x", x.toString());
        assertEquals("1", x.derivative("x").toString());
        assertEquals("0", x.derivative("y").toString());
    }

    /**
     * Тестирование класса Add (сложение чисел и переменных,
     * вычисление и правило производной суммы).
     */
    @Test
    public void testAdd() {
        Expression add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", add.toString());
        assertEquals(13, add.eval("x = 10"));
        assertEquals("(0+1)", add.derivative("x").toString());
    }

    /**
     * Тестирование класса Sub (вычитание, вычисление и правило
     * производной разности).
     */
    @Test
    public void testSub() {
        Expression sub = new Sub(new Variable("x"), new Number(5));
        assertEquals("(x-5)", sub.toString());
        assertEquals(5, sub.eval("x = 10"));
        assertEquals("(1-0)", sub.derivative("x").toString());
    }

    /**
     * Тестирование класса Mul (умножение, вычисление и правило
     * произведения для производной).
     */
    @Test
    public void testMul() {
        Expression mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", mul.toString());
        assertEquals(20, mul.eval("x = 10"));
        assertEquals("((0*x)+(2*1))", mul.derivative("x").toString());
    }

    /**
     * Тестирование класса Div (деление, правила дифференцирования
     * дроби и обработка деления на ноль).
     */
    @Test
    public void testDiv() {
        Expression div = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2)", div.toString());
        assertEquals(5, div.eval("x = 10"));
        assertEquals("(((1*2)-(x*0))/(2*2))", div.derivative("x").toString());

        Expression divZero = new Div(new Variable("x"), new Number(0));
        assertThrows(ArithmeticException.class, () -> divZero.eval("x = 10"));
    }

    /**
     * Взаимодействие нескольких классов (Add, Sub, Mul, Div, Variable, Number)
     * в составе сложного дерева выражений.
     */
    @Test
    public void testComplexExpressions() {
        // Тест комбинаций нескольких операций
        Expression expr = new Add(
                new Mul(new Number(2), new Variable("x")),
                new Sub(new Variable("y"), new Div(new Variable("x"), new Number(2)))
        );
        assertEquals(23, expr.eval("x = 10; y = 8"));
        assertNotNull(expr.derivative("x"));
        assertNotNull(expr.derivative("y"));
    }

    /**
     * Тестирование базового метода print() класса Expression.
     */
    @Test
    public void testPrint() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Expression expr = new Add(new Number(1), new Number(2));
        expr.print();

        System.setOut(originalOut);
        assertEquals("(1+2)" + System.lineSeparator(), outContent.toString());
    }

    /**
     * Тестирование класса ExpressionParser (разбор математических
     * строк с пробелами, скобками и переменными).
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

    /**
     * Тестирование класса Main (вызов главной точки входа приложения).
     */
    @Test
    public void testMain() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Main.main(new String[]{});

        System.setOut(originalOut);
        assertNotNull(outContent.toString());
    }
}