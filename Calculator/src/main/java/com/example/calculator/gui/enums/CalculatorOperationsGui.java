package com.example.calculator.gui.enums;

import com.example.calculator.operations.MathSymbol;

import java.util.Arrays;

public enum CalculatorOperationsGui {
    MAX(MathSymbol.MAX.getSymbol()),
    MIN(MathSymbol.MIN.getSymbol()),
    SQRT(MathSymbol.SQRT.getSymbol()),
    LN(MathSymbol.LN.getSymbol()),
    EXP(MathSymbol.EXP.getSymbol()),
    MULTIPLICATION(MathSymbol.MULTIPLICATION.getSymbol()),
    DIVISION(MathSymbol.DIVISION.getSymbol()),
    MODULO(MathSymbol.MODULO.getSymbol()),
    POWER(MathSymbol.POWER.getSymbol()),
    ADDITION(MathSymbol.ADDITION.getSymbol()),
    SUBTRACTION(MathSymbol.SUBTRACTION.getSymbol());

    private final String operation;

    CalculatorOperationsGui(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public static boolean isOperation(String input){
        return Arrays.stream(values())
                .anyMatch(op -> op.getOperation().equalsIgnoreCase(input));
    }
}
