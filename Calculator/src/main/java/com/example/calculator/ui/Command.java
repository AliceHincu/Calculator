package com.example.calculator.ui;

import java.util.stream.Stream;

public enum Command {
    EXIT_COMMAND("exit", "Exit the program"),
    HISTORY_COMMAND("history", "Show results history of calculated expressions");
    private final String inputCommand;
    private final String menuDescription;

    Command(String inputCommand, String menuDescription) {
        this.inputCommand = inputCommand;
        this.menuDescription = menuDescription;
    }

    public static String getMenuText() {
        StringBuilder text = new StringBuilder("* Commands:");
        for (Command c : Command.values()) {
            text.append(String.format("\n\t%s -> %s", c.getInputCommand(), c.getMenuDescription()));
        }
        return text.toString();
    }

    public String getInputCommand() {
        return inputCommand;
    }

    public String getMenuDescription() {
        return menuDescription;
    }
}
