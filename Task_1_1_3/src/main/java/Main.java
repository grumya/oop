package expression;

/**
 * Точка входа в приложение для демонстрации работы с выражениями.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // 1. Создание выражения через конструктор: (3 + (2 * x))
        Expression e = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );

        System.out.print("Выражение e: ");
        e.print(); // Вывод: (3+(2*x))

        // 2. Взятие производной по переменной x
        Expression de = e.derivative("x");

        System.out.print("Производная de (de/dx): ");
        de.print(); // Вывод: (0+((0*x)+(2*1)))

        // 3. Вычисление значения при заданной подстановке переменной
        int result = e.eval("x = 10; y = 13");
        System.out.println("Результат e при x = 10; y = 13: " + result); // Вывод: 23

        // 4. Парсинг выражения из строки
        Expression parsed = ExpressionParser.parse("(3+(2*x))");
        System.out.print("Распарсенное выражение: ");
        parsed.print();
    }
}