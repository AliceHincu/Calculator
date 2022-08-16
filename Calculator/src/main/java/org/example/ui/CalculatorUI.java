package org.example.ui;

import org.example.domain.ComplexNumber;
import org.example.service.CalculatorService;
import org.example.service.HistoryService;

import java.util.Objects;
import java.util.Scanner;

public class CalculatorUI {
    public void run() {
        CalculatorService calculatorService = new CalculatorService();
        HistoryService historyService = new HistoryService();
        Scanner scanner = new Scanner(System.in);

        // todo: extract constants - viktor
        // TODO: Interface more friendly - attilo

        System.out.println("Welcome! Please Enter your mathematical expression. Be aware that to exit you can just press" +
                " 0. To show history of results and expressions just type \"history\" \nRules:" +
                "\n\t 1. For unary operations you need to put them in brackets (ex: 5+(-9))" +
                "\n\t 2. For imaginary numbers, if you only want i, you need to put it as 1i.");

        while (true) {
            System.out.println("\n>>Enter expression:");
            String mathematicalExpression = scanner.nextLine();

            if (Objects.equals(mathematicalExpression, "0")) {
                System.out.println("Goodbye!");
                break;
            }
            if (Objects.equals(mathematicalExpression, "history")) {
                System.out.println(historyService.getHistory());
                continue;
            }


            try {
                // TODO : rename RPN ATTILA
                ComplexNumber result = calculatorService.evaluateRPN(mathematicalExpression);
                System.out.println(result);
                historyService.add(mathematicalExpression, result);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("You mathematical expression is incorrect! Please check it again.");
            }
        }
    }
}

