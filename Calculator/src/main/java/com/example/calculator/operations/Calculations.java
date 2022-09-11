package com.example.calculator.operations;

import com.example.calculator.domain.ComplexNumber;

public class Calculations {
    private Calculations() {
    } // to hide public constructor

    public static final ComplexNumber ZERO_C = new ComplexNumber(0.0, 0.0);

    public static final ComplexNumber ONE_C = new ComplexNumber(1.0, 0.0);

    public static final ComplexNumber I_C = new ComplexNumber(0.0, 1.0);

    /**
     * Sum between two complex numbers
     */
    public static ComplexNumber calculateAddition(ComplexNumber number1, ComplexNumber number2) {
        ComplexNumber result = new ComplexNumber();
        result.setReal(number1.getReal() + number2.getReal());
        result.setImaginary(number1.getImaginary() + number2.getImaginary());

        return result;
    }

    /**
     * Subtracting the second complex number from the first complex number
     */
    public static ComplexNumber calculateSubtraction(ComplexNumber number1, ComplexNumber number2) {
        ComplexNumber result = new ComplexNumber();
        result.setReal(number1.getReal() - number2.getReal());
        result.setImaginary(number1.getImaginary() - number2.getImaginary());

        return result;
    }

    /**
     * Multiplying two complex numbers
     */
    public static ComplexNumber calculateMultiplication(ComplexNumber number1, ComplexNumber number2) {
        // https://www.mathsisfun.com/algebra/complex-number-multiply.html
        ComplexNumber result = new ComplexNumber();
        result.setReal(number1.getReal() * number2.getReal() - number1.getImaginary() * number2.getImaginary());
        result.setImaginary(number1.getReal() * number2.getImaginary() + number2.getReal() * number1.getImaginary());

        return result;
    }

    /**
     * Divide two complex numbers
     * <a href="https://www.cuemath.com/numbers/division-of-complex-numbers/">https://www.cuemath.com/numbers/division-of-complex-numbers/</a>
     */
    public static ComplexNumber calculateDivision(ComplexNumber number1, ComplexNumber number2) {
        if (number2.getImaginary() == 0.0 && number2.getReal() == 0.0) {
            throw new ArithmeticException("Error...Division by 0!");
        }
        ComplexNumber result = new ComplexNumber();
        double denominator = (number2.getReal() * number2.getReal() + number2.getImaginary() * number2.getImaginary());
        result.setReal((number1.getReal() * number2.getReal() + number1.getImaginary() * number2.getImaginary()) / denominator);
        result.setImaginary((number2.getReal() * number1.getImaginary() - number1.getReal() * number2.getImaginary()) / denominator);

        return result;
    }

    /**
     * Remainder from the division of the two numbers
     */
    public static ComplexNumber calculateRemainder(ComplexNumber number1, ComplexNumber number2) {
        return new ComplexNumber(number1.getReal() % number2.getReal(), 0.0);
    }

    /**
     * The minimum between two complex numbers
     */
    public static ComplexNumber calculateMinimum(ComplexNumber number1, ComplexNumber number2) {
        return getAbsoluteValue(number1) < getAbsoluteValue(number2) ? number1 : number2;
    }

    /**
     * The maximum between two complex numbers
     */
    public static ComplexNumber calculateMaximum(ComplexNumber number1, ComplexNumber number2) {
        return getAbsoluteValue(number1) < getAbsoluteValue(number2) ? number2 : number1;
    }

    /**
     * The square root between two numbers
     */
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

    /**
     * Computes the logarithm of this complex number
     */
    public static ComplexNumber calculateNaturalLogarithm(ComplexNumber number) {
        Double modulus = getAbsoluteValue(number);
        Double arg = Math.atan2(number.getImaginary(), number.getReal());
        return new ComplexNumber(Math.log(modulus), arg);
    }

    /**
     * Computes first number raised by the power of the second
     */
    public static ComplexNumber calculatePowerOf(ComplexNumber number1, ComplexNumber number2) {
        if (number1.getImaginary() == 0.0 && number2.getImaginary() == 0.0) {
            Double result = Math.pow(number1.getReal(), number2.getReal());
            return new ComplexNumber(result, 0.0);
        }
        return calculateExponential(calculateMultiplication(calculateNaturalLogarithm(number1), number2));
    }

    /**
     * Computes the complex exponential function, e^z, where z is this complex number.
     */
    public static ComplexNumber calculateExponential(ComplexNumber z) {
        Double length = Math.exp(z.getReal());
        return new ComplexNumber(length * Math.cos(z.getImaginary()), length * Math.sin(z.getImaginary()));
    }

    /**
     * The modulus of a complex number
     */
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
