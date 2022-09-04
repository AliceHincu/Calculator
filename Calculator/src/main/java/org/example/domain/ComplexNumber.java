package org.example.domain;

import java.util.Objects;

public class ComplexNumber {
    public static final String IMAGINARY_UNIT = "i";
    private static final double ZERO = 0.0;
    private Double real;
    private Double imaginary;

    public ComplexNumber() {
        this.real = 0.0;
        this.imaginary = 0.0;
    }

    public ComplexNumber(String number) {
        char lastChar = number.charAt(number.length() - 1);
        if (lastChar == 'i') {
            this.real = 0.0;
            this.imaginary = number.equals(ComplexNumber.IMAGINARY_UNIT) ? 1.0 : Double.parseDouble(number.substring(0, number.length() - 1));
        } else {
            this.real = Double.parseDouble(number);
            this.imaginary = 0.0;
        }
    }

    public ComplexNumber(Double real, Double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Double getReal() {
        return real;
    }

    public void setReal(Double real) {
        this.real = real;
    }

    public Double getImaginary() {
        return imaginary;
    }

    public void setImaginary(Double imaginary) {
        if (imaginary.equals(-0d)) {
            imaginary = 0d;
        }

        this.imaginary = imaginary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Objects.equals(real, that.real) && Objects.equals(imaginary, that.imaginary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

    @Override
    public String toString() {
        String result = "";

        if (this.real != ZERO) {
            result += this.real;

            if (this.imaginary != ZERO) {
                if(this.imaginary >= ZERO) {
                    result += "+";
                }
                result += this.imaginary + IMAGINARY_UNIT;
            }
        } else {
            if (this.imaginary != ZERO) {
                result += this.imaginary + IMAGINARY_UNIT;
            } else {
                result = Double.toString(ZERO);
            }
        }

        return result;
    }
}