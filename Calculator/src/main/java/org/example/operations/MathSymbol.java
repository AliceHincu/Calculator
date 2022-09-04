package org.example.operations;

public enum MathSymbol {
    LEFT_BRACKET("(", 1),
    RIGHT_BRACKET(")", 1),
    ZERO("0", 0),
    COMMA(",", 15),
    MAX("max", 2),
    MIN("min", 2),
    SQRT("sqrt", 2),
    PLUS("+", 4),
    MINUS("-", 4),
    MULTIPLY("*", 3),
    DIVIDE("/", 3);

    private final String symbol;
    private final int precedence;

    MathSymbol(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    /** Operator precedence - PEMDAS rule. All have same left-associativity.
     * <a href="https://en.wikipedia.org/wiki/Order_of_operations#:~:text=It%20stands%20for%20Parentheses%2C%20Exponents,%2FMultiplication%2C%20Addition%2FSubtraction">https://en.wikipedia.org/wiki/Order_of_operations#:~:text=It%20stands%20for%20Parentheses%2C%20Exponents,%2FMultiplication%2C%20Addition%2FSubtraction</a>.
     * @return precedence of symbol
     */
    public int getPrecedence() {
        return precedence;
    }
}
