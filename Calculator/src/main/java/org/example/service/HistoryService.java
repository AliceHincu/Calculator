package org.example.service;

import org.example.domain.ComplexNumber;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryService {
    private final Map<String, ComplexNumber> history;

    public HistoryService() {
        history = new HashMap<>();
    }

    public String getHistory() {
        return this.history.entrySet()
                .stream()
                .map(e -> "Expression: " + e.getKey() + " has the result " + e.getValue() + ";")
                .collect(Collectors.joining("\n"));
    }

    public void add(String mathematicalExpression, ComplexNumber result) {
        this.history.put(mathematicalExpression, result);
    }
}
