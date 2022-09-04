package org.example.validation;

public class Validator extends Throwable {
    BalancedBrackets validatorBrackets;

    public Validator() {
        this.validatorBrackets = new BalancedBrackets();
    }

    public void validate(String mathematicalExpression) throws ValidatorException {
        if(!this.validatorBrackets.areBracketsBalanced(mathematicalExpression)) {
            if(mathematicalExpression.contains(")") || mathematicalExpression.contains("(")) {
                throw new ValidatorException("Please check parentheses again!");
            }
        }
    }
}
