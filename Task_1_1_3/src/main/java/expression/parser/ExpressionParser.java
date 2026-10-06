package expression.parser;

import expression.core.Expression;
import expression.core.Number;
import expression.core.Variable;
import expression.operation.Add;
import expression.operation.Div;
import expression.operation.Mul;
import expression.operation.Sub;

/**
 * Парсер математических выражений из текстовой строки.
 */
public class ExpressionParser {

    private static final char OPEN_BRACKET = '(';
    private static final char CLOSE_BRACKET = ')';
    private static final char ADD_OP = '+';
    private static final char SUB_OP = '-';
    private static final char MUL_OP = '*';
    private static final char DIV_OP = '/';

    /**
     * Парсит строку и создает объектное дерево Expression.
     *
     * @param input входная строка выражения
     * @return объект Expression
     */
    public static Expression parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        String sanitized = input.replaceAll("\\s+", "");
        return parseExpression(sanitized);
    }

    private static Expression parseExpression(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        String unbracketed = stripOuterBrackets(str);

        // 1. Поиск аддитивных операций (+, -)
        int opIndex = findMainOperatorIndex(unbracketed, ADD_OP, SUB_OP);
        if (opIndex != -1) {
            return buildBinaryExpression(unbracketed, opIndex);
        }

        // 2. Поиск мультипликативных операций (*, /)
        opIndex = findMainOperatorIndex(unbracketed, MUL_OP, DIV_OP);
        if (opIndex != -1) {
            return buildBinaryExpression(unbracketed, opIndex);
        }

        return parseTerminal(unbracketed);
    }

    private static String stripOuterBrackets(String str) {
        String current = str;
        while (current.startsWith(String.valueOf(OPEN_BRACKET))
                && current.endsWith(String.valueOf(CLOSE_BRACKET))) {

            if (!hasEnclosingOuterBrackets(current)) {
                break;
            }
            current = current.substring(1, current.length() - 1);
        }
        return current;
    }

    private static boolean hasEnclosingOuterBrackets(String str) {
        int balance = 0;
        for (int i = 0; i < str.length() - 1; i++) {
            char ch = str.charAt(i);
            if (ch == OPEN_BRACKET) balance++;
            if (ch == CLOSE_BRACKET) balance--;
            if (balance == 0) {
                return false;
            }
        }
        return true;
    }

    private static int findMainOperatorIndex(String str, char targetOp1, char targetOp2) {
        int balance = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            if (ch == CLOSE_BRACKET) balance++;
            else if (ch == OPEN_BRACKET) balance--;
            else if (balance == 0 && (ch == targetOp1 || ch == targetOp2)) {
                return i;
            }
        }
        return -1;
    }

    private static Expression buildBinaryExpression(String str, int opIndex) {
        char op = str.charAt(opIndex);
        Expression left = parseExpression(str.substring(0, opIndex));
        Expression right = parseExpression(str.substring(opIndex + 1));

        switch (op) {
            case ADD_OP:
                return new Add(left, right);
            case SUB_OP:
                return new Sub(left, right);
            case MUL_OP:
                return new Mul(left, right);
            case DIV_OP:
                return new Div(left, right);
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    private static Expression parseTerminal(String str) {
        try {
            return new Number(Integer.parseInt(str));
        } catch (NumberFormatException ignored) {
            return new Variable(str);
        }
    }
}