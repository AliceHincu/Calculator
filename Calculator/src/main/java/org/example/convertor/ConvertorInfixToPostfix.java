package org.example.convertor;

import java.util.*;
import java.util.regex.Pattern;

public class ConvertorInfixToPostfix {
    private static final String ZERO = "0";
    private static final String COMMA = ",";
    private static final String MAX = "max";
    private static final String MIN = "min";
    private static final String SQRT = "sqrt";
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";


    /**
     * Elements in the queue are now set up in Reverse-Polish notation (RPN)
     * So "2*(5+5*2)/3+(6/2+8)" is now "2 5 5 2 * + * 3 / 6 2 / 8 + +"
     *
     * @param mathematicalExpressionElements - list with ordered operators and operands of the expression
     * @return - result of mathematical expression
     */
    public Queue<String> convert(List<String> mathematicalExpressionElements) {
        // Shunting Yard algorithm implementation
        Queue<String> queue = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();

        boolean checkUnary = true;

        for(String element: mathematicalExpressionElements) {
            if (Objects.equals(element, COMMA)) {
                processComma(queue, stack);
                checkUnary = true;
                continue;
            }

            if (Arrays.asList(MAX, MIN, SQRT).contains(element)) {
                stack.push(element);
                continue;
            }

            // Check if you have a unary minus or plus
            if (checkUnary) {
                checkUnary = false;
                if (Arrays.asList(PLUS, MINUS).contains(element)) //instead of (-9) it will be (0-9)
                    queue.add(ZERO);
            }

            if (Pattern.compile("\\d").matcher(element).find()) { // if is number
                queue.add(element);
                continue;
            }

            if (element.equals(LEFT_BRACKET)) {
                checkUnary = true;
                stack.push(element); // conversion from char to string
                continue;
            }

            if (element.equals(RIGHT_BRACKET)) {
                while (!Objects.equals(stack.peek(), LEFT_BRACKET)) {
                    queue.add(String.valueOf(stack.pop()));
                }

                stack.pop();
                continue;
            }

            // if it is one of the basic operations (+,-,*,/)
            while (!stack.isEmpty() && !Objects.equals(stack.peek(), LEFT_BRACKET)
                    && getPrecedence(element) <= getPrecedence(stack.peek())) {
                queue.add(String.valueOf(stack.pop()));
            }

            stack.push(element);
        }

        while (!stack.isEmpty()) {
            queue.add(String.valueOf(stack.pop()));
        }

        return queue;
    }

    /**
     * Process comma from min(a,b), max(a,b)
     * It calculates the first number (which is the mathematical expression before the comma)
     *
     * @param queue - used for final postfix notation
     * @param stack - temporary stack used for operations which are not in the queue yet
     */
    private void processComma(Queue<String> queue, Deque<String> stack) {
        if (!Objects.equals(stack.peek(), LEFT_BRACKET)) {
            String op = stack.pop();
            while (!stack.isEmpty() && !Objects.equals(stack.peek(), LEFT_BRACKET) && getPrecedence(op) <= getPrecedence(stack.peek())) {
                queue.add(op);
                op = String.valueOf(stack.pop());
            }

            queue.add(op);
        }
    }

    // Operator precedence - PEMDAS rule. All 4 have same left-associativity.
    private int getPrecedence(String c) {
        if (Objects.equals(c, "sqrt"))
            return 4;
        if (Objects.equals(c, "max") || Objects.equals(c, "min"))
            return 3;
        if (Objects.equals(c, "*") || Objects.equals(c, "/"))
            return 2;
        else // '+' or '-'
            return 1;
    }
}