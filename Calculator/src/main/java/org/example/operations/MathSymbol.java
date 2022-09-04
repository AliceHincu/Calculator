package org.example.operations;

import java.util.Arrays;
import java.util.Optional;

public enum MathSymbol {
    LEFT_BRACKET("(", 1),
    RIGHT_BRACKET(")", 1),
    MAX("max", 2),
    MIN("min", 2),
    SQRT("sqrt", 2),
    MULTIPLICATION("*", 3),
    DIVISION("/", 3),
    MODULO("%", 3),
    ADDITION("+", 4),
    SUBTRACTION("-", 4),
    COMMA(",", 15);

    private final String symbol;
    private final int precedence;

    MathSymbol(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public static int getPrecedenceOfElement(String c) {
        return Arrays.stream(values())
                .filter(mathSymbol -> mathSymbol.getSymbol().equals(c))
                .findFirst()
                .map(MathSymbol::getPrecedence)
                .orElse(0);
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Operator precedence - PEMDAS rule. All have same left-associativity.
     * <a href="https://en.wikipedia.org/wiki/Order_of_operations#:~:text=It%20stands%20for%20Parentheses%2C%20Exponents,%2FMultiplication%2C%20Addition%2FSubtraction">https://en.wikipedia.org/wiki/Order_of_operations#:~:text=It%20stands%20for%20Parentheses%2C%20Exponents,%2FMultiplication%2C%20Addition%2FSubtraction</a>.
     *
     * @return precedence of symbol
     */
    public int getPrecedence() {
        return precedence;
    }
}
