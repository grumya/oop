package expression;

import expression.core.Expression;
import expression.parser.ExpressionParser;
import java.util.Scanner;

/**
 * Точка входа в программу.
 */
public class Main {

    /**
     * Основной метод для ввода выражения через консоль и его обработки.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Математические выражения ===");
        System.out.print("Введите выражение: ");

        if (!scanner.hasNextLine()) {
            return;
        }

        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            System.out.println("Введена пустая строка.");
            return;
        }

        try {
            Expression expr = ExpressionParser.parse(input);

            System.out.print("Распарсенное выражение: ");
            expr.print();

            System.out.print("Введите переменную для дифференцирования: ");
            String var = scanner.nextLine().trim();

            if (!var.isEmpty()) {
                Expression diff = expr.derivative(var);
                System.out.print("Производная: ");
                diff.print();
            }

            System.out.print("Введите значения переменных (например, x = 10; y = 13): ");
            String assignments = scanner.nextLine();
            int val = expr.eval(assignments);
            System.out.println("Результат вычисления: " + val);

        } catch (Exception e) {
            System.err.println("Ошибка при обработке выражения: " + e.getMessage());
        }
    }
}