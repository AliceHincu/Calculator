package com.example.calculator.gui;

import com.example.calculator.domain.ComplexNumber;
import com.example.calculator.operations.MathSymbol;

public enum CalculatorSymbolsGui {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    POINT("."),
    IMAGINARY_UNIT(ComplexNumber.IMAGINARY_UNIT),
    SIGN_CHANGE("+/-"),
    LEFT_BRACKET(MathSymbol.LEFT_BRACKET.getSymbol()),
    RIGHT_BRACKET(MathSymbol.RIGHT_BRACKET.getSymbol()),
    COMMA(MathSymbol.COMMA.getSymbol()),
    CLEAR_ENTRY("CE"),
    CLEAR("C"),
    DELETE_CHAR("<=|");

    private final String symbol;

    CalculatorSymbolsGui(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
