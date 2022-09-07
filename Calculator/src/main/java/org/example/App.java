package org.example;

import org.example.domain.ComplexNumber;
import org.example.operations.Calculations;

public class App {
    public static void main(String[] args) {
//        CalculatorUI calculatorUI = new CalculatorUI();
//        calculatorUI.run();
        ComplexNumber a = new ComplexNumber(2.0, 3.0);
        ComplexNumber b = new ComplexNumber(1.0, 0.0);
        System.out.println(Calculations.calculatePowerOf(a, b));
        //todo: powerof, modulo(can only be done for real numbers), log, pow, e
    }
}





