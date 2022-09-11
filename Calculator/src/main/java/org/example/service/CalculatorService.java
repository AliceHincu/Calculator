package org.example.service;

import org.example.convertor.ConvertorExpressionToList;
import org.example.convertor.ConvertorInfixToPostfix;
import org.example.domain.ComplexNumber;
import org.example.operations.*;

import java.util.*;

public class CalculatorService {
    private final ConvertorExpressionToList regexConvertor;
    private final ConvertorInfixToPostfix expressionConvertor;

    public CalculatorService() {
        this.regexConvertor = new ConvertorExpressionToList();
        this.expressionConvertor = new ConvertorInfixToPostfix();
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">Learn about RPN</a>
     * Uses a convertor to get the postfix notation and then evaluates the expression in postfix form.
     *
     * @param mathematicalExpression - input from user
     * @return - the result of the mathematical expression
     */
    public ComplexNumber evaluateRPN(String mathematicalExpression) {
        List<String> list = this.regexConvertor.convert(mathematicalExpression);
        Queue<String> queue = this.expressionConvertor.convert(list);

        Deque<ComplexNumber> numStack = new LinkedList<>();
        ComplexNumber first;
        ComplexNumber second;

        while (!queue.isEmpty()) {
            final String token = queue.poll();

            if (UnaryOperatorEnum.isOfType(token)) {
                first = numStack.pop();
                numStack.push(UnaryOperatorEnum.calculateExpression(token, first));
            } else if (BinaryOperatorEnum.isOfType(token)) {
                second = numStack.pop();
                first = numStack.pop();
                numStack.push(BinaryOperatorEnum.calculateExpression(token, first, second));
            } else {
                numStack.push(new ComplexNumber(token));
            }
        }

        return numStack.pop();
    }
}
