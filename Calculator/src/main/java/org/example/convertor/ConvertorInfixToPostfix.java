package org.example.convertor;

import org.example.operations.MathSymbol;

import java.util.*;
import java.util.regex.Pattern;

import static org.example.operations.MathSymbol.*;

public class ConvertorInfixToPostfix {
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

        for (String element : mathematicalExpressionElements) {
            if (Objects.equals(element, COMMA.getSymbol())) {
                processComma(queue, stack);
                checkUnary = true;
                continue;
            }

            if (Arrays.asList(MAX.getSymbol(), MIN.getSymbol(), SQRT.getSymbol()).contains(element)) {
                stack.push(element);
                continue;
            }

            // Check if you have a unary minus or plus
            if (checkUnary) {
                checkUnary = false;
                if (Arrays.asList(PLUS.getSymbol(), MINUS.getSymbol()).contains(element)) //instead of (-9) it will be (0-9)
                    queue.add(ZERO.getSymbol());
            }

            if (Pattern.compile("\\d").matcher(element).find()) { // if is number
                queue.add(element);
                continue;
            }

            if (element.equals(LEFT_BRACKET.getSymbol())) {
                checkUnary = true;
                stack.push(element); // conversion from char to string
                continue;
            }

            if (element.equals(RIGHT_BRACKET.getSymbol())) {
                while (!Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol())) {
                    queue.add(String.valueOf(stack.pop()));
                }

                stack.pop();
                continue;
            }

            // if it is one of the basic operations (+,-,*,/)
            while (!stack.isEmpty() && !Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol())
                    && getPrecedence(element) >= getPrecedence(stack.peek())) {
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
        if (!Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol())) {
            String op = stack.pop();
            while (!stack.isEmpty() && !Objects.equals(stack.peek(), LEFT_BRACKET.getSymbol()) && getPrecedence(op) >= getPrecedence(stack.peek())) {
                queue.add(op);
                op = String.valueOf(stack.pop());
            }

            queue.add(op);
        }
    }

    private int getPrecedence(String c) {
//        if (Objects.equals(c, SQRT.getSymbol()))
//            return 4;
//        if (Objects.equals(c, MAX.getSymbol()) || Objects.equals(c, MIN.getSymbol()))
//            return 3;
//        if (Objects.equals(c, MULTIPLY.getSymbol()) || Objects.equals(c, DIVIDE.getSymbol()))
//            return 2;
//        else // '+' or '-'
//            return 1;
        Optional<MathSymbol> op = Arrays.stream(values()).filter(mathSymbol -> mathSymbol.getSymbol().equals(c)).findFirst();
        return op.map(MathSymbol::getPrecedence).orElse(0);
    }
}