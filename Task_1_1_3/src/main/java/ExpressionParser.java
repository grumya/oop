package expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для парсинга текстовой строки в объектное дерево Expression.
 */
public class ExpressionParser {

    /**
     * Преобразует строку в объект Expression.
     *
     * @param input входная строка выражения
     * @return объект Expression
     */
    public static Expression parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        String str = input.replaceAll("\\s+", "");
        return parseHelper(str);
    }

    private static Expression parseHelper(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        // Снимаем внешние скобки, если они обволакивают всё выражение
        if (str.startsWith("(") && str.endsWith(")")) {
            int balance = 0;
            boolean isOuter = true;
            for (int i = 0; i < str.length() - 1; i++) {
                if (str.charAt(i) == '(') {
                    balance++;
                }
                if (str.charAt(i) == ')') {
                    balance--;
                }
                if (balance == 0) {
                    isOuter = false;
                    break;
                }
            }
            if (isOuter) {
                str = str.substring(1, str.length() - 1);
            }
        }

        int balance = 0;
        int mainOpIndex = -1;

        // Ищем главную операцию верхнего уровня
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '(') {
                balance++;
            } else if (ch == ')') {
                balance--;
            } else if (balance == 0 && (ch == '+' || ch == '-' || ch == '*' || ch == '/')) {
                mainOpIndex = i;
                break;
            }
        }

        if (mainOpIndex != -1) {
            char op = str.charAt(mainOpIndex);
            Expression left = parseHelper(str.substring(0, mainOpIndex));
            Expression right = parseHelper(str.substring(mainOpIndex + 1));

            switch (op) {
                case '+':
                    return new Add(left, right);
                case '-':
                    return new Sub(left, right);
                case '*':
                    return new Mul(left, right);
                case '/':
                    return new Div(left, right);
                default:
                    throw new IllegalArgumentException("Unknown operator: " + op);
            }
        }

        // Если это целое число
        try {
            return new Number(Integer.parseInt(str));
        } catch (NumberFormatException ignored) {
            // Игнорируем исключение, так как строка может быть именем переменной
        }

        // Иначе это имя переменной
        return new Variable(str);
    }
}