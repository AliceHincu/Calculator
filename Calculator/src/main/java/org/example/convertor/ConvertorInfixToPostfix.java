package org.example.convertor;

import org.example.domain.ComplexNumber;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static org.example.operations.MathSymbol.*;

public class ConvertorInfixToPostfix {
    Queue<String> queue; // used for final postfix notation
    Deque<String> stack; // temporary stack used for operations which are not in the queue yet
    boolean checkUnary;
    Map<String, Consumer<String>> processMethods;

    public ConvertorInfixToPostfix() {
        processMethods = new HashMap<>();
        populateMap();
        resetFields();
    }

    /**
     * Shunting Yard algorithm implementation
     * Elements in the queue are now set up in Reverse-Polish notation (RPN)
     * So "2*(5+5*2)/3+(6/2+8)" is now "2 5 5 2 * + * 3 / 6 2 / 8 + +"
     *
     * @param mathematicalExpressionElements - list with ordered operators and operands of the expression
     * @return - result of mathematical expression
     */
    public Queue<String> convert(List<String> mathematicalExpressionElements) {
        resetFields();

        for (String element : mathematicalExpressionElements) {
            checkUnary(element);
            processMethods.getOrDefault(element, this::processNumber).accept(element);
        }

        while (!stack.isEmpty()) {
            queue.add(String.valueOf(stack.pop()));
        }

        return queue;
    }

    /**
     * Treat the case when the element can be a unary sign
     *
     * @param element - current math element from expression (symbol or number)
     */
    private void checkUnary(String element) {
        if (checkUnary) {
            checkUnary = false;
            if (Arrays.asList(ADDITION.getSymbol(), SUBTRACTION.getSymbol()).contains(element)) //instead of (-9) it will be (0-9)
                queue.add("0");
        }
    }

    /**
     * Process comma from min(a,b), max(a,b)
     * If before the comma it is a mathematical expression, it adds the mini expression queue. If it is a number, it ignores it.
     */
    private void processComma(String element) {
        if (!Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol())) {
            String op = stack.pop();
            while (!stack.isEmpty() && !Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol()) && getPrecedenceOfElement(op) < getPrecedenceOfElement(stack.peek())) {
                queue.add(op);
                op = String.valueOf(stack.pop());
            }
            queue.add(op);
        }
        checkUnary = true;
    }

    /**
     * If it's a unary operator, then push it to the stack
     *
     * @param element - current math element from expression (symbol or number)
     */
    private void processUnaryOperator(String element) {
        stack.push(element);
    }

    /**
     * If it's a number, then add it to the queue
     *
     * @param element - current math element from expression (symbol or number)
     */
    private void processNumber(String element) {
        if (Pattern.compile("\\d").matcher(element).find() || element.equals(ComplexNumber.IMAGINARY_UNIT)) {
            queue.add(element);
        }
    }

    /**
     * If it's a left bracket, then push it to stack and start checking for a unary sign
     * If it's a right bracket, then add the mini expression from the stack to the queue
     *
     * @param element - current math element from expression (symbol or number)
     */
    private void processBrackets(String element) {
        if (element.equals(LEFT_BRACKET.getSymbol())) {
            checkUnary = true;
            stack.push(element);
        } else if (element.equals(RIGHT_BRACKET.getSymbol())) {
            while (!Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol())) {
                queue.add(String.valueOf(stack.pop()));
            }
            stack.pop();
        }
    }

    /**
     * If it's a basic operation (precedence 3 or 4) then add the mini expression from the stack to the queue
     *
     * @param element - current math element from expression (symbol or number)
     */
    private void processBinaryOperator(String element) {
        while (!stack.isEmpty() && !Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol())
                && getPrecedenceOfElement(element) >= getPrecedenceOfElement(stack.peek())) {
            queue.add(String.valueOf(stack.pop()));
        }
        stack.push(element);
    }

    private void populateMap() {
        processMethods.put(COMMA.getSymbol(), this::processComma);
        processMethods.put(MAX.getSymbol(), this::processUnaryOperator);
        processMethods.put(MIN.getSymbol(), this::processUnaryOperator);
        processMethods.put(SQRT.getSymbol(), this::processUnaryOperator);
        processMethods.put(LEFT_BRACKET.getSymbol(), this::processBrackets);
        processMethods.put(RIGHT_BRACKET.getSymbol(), this::processBrackets);
        processMethods.put(ADDITION.getSymbol(), this::processBinaryOperator);
        processMethods.put(SUBTRACTION.getSymbol(), this::processBinaryOperator);
        processMethods.put(MULTIPLICATION.getSymbol(), this::processBinaryOperator);
        processMethods.put(DIVISION.getSymbol(), this::processBinaryOperator);
    }

    private void resetFields() {
        queue = new LinkedList<>();
        stack = new LinkedList<>();
        checkUnary = true;
    }
}