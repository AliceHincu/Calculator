package com.example.calculator.validation;

import java.util.ArrayDeque;
import java.util.Deque;

public class BalancedBrackets {
    public boolean areBracketsBalanced(String expr) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < expr.length(); i++) {
            char x = expr.charAt(i);

            if (x == '(' || x == '[' || x == '{') {
                stack.push(x);
                continue;
            }

            // If current character is not opening bracket, then it must be closing. So stack cannot be empty at this point.
            if (stack.isEmpty())
                return false;

            char check;
            switch (x) {
                case ')' -> {
                    check = stack.pop();
                    if (check == '{' || check == '[')
                        return false;
                }
                case '}' -> {
                    check = stack.pop();
                    if (check == '(' || check == '[')
                        return false;
                }
                case ']' -> {
                    check = stack.pop();
                    if (check == '(' || check == '{')
                        return false;
                }
            }
        }

        // Check Empty Stack
        return (stack.isEmpty());
    }
}