package org.example;

import org.example.ui.CalculatorUI;

public class App {
    public static void main(String[] args) {
        CalculatorUI calculatorUI = new CalculatorUI();
        calculatorUI.run();
        //todo convertor(ExpToList): initially, get rid of all whitespaces from the string
        //todo convertor(ExpToList): make regex work for "i" instead of "1i"
        //todo convertor(InfixToPostfix): refactor phase2

        //TODO: optional: check brackets + invalid symbols like a,b,c...
        //TODO: to work with i instead of 1i
        //TODO: replace new Complex(...) with just strings like "2+i" in test
        // test that don't work: min(4 + 5 + 9 / 3 - 3 - 5 - 4 + max(4, min(4, 6)), 5)
    }
}





