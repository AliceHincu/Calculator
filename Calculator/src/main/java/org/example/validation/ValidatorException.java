package org.example.validation;

public class ValidatorException extends Exception {
    public ValidatorException(String s)
    {
        // Call constructor of parent Exception
        super(s);
    }
}