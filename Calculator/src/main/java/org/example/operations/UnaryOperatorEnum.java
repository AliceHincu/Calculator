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

    private static UnaryOperator<ComplexNumber> getFunctionBySign(String text) {
        return Arrays.stream(values())
                .filter(op -> op.getSign().equalsIgnoreCase(text))
                .findFirst().map(UnaryOperatorEnum::getOperation).orElse(null);
    }

    public static ComplexNumber calculateExpression(String token, Deque<ComplexNumber> numStack) {
        ComplexNumber first;
        ComplexNumber result;

        UnaryOperator<ComplexNumber> function = getFunctionBySign(token);
        first = numStack.pop();
        result = function.apply(first);

        return result;

    }

    public static boolean isOfType(String token) {
        return Arrays.stream(values())
                .anyMatch(op -> op.getSign().equalsIgnoreCase(token));
    }
}