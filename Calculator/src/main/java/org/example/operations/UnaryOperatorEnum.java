package org.example.operations;

import org.example.domain.ComplexNumber;

import java.util.Arrays;
import java.util.Deque;
import java.util.function.UnaryOperator;

public enum UnaryOperatorEnum {
    SQRT("sqrt", Calculations::calculateSquareRoot, "sqrt");

    private final String sign;
    private final UnaryOperator<ComplexNumber> operation;
    private final String regex;

    UnaryOperatorEnum(String operator, UnaryOperator<ComplexNumber> operation, String regex) {
        this.sign = operator;
        this.operation = operation;
        this.regex = regex;
    }

    public String getSign() {
        return sign;
    }

    public UnaryOperator<ComplexNumber> getOperation() {
        return operation;
    }
    public String getRegex() {
        return regex;
    }

    /**
     * Return a function from Calculations class that needs to be applied on the complex numbers
     * The function is determined by the sign used on the complex number
     *
     * @param text the sign (sqrt, ln...)
     * @return binary function associated with the given sign
     */
    private static UnaryOperator<ComplexNumber> getFunctionBySign(String text) {
        return Arrays.stream(values())
                .filter(op -> op.getSign().equalsIgnoreCase(text))
                .findFirst().map(UnaryOperatorEnum::getOperation).orElse(null);
    }

    public static ComplexNumber calculateExpression(String token, ComplexNumber number) {
        return getFunctionBySign(token).apply(number);
    }

    public static boolean isOfType(String token) {
        return Arrays.stream(values())
                .anyMatch(op -> op.getSign().equalsIgnoreCase(token));
    }
}