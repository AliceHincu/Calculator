package com.example.calculator.gui;

import com.example.calculator.convertor.ConvertorExpressionToList;
import com.example.calculator.domain.ComplexNumber;
import com.example.calculator.gui.enums.CalculatorButtonsTextGui;
import com.example.calculator.gui.enums.CalculatorDigitsGui;
import com.example.calculator.gui.enums.CalculatorOperationsGui;
import com.example.calculator.gui.enums.CalculatorSymbolsGui;
import com.example.calculator.operations.UnaryOperatorEnum;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.service.HistoryService;
import com.example.calculator.validation.Validator;
import com.example.calculator.validation.ValidatorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.*;

public class CalculatorGUI {
    @FXML
    private Button zeroButton = new Button(CalculatorDigitsGui.ZERO.getSymbol());
    @FXML
    private Button oneButton = new Button(CalculatorDigitsGui.ONE.getSymbol());
    @FXML
    private Button twoButton = new Button(CalculatorDigitsGui.TWO.getSymbol());
    @FXML
    private Button threeButton = new Button(CalculatorDigitsGui.THREE.getSymbol());
    @FXML
    private Button fourButton = new Button(CalculatorDigitsGui.FOUR.getSymbol());
    @FXML
    private Button fiveButton = new Button(CalculatorDigitsGui.FIVE.getSymbol());
    @FXML
    private Button sixButton = new Button(CalculatorDigitsGui.SIX.getSymbol());
    @FXML
    private Button sevenButton = new Button(CalculatorDigitsGui.SEVEN.getSymbol());
    @FXML
    private Button eightButton = new Button(CalculatorDigitsGui.EIGHT.getSymbol());
    @FXML
    private Button nineButton = new Button(CalculatorDigitsGui.NINE.getSymbol());
    @FXML
    private Button iButton = new Button(ComplexNumber.IMAGINARY_UNIT);
    @FXML
    private Button periodButton = new Button(CalculatorDigitsGui.POINT.getSymbol());
    @FXML
    private Button commaButton = new Button(CalculatorSymbolsGui.COMMA.getSymbol());
    @FXML
    private Button clearButton = new Button(CalculatorButtonsTextGui.CLEAR.getSymbol());
    @FXML
    private Button deleteButton = new Button(CalculatorButtonsTextGui.DELETE_CHAR.getSymbol());
    @FXML
    private Button lnButton = new Button(CalculatorOperationsGui.LN.getOperation());
    @FXML
    private Button expButton = new Button(CalculatorOperationsGui.EXP.getOperation());
    @FXML
    private Button sqrtButton = new Button(CalculatorOperationsGui.SQRT.getOperation());
    @FXML
    private Button maxButton = new Button(CalculatorOperationsGui.MAX.getOperation());
    @FXML
    private Button minButton = new Button(CalculatorOperationsGui.MIN.getOperation());
    @FXML
    private Button changeSgnButton = new Button(CalculatorButtonsTextGui.SIGN_CHANGE.getSymbol());

    @FXML
    private TextField currentNumber = new TextField(CalculatorDigitsGui.ZERO.getSymbol());
    @FXML
    private TextField equation = new TextField(CalculatorDigitsGui.ZERO.getSymbol());

    private List<String> equationList;

    private List<Button> numberButtonsList;
    Deque<String> stackOperationsComma; // only allow comma for max and min operands
    private CalculatorService calculatorService;
    private HistoryService historyService;
    private Validator validator;

    private ConvertorExpressionToList regexConvertor;

    @FXML
    public void initialize() {
        //todo: implement history
        //todo: when you press right paranthesis, don t putt always 0
        //todo:
        equationList = new ArrayList<>();
        numberButtonsList = new ArrayList<>(Arrays.asList(
                zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, iButton, periodButton
        ));
        calculatorService = new CalculatorService();
        historyService = new HistoryService();
        validator = new Validator();
        regexConvertor = new ConvertorExpressionToList();
        stackOperationsComma = new LinkedList<>();
        commaButton.setDisable(true);
    }

    /**
     * Takes the digit from the button clicked by the user.
     * - Change the clear button from C to CE
     * - If the digit is "i", then disables all number related buttons
     * - Adds the digit to the current displayed number
     * - if the current number is zero and the digit is not ".", replace the current number with the digit
     * - if the digit is ".", add it after the current number (only if there isn't already a "." in the number)
     */
    public void processNumber(ActionEvent e) {
        clearButton.setText(CalculatorButtonsTextGui.CLEAR_ENTRY.getSymbol());
        String digit = ((Button) e.getSource()).getText();

        if (digit.equals(ComplexNumber.IMAGINARY_UNIT))
            disableNumberButtons();
        if (digit.equals(CalculatorDigitsGui.POINT.getSymbol()) && currentNumber.getText().contains(CalculatorDigitsGui.POINT.getSymbol()))
            return;

        if (currentNumber.getText().equals(CalculatorDigitsGui.ZERO.getSymbol()) && !digit.equals(CalculatorDigitsGui.POINT.getSymbol())) {
            currentNumber.setText(digit);
        } else {
            currentNumber.setText(currentNumber.getText() + digit);
        }
    }

    /**
     * A basic operation is an operation that doesn't require brackets.
     * Takes the operation from the button clicked by the user.
     * - If the last element from the saved expression is a basic operation, then replace only the last operation
     * - If the last element is an operation with brackets, only add the operand
     * - Else, add both the current number and the selected operation to the saved expression.
     * Also enable buttons
     */
    public void processOperandBasic(ActionEvent e) {
        String operand = ((Button) e.getSource()).getText();
        String lastElement = equationList.isEmpty() ? null : equationList.get(equationList.size() - 1);

        if (currentNumber.getText().equals(CalculatorDigitsGui.ZERO.getSymbol()) && !equationList.isEmpty() && CalculatorOperationsGui.isOperation(lastElement)) {
            equationList.set(equationList.size() - 1, operand);
        } else {
            if (!CalculatorSymbolsGui.isSymbol(lastElement)) {
                equationList.add(currentNumber.getText());
            }
            equationList.add(operand);
        }

        this.setExpressionTextToList();
        cleanExpression();
        this.enableNumberButtons();
    }

    /**
     * Takes the operation from the button clicked by the user.
     * - Add to equation the operand only if the last element is an operand or a symbol.
     * - Only enable comma if it's max/min.
     */
    public void processOperandWithBrackets(ActionEvent e) {
        String operand = ((Button) e.getSource()).getText();
        String lastElement = equationList.isEmpty() ? operand : equationList.get(equationList.size() - 1);

        if (CalculatorOperationsGui.isOperation(lastElement) || CalculatorSymbolsGui.isSymbol(lastElement)) {
            equationList.add(operand);
            equationList.add(CalculatorSymbolsGui.LEFT_BRACKET.getSymbol());
            this.setExpressionTextToList();
        }

        commaButton.setDisable(!operand.equals(CalculatorOperationsGui.MAX.getOperation()) && !operand.equals(CalculatorOperationsGui.MIN.getOperation()));
        stackOperationsComma.push(operand);

    }

    /**
     * Takes the symbol from the button clicked by the user. Add it to the equation
     * - if the symbol is "," or ")", also add the current number before the symbol
     * - if it's ")", enable comma if the previous unfinished operand is max/min and disable number buttons
     * - if it's a "," , enable number buttons
     */
    public void processSymbol(ActionEvent e) {
        String symbol = ((Button) e.getSource()).getText();

        if(symbol.equals(CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol())){
            stackOperationsComma.pop();
            String currentOp = stackOperationsComma.peek();
            System.out.println(currentOp);
            commaButton.setDisable(currentOp == null || (!currentOp.equals(CalculatorOperationsGui.MAX.getOperation()) && !currentOp.equals(CalculatorOperationsGui.MIN.getOperation())));
        }
        if (symbol.equals(CalculatorSymbolsGui.COMMA.getSymbol()) || symbol.equals(CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol())) {
            equationList.add(currentNumber.getText());
        }
        equationList.add(symbol);
        this.setExpressionTextToList();
        cleanExpression();
    }

    public void processChangeSign(ActionEvent e) {
        //todo
    }


    /**
     * If the text from the clear button is CE, only clear current number. Else, clear everything.
     */
    public void clearShownText(ActionEvent e) {
        String text = ((Button) e.getSource()).getText();

        if (text.equals(CalculatorButtonsTextGui.CLEAR_ENTRY.getSymbol())) {
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
        if (currentText.charAt(currentText.length() - 1) == CalculatorDigitsGui.IMAGINARY_UNIT.getSymbol().charAt(0)) {
            enableNumberButtons();
        }
        if (currentText.length() == 1) {
            currentNumber.setText(CalculatorDigitsGui.ZERO.getSymbol());
            clearButton.setText(CalculatorButtonsTextGui.CLEAR.getSymbol());
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
            System.out.println(equationText);
            System.out.println(result);
            this.clean();
            currentNumber.setText(result.toString());
            this.enableNumberButtons();
        } catch (ArithmeticException | ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println("You mathematical expression is incorrect! Please check it again.");
        }
    }

    public void updateEquation(){
        equationList = regexConvertor.convert(equation.getText());
    }

    /**
     * After "=" is pressed, complete the expression where it needs to:
     * - If last element is a basic operation, add the current number to the list.
     * - If last element is a bracket, just calculate.
     */
    private void finalizeExpression() {
        String lastElement = equationList.isEmpty() ? CalculatorDigitsGui.ZERO.getSymbol() : equationList.get(equationList.size() - 1);
        if (CalculatorOperationsGui.isOperation(lastElement)) {
            equationList.add(currentNumber.getText());
            this.setExpressionTextToList();
        }
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

    private void clean() {
        currentNumber.setText(CalculatorDigitsGui.ZERO.getSymbol());
        equation.setText(CalculatorDigitsGui.ZERO.getSymbol());
        equationList.clear();
        clearButton.setText(CalculatorButtonsTextGui.CLEAR.getSymbol());
    }

    private void cleanExpression() {
        currentNumber.setText(CalculatorDigitsGui.ZERO.getSymbol());
        clearButton.setText(CalculatorButtonsTextGui.CLEAR.getSymbol());
    }

}