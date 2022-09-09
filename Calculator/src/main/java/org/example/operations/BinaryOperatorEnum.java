package org.example.operations;

import org.example.domain.ComplexNumber;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public enum BinaryOperatorEnum {
    PLUS("+", Calculations::calculateAddition, "\\+"),
    MINUS("-", Calculations::calculateSubtraction, "-"),
    TIMES("*", Calculations::calculateMultiplication, "\\*"),
    DIVIDE("/", Calculations::calculateDivision, "/"),
    MAX("max", Calculations::calculateMaximum, "max"),
    MIN("min", Calculations::calculateMinimum, "min"),
    POW("^", Calculations::calculatePowerOf, "\\^"),
    MOD("%", Calculations::calculateRemainder, "%");

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


    /**
     * Return a function from Calculations class that needs to be applied on the complex numbers
     * The function is determined by the sign between the two complex numbers
     *
     * @param text the sign (+, -, max, etc...)
     * @return binary function associated with the given sign
     */
    private static BinaryOperator<ComplexNumber> getFunctionBySign(String text) {
        return Arrays.stream(values())
                .filter(op -> op.getSign().equalsIgnoreCase(text))
                .findFirst().map(BinaryOperatorEnum::getOperation).orElse(null);
    }

    public static ComplexNumber calculateExpression(String sign, ComplexNumber first, ComplexNumber second) {
        return getFunctionBySign(sign).apply(first, second);
    }

    /**
     * Check if the given sign is a binary operator.
     */
    public static boolean isOfType(String sign) {
        return Arrays.stream(values())
                .anyMatch(op -> op.getSign().equalsIgnoreCase(sign));
    }
}
