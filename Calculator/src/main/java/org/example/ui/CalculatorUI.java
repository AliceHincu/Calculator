package org.example.ui;

import org.example.domain.ComplexNumber;
import org.example.service.CalculatorService;
import org.example.service.HistoryService;
import org.example.validation.Validator;
import org.example.validation.ValidatorException;

import java.util.HashMap;
import java.util.Scanner;

public class CalculatorUI {
    private final CalculatorService calculatorService = new CalculatorService();
    private final HistoryService historyService = new HistoryService();
    private final Validator validator = new Validator();
    private String userInput = "";
    private boolean isRunning = true;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Runnable> commands = getCommandsMap();
        this.printMenu();

        while (isRunning) {
            System.out.println("\n>>Enter command or mathematical expression:");
            this.userInput = scanner.nextLine();
            commands.getOrDefault(this.userInput, this::calculateExpression).run();
        }
    }

    private HashMap<String, Runnable> getCommandsMap(){
        HashMap<String, Runnable> commands = new HashMap<>();
        commands.put(Command.EXIT_COMMAND.toString(), this::exit);
        commands.put(Command.HISTORY_COMMAND.toString(), this::showHistory);

        return commands;
    }

    private void exit() {
        System.out.println("Goodbye!");
        this.isRunning = false;
    }

    private void showHistory() {
        System.out.println(this.historyService.getHistory());
    }

    private void calculateExpression() {
        try {
            this.validator.validate(this.userInput);
            ComplexNumber result = this.calculatorService.evaluateRPN(this.userInput);
            System.out.println(result);
            historyService.add(userInput, result);
        } catch (ArithmeticException | ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("You mathematical expression is incorrect! Please check it again.");
        }
    }

    private void printMenu() {
        String commandsText = Command.getMenuText();
        System.out.println("""
                Welcome! Please Enter your mathematical expression. Be aware that to exit you can just press 0. To show history of results and expressions just type "history"\s
                """ + commandsText + """                
                \n* Rules:
                \t 1. For unary signs, you need to put them in brackets (ex: 5+(-9))
               *  Available operations: +,-,*,/,min,max,sqrt""");
    }
}

