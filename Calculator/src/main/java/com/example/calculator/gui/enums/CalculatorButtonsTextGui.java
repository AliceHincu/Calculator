package com.example.calculator.gui.enums;

public enum CalculatorButtonsTextGui {
    SIGN_CHANGE("+/-"),
    CLEAR_ENTRY("CE"),
    CLEAR("C"),
    DELETE_CHAR("<=|");

    private final String symbol;

    CalculatorButtonsTextGui(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
