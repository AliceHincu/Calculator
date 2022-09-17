package com.example.calculator.gui.enums;

import com.example.calculator.domain.ComplexNumber;

public enum CalculatorDigitsGui {
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
    IMAGINARY_UNIT(ComplexNumber.IMAGINARY_UNIT);

    private final String digit;

    CalculatorDigitsGui(String digit) {
        this.digit = digit;
    }

    public String getSymbol() {
        return digit;
    }
}
