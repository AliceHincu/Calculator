package org.example.operations;

import org.example.domain.ComplexNumber;

public class Calculations {
    private Calculations() {
    } // to hide public constructor

    public static ComplexNumber calculateAddition(ComplexNumber number1, ComplexNumber number2) {
        ComplexNumber result = new ComplexNumber();
        result.setReal(number1.getReal() + number2.getReal());
        result.setImaginary(number1.getImaginary() + number2.getImaginary());

        return result;
    }

    public static ComplexNumber calculateSubtraction(ComplexNumber number1, ComplexNumber number2) {
        ComplexNumber result = new ComplexNumber();
        result.setReal(number1.getReal() - number2.getReal());
        result.setImaginary(number1.getImaginary() - number2.getImaginary());

        return result;
    }

    public static ComplexNumber calculateMultiplication(ComplexNumber number1, ComplexNumber number2) {
        // https://www.mathsisfun.com/algebra/complex-number-multiply.html
        ComplexNumber result = new ComplexNumber();
        result.setReal(number1.getReal() * number2.getReal() - number1.getImaginary() * number2.getImaginary());
        result.setImaginary(number1.getReal() * number2.getImaginary() + number2.getReal() * number1.getImaginary());

        return result;
    }

    public static ComplexNumber calculateDivision(ComplexNumber number1, ComplexNumber number2) {
        // https://www.cuemath.com/numbers/division-of-complex-numbers/
        if (number2.getImaginary() == 0.0 && number2.getReal() == 0.0) {
            throw new ArithmeticException("Error...Division by 0!");
        }
        ComplexNumber result = new ComplexNumber();
        double denominator = (number2.getReal() * number2.getReal() + number2.getImaginary() * number2.getImaginary());
        result.setReal((number1.getReal() * number2.getReal() + number1.getImaginary() * number2.getImaginary()) / denominator);
        result.setImaginary((number2.getReal() * number1.getImaginary() - number1.getReal() * number2.getImaginary()) / denominator);

        return result;
    }

    public static ComplexNumber calculateMinimum(ComplexNumber number1, ComplexNumber number2) {
        return getAbsoluteValue(number1) < getAbsoluteValue(number2) ? number1 : number2;
    }

    public static ComplexNumber calculateMaximum(ComplexNumber number1, ComplexNumber number2) {
        return getAbsoluteValue(number1) < getAbsoluteValue(number2) ? number2 : number1;
    }

    public static ComplexNumber calculateSquareRoot(ComplexNumber number) {
        ComplexNumber result = new ComplexNumber();

        if ((number.getReal() == 0) && (number.getImaginary() == 0.0)) {
            result.setReal(0.0);
            result.setImaginary(0.0);
            return result;
        }

        double dX;
        double dY;

        dX = Math.abs(number.getReal());
        dY = Math.abs(number.getImaginary());

        double dW;
        double dR;

        if (dX >= dY) {
            dR = dY / dX;
            dW = Math.sqrt(dX) * Math.sqrt(0.5 * (1.0 + Math.sqrt(1 + dR * dR)));
        } else {
            dR = dX / dY;
            dW = Math.sqrt(dY) * Math.sqrt(0.5 * (dR + Math.sqrt(1 + dR * dR)));
        }

        if (number.getReal() >= 0.0) {
            result.setReal(dW);
            result.setImaginary(number.getImaginary() / (2.0 * dW));
        } else {
            result.setImaginary((number.getImaginary() > 0.0) ? dW : -dW);
            result.setReal(number.getImaginary() / (2.0 * result.getImaginary()));
        }

        return result;
    }

    private static double getAbsoluteValue(ComplexNumber number) {
        double dX;
        double dY;
        double dTemp;
        double dAnswer;

        dX = Math.abs(number.getReal());
        dY = Math.abs(number.getImaginary());
        if (dX == 0)
            dAnswer = dY;
        else if (dY == 0)
            dAnswer = dX;
        else if (dX > dY) {
            dTemp = dY / dX;
            dAnswer = dX * Math.sqrt((1.0 + dTemp * dTemp));
        } else {
            dTemp = dX / dY;
            dAnswer = dY * Math.sqrt((1.0 + dTemp * dTemp));
        }

        return dAnswer;
    }
}
