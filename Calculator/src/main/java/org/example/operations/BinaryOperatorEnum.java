package org.example.operations;

import org.example.domain.ComplexNumber;

import java.util.Arrays;
import java.util.Deque;
import java.util.function.BinaryOperator;

public enum BinaryOperatorEnum {
    PLUS("+", Calculations::calculateAddition, "\\+"),
    MINUS("-", Calculations::calculateSubtraction, "-"),
    TIMES("*", Calculations::calculateMultiplication, "\\*"),
    DIVIDE("/", Calculations::calculateDivision, "/"),
    MAX("max", Calculations::calculateMaximum, "max"),
    MIN("min", Calculations::calculateMinimum, "min");

    private final String sign;
    private final BinaryOperator<ComplexNumber> operation;
    private final String regex;

    BinaryOperatorEnum(String sign, BinaryOperator<ComplexNumber> operation, String regex) {
        this.sign = sign;
        this.operation = operation;
        this.regex = regex;
    }

    public String getSign() {
        return sign;
    }

    public BinaryOperator<ComplexNumber> getOperation() {
        return operation;
    }
    public String getRegex() {
        return regex;
    }


    private static BinaryOperator<ComplexNumber> getFunctionBySign(String text) {
        return Arrays.stream(values())
                .filter(op -> op.getSign().equalsIgnoreCase(text))
                .findFirst().map(BinaryOperatorEnum::getOperation).orElse(null);
    }

    public static ComplexNumber calculateExpression(String token, ComplexNumber first, ComplexNumber second) {
        return getFunctionBySign(token).apply(first, second);
    }

    public static boolean isOfType(String token) {
        return Arrays.stream(values())
                .anyMatch(op -> op.getSign().equalsIgnoreCase(token));
    }
}
