package com.example.calculator.gui;

import com.example.calculator.domain.ComplexNumber;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.service.HistoryService;
import com.example.calculator.validation.Validator;
import com.example.calculator.validation.ValidatorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorGUI {
    @FXML
    private Button zeroButton = new Button(CalculatorSymbolsGui.ZERO.getSymbol());
    @FXML
    private Button oneButton = new Button(CalculatorSymbolsGui.ONE.getSymbol());
    @FXML
    private Button twoButton = new Button(CalculatorSymbolsGui.TWO.getSymbol());
    @FXML
    private Button threeButton = new Button(CalculatorSymbolsGui.THREE.getSymbol());
    @FXML
    private Button fourButton = new Button(CalculatorSymbolsGui.FOUR.getSymbol());
    @FXML
    private Button fiveButton = new Button(CalculatorSymbolsGui.FIVE.getSymbol());
    @FXML
    private Button sixButton = new Button(CalculatorSymbolsGui.SIX.getSymbol());
    @FXML
    private Button sevenButton = new Button(CalculatorSymbolsGui.SEVEN.getSymbol());
    @FXML
    private Button eightButton = new Button(CalculatorSymbolsGui.EIGHT.getSymbol());
    @FXML
    private Button nineButton = new Button(CalculatorSymbolsGui.NINE.getSymbol());
    @FXML
    private Button iButton = new Button(ComplexNumber.IMAGINARY_UNIT);
    @FXML
    private Button periodButton = new Button(CalculatorSymbolsGui.POINT.getSymbol());
    @FXML
    private Button clearButton = new Button(CalculatorSymbolsGui.CLEAR.getSymbol());
    @FXML
    private Button deleteButton = new Button(CalculatorSymbolsGui.DELETE_CHAR.getSymbol());
    @FXML
    private TextField currentNumber = new TextField(CalculatorSymbolsGui.ZERO.getSymbol());
    @FXML
    private Text equation = new Text(CalculatorSymbolsGui.ZERO.getSymbol());

    private List<String> equationList;

    private List<Button> numberButtonsList;

    private String operation = "";
    private CalculatorService calculatorService;
    private HistoryService historyService;
    private Validator validator;

    @FXML
    public void initialize() {
        equationList = new ArrayList<>();
        numberButtonsList = new ArrayList<>(Arrays.asList(
                zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, iButton, periodButton
        ));
        calculatorService = new CalculatorService();
        historyService = new HistoryService();
        validator = new Validator();
    }

    /**
     * Takes the digit from the button clicked by the user.
     * - Change the clear button from C to CE
     * - If the digit is "i", then disables all number related buttons
     * - Adds the digit to the current displayed number
     *      - if the current number is zero and the digit is not ".", replace the current number with the digit
     *      - if the digit is ".", add it after the current number (only if there isn't already a "."=)
     */
    public void processNumber(ActionEvent e) {
        clearButton.setText(CalculatorSymbolsGui.CLEAR_ENTRY.getSymbol());
        String digit = ((Button) e.getSource()).getText();
        if (digit.equals(ComplexNumber.IMAGINARY_UNIT)) {
            disableNumberButtons();
        }

        if(currentNumber.getText().equals(CalculatorSymbolsGui.ZERO.getSymbol()) && !digit.equals(CalculatorSymbolsGui.POINT.getSymbol())){
            currentNumber.setText(digit);
            return;
        }
        if(!(digit.equals(CalculatorSymbolsGui.POINT.getSymbol()) && currentNumber.getText().contains(CalculatorSymbolsGui.POINT.getSymbol()))) {
            currentNumber.setText(currentNumber.getText() + digit);
        }

    }

    /**
     * Takes the operation from the button clicked by the user.
     *  - If the last element from the saved expression is an operation, then only replace the last operation
     *  - Else, add both the current number and the selected operation to the saved expression.
     */
    public void processOperation(ActionEvent e) {
        String operation = ((Button) e.getSource()).getText();
        String oldExpression = equation.getText();
        if(equationList.isEmpty() || CalculatorOperationsGui.isOperation(equationList.get(equationList.size()-2))){

        }
//        String newExpression = oldExpression + currentNumber + operation + CalculatorSymbolsGui.LEFT_BRACKET.getSymbol();
//        savedExpression.setText(newExpression);
    }

    /**
     * A basic operation is an operation that doesn't require brackets.
     * Takes the operation from the button clicked by the user.
     *  - If the last element from the saved expression is an operation, then only replace the last operation
     *  - Else, add both the current number and the selected operation to the saved expression.
     * Also enable buttons
     */
    public void processBasicOperation(ActionEvent e) {
        String operation = ((Button) e.getSource()).getText();

        if(currentNumber.getText().equals(CalculatorSymbolsGui.ZERO.getSymbol()) && !equationList.isEmpty()) {
            equationList.set(equationList.size()-1, operation);
        } else {
            equationList.add(currentNumber.getText());
            equationList.add(operation);
        }

        this.setExpressionTextToList();
        cleanExpression();
        this.enableNumberButtons();
    }


    /**
     * If the text from the clear button is CE, only clear current number. Else, clear everything.
     */
    public void clearShownText(ActionEvent e) {
        String text = ((Button) e.getSource()).getText();

        if (text.equals(CalculatorSymbolsGui.CLEAR_ENTRY.getSymbol())) {
            cleanExpression();
        } else {
            clean();
        }
    }

    /**
     * Delete last character from expression.
     * - if deleted character is the imaginary unit, enable back all the buttons related to a number.
     * - if deleted character is the only character remaining, the current number becomes zero.
     */
    public void deleteShownText(ActionEvent e) {
        String currentText = currentNumber.getText();
        if(currentText.charAt(currentText.length() - 1) == CalculatorSymbolsGui.IMAGINARY_UNIT.getSymbol().charAt(0)){
            enableNumberButtons();
        }
        if (currentText.length() == 1) {
            currentNumber.setText(CalculatorSymbolsGui.ZERO.getSymbol());
            clearButton.setText(CalculatorSymbolsGui.CLEAR.getSymbol());
        } else {
            currentNumber.setText(currentText.substring(0, currentText.length() - 1));
        }

    }

    public void processEvaluation(ActionEvent e) {
        finalizeExpression();
        String equationText = equation.getText();

        try {
            this.validator.validate(equationText);
            ComplexNumber result = this.calculatorService.evaluateRPN(equationText);
            historyService.add(equationText, result);
            this.clean();
            currentNumber.setText(result.toString());
            this.enableNumberButtons();
        } catch (ArithmeticException | ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println("You mathematical expression is incorrect! Please check it again.");
        }
    }

    /**
     * After "=" is pressed, complete the expression where it needs to:
     *  - If last element is an operation, delete it.
     */
    private void finalizeExpression() {
        equationList.add(currentNumber.getText());
        this.setExpressionTextToList();
    }

    private void disableNumberButtons() {
        for (Button button : numberButtonsList) {
            button.setDisable(true);
        }
    }

    private void enableNumberButtons() {
        for (Button button : numberButtonsList) {
            button.setDisable(false);
        }
    }

    private void setExpressionTextToList() {
        String text = String.join("", equationList);
        equation.setText(text);
    }

    private void clean(){
        currentNumber.setText(CalculatorSymbolsGui.ZERO.getSymbol());
        equation.setText(CalculatorSymbolsGui.ZERO.getSymbol());
        equationList.clear();
        operation = "";
        clearButton.setText(CalculatorSymbolsGui.CLEAR.getSymbol());
    }

    private void cleanExpression() {
        currentNumber.setText(CalculatorSymbolsGui.ZERO.getSymbol());
        operation = "";
        clearButton.setText(CalculatorSymbolsGui.CLEAR.getSymbol());
    }

}