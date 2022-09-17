package com.example.calculator.gui.enums;

import com.example.calculator.domain.ComplexNumber;
import com.example.calculator.operations.MathSymbol;

import java.util.Arrays;

public enum CalculatorSymbolsGui {
    LEFT_BRACKET(MathSymbol.LEFT_BRACKET.getSymbol()),
    RIGHT_BRACKET(MathSymbol.RIGHT_BRACKET.getSymbol()),
    COMMA(MathSymbol.COMMA.getSymbol());

    private final String symbol;

    CalculatorSymbolsGui(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static boolean isSymbol(String input){
        return Arrays.stream(values())
                .anyMatch(op -> op.getSymbol().equalsIgnoreCase(input));
    }
}
