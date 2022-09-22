package com.example.calculator.gui;

import com.example.calculator.StarterGUI;
import com.example.calculator.convertor.ConvertorExpressionToList;
import com.example.calculator.domain.ComplexNumber;
import com.example.calculator.gui.enums.CalculatorButtonsTextGui;
import com.example.calculator.gui.enums.CalculatorDigitsGui;
import com.example.calculator.gui.enums.CalculatorOperationsGui;
import com.example.calculator.gui.enums.CalculatorSymbolsGui;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.validation.Validator;
import com.example.calculator.validation.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class CalculatorGUI {
    /**
     * --- buttons - operations ---
     **/
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
    private Button changeSgnButton = new Button(CalculatorOperationsGui.LN.getOperation());
    @FXML
    private Button divisionButton = new Button(CalculatorOperationsGui.DIVISION.getOperation());
    @FXML
    private Button multiplicationButton = new Button(CalculatorOperationsGui.MULTIPLICATION.getOperation());
    @FXML
    private Button minusButton = new Button(CalculatorOperationsGui.SUBTRACTION.getOperation());
    @FXML
    private Button plusButton = new Button(CalculatorOperationsGui.ADDITION.getOperation());
    @FXML
    private Button moduloButton = new Button(CalculatorOperationsGui.MODULO.getOperation());
    @FXML
    private Button powerButton = new Button(CalculatorOperationsGui.POWER.getOperation());
    @FXML
    private Button equalButton = new Button("=");
    /**
     * --- buttons - number ---
     **/
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
    /**
     * --- buttons - symbols and text ---
     **/
    @FXML
    private Button commaButton = new Button(CalculatorSymbolsGui.COMMA.getSymbol());
    @FXML
    private Button leftBracketButton = new Button(CalculatorSymbolsGui.LEFT_BRACKET.getSymbol());
    @FXML
    private Button rightBracketButton = new Button(CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol());
    @FXML
    private Button clearButton = new Button(CalculatorButtonsTextGui.CLEAR.getSymbol());
    @FXML
    private Button backspaceButton = new Button(CalculatorButtonsTextGui.DELETE_CHAR.getSymbol());
    /**
     * --- text fields and lists ---
     */
    @FXML
    private TextField currentNumberTextField = new TextField(CalculatorDigitsGui.ZERO.getSymbol());
    @FXML
    private TextField equationTextField = new TextField(CalculatorDigitsGui.ZERO.getSymbol());
    @FXML
    private ListView<String> historyListView;
    String infoFileName = "info-gui.fxml";
    ObservableList<String> historyList;
    private List<String> equationList;
    Deque<String> stackParentheses;
    private CalculatorService calculatorService;
    private Validator validator;
    private ConvertorExpressionToList regexConvertor;
    private final HashMap<KeyCode, Button> keyButtonMap = new HashMap<>();
    private final HashMap<KeyCombination, Button> keyCombinationButtonMap = new HashMap<>();

    @FXML
    public void initialize() {
        calculatorService = new CalculatorService();
        validator = new Validator();
        regexConvertor = new ConvertorExpressionToList();
        stackParentheses = new LinkedList<>();

        equationList = new ArrayList<>();

        historyList = FXCollections.observableArrayList();
        historyListView.setItems(historyList);

        populateKeyButtonMap();
        populateKeyCombinationButtonMap();

//        commaButton.setDisable(true);
        currentNumberTextField.setEditable(false);
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

        if (digit.equals(CalculatorDigitsGui.POINT.getSymbol()) && currentNumberTextField.getText().contains(CalculatorDigitsGui.POINT.getSymbol()))
            return;

        if (currentNumberTextField.getText().equals(CalculatorDigitsGui.ZERO.getSymbol()) && !digit.equals(CalculatorDigitsGui.POINT.getSymbol())) {
            currentNumberTextField.setText(digit);
        } else {
            currentNumberTextField.setText(currentNumberTextField.getText() + digit);
        }
    }

    /**
     * A basic operation is an operation that doesn't require brackets.
     * Takes the operation from the button clicked by the user.
     * - If the last element from the saved expression is a basic operation, then replace only the last operation
     * - If the last element is ')', only add the operand
     * - Else, add both the current number and the selected operation to the saved expression.
     * Also enable buttons
     */
    public void processOperandBasic(ActionEvent e) {
        String operand = ((Button) e.getSource()).getText();
        String lastElement = equationList.isEmpty() ? null : equationList.get(equationList.size() - 1);

        if (currentNumberTextField.getText().equals(CalculatorDigitsGui.ZERO.getSymbol()) && !equationList.isEmpty() && CalculatorOperationsGui.isOperation(lastElement)) {
            equationList.set(equationList.size() - 1, operand);
            this.setExpressionTextToList();
            return;
        }

        List<String> elementsToAdd = new LinkedList<>();
        if (!CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol().equals(lastElement)) {
            elementsToAdd.add(currentNumberTextField.getText());
        }
        elementsToAdd.add(operand);


        this.updateEquationList(elementsToAdd);
        cleanExpression();
    }

    /**
     * Takes the operation from the button clicked by the user.
     * - Add to equation the operand only if the last element is not a number.
     */
    public void processOperandWithBrackets(ActionEvent e) {
        String operand = ((Button) e.getSource()).getText();
        String lastElement = equationList.isEmpty() ? operand : equationList.get(equationList.size() - 1);

        if (Character.isDefined(lastElement.charAt(0)))
            this.updateEquationList(Arrays.asList(operand, CalculatorSymbolsGui.LEFT_BRACKET.getSymbol()));

        stackParentheses.push(operand);

    }


    public void processSymbol(ActionEvent e) {
        String symbol = ((Button) e.getSource()).getText();

        if (CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol().equals(symbol)) {
            if (stackParentheses.peek() != null) {
                processRightBracket();
            } else return;
        }
        if (CalculatorSymbolsGui.LEFT_BRACKET.getSymbol().equals(symbol))
            processLeftBracket();
        if (CalculatorSymbolsGui.COMMA.getSymbol().equals(symbol))
            processComa();

        this.updateEquationList(Collections.singletonList(symbol));
        cleanExpression();
    }

    /**
     * Add the current number only if the previous character is not ")"
     * Enable comma & disable number buttons only if the previous unfinished operand is max/min
     * If number of ")" > "(", don't add the symbol.
     */
    private void processRightBracket() {
        String lastElement = equationList.get(equationList.size() - 1);

        if (!lastElement.equals(CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol()))
            equationList.add(currentNumberTextField.getText());
        if (stackParentheses.peek() != null)
            stackParentheses.pop();
    }

    /**
     * Add "(" only if the last element is not a number or ")"
     */
    private void processLeftBracket() {
        String lastElement = equationList.isEmpty() ? null : equationList.get(equationList.size() - 1);

        if (CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol().equals(lastElement) && Character.isDefined(lastElement.charAt(0)))
            return;

        stackParentheses.push(CalculatorSymbolsGui.LEFT_BRACKET.getSymbol());
    }

    /**
     * Add the current number only if the previous character is not ")"
     */
    private void processComa() {
        String lastElement = equationList.get(equationList.size() - 1);

        if (!lastElement.equals(CalculatorSymbolsGui.RIGHT_BRACKET.getSymbol()))
            equationList.add(currentNumberTextField.getText());
    }

    /**
     * If it is a positive number, add "(- ...)"
     * If it a negative number, get rid of parentheses and minus sign
     */
    public void processChangeSign() {
        String currentNumber = currentNumberTextField.getText();
        String newNumber;
        if(currentNumber.charAt(0) == '(')
            newNumber = currentNumber.substring(2, currentNumber.length()-1);
        else
            newNumber = "(-" + currentNumber + ")";

        currentNumberTextField.setText(newNumber);
    }

    public void processInfo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StarterGUI.class.getResource(infoFileName));
            Parent rootInfo = fxmlLoader.load();
            Stage stageInfo = new Stage();
            stageInfo.setTitle("Info");
            stageInfo.setScene(new Scene(rootInfo, 258, 300));
            stageInfo.show();

        } catch (IOException ex) {
            showError("Can't load info window!");
        }
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
    public void deleteShownText() {
        String currentText = currentNumberTextField.getText();

        if (currentText.length() == 1) {
            currentNumberTextField.setText(CalculatorDigitsGui.ZERO.getSymbol());
            clearButton.setText(CalculatorButtonsTextGui.CLEAR.getSymbol());
        } else {
            currentNumberTextField.setText(currentText.substring(0, currentText.length() - 1));
        }

    }

    public void processEvaluation() {
        finalizeExpression();
        String equationText = equationTextField.getText();

        try {
            this.validator.validate(equationText);
            ComplexNumber result = this.calculatorService.evaluateRPN(equationText);
            historyList.add(0, equationText + " = " + result.toString());
            this.clean();
            currentNumberTextField.setText(result.toString());
        } catch (ArithmeticException | ValidatorException exception) {
            showError(exception.getMessage());
        } catch (Exception exception) {
            showError("Incorrect expression!");
        }
    }

    @FXML
    public void updateEquationListOnKeyPressed() {
        equationList = regexConvertor.convert(equationTextField.getText());
    }

    private void updateEquationList(List<String> elementsToAdd) {
        equationList.addAll(elementsToAdd);
        this.setExpressionTextToList();
    }

    private void showError(String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(text);
        a.show();
    }

    /**
     * After "=" is pressed, complete the expression where it needs to:
     * - If last element is a basic operation, add the current number to the list.
     * - If last element is a bracket, just calculate.
     */
    private void finalizeExpression() {
        String lastElement = equationList.isEmpty() ? CalculatorDigitsGui.ZERO.getSymbol() : equationList.get(equationList.size() - 1);
        if (CalculatorOperationsGui.isOperation(lastElement))
            this.updateEquationList(Collections.singletonList(currentNumberTextField.getText()));
    }

    private void setExpressionTextToList() {
        String text = String.join("", equationList);
        equationTextField.setText(text);
    }

    private void clean() {
        currentNumberTextField.setText(CalculatorDigitsGui.ZERO.getSymbol());
        equationTextField.setText(CalculatorDigitsGui.ZERO.getSymbol());
        equationList.clear();
        clearButton.setText(CalculatorButtonsTextGui.CLEAR.getSymbol());
    }

    private void cleanExpression() {
        currentNumberTextField.setText(CalculatorDigitsGui.ZERO.getSymbol());
        clearButton.setText(CalculatorButtonsTextGui.CLEAR.getSymbol());
    }

    public void disableFocus(MouseEvent event) {
        if (!equationTextField.equals(event.getSource()))
            equationTextField.getParent().requestFocus();
    }

    public void keyPressed(KeyEvent event) {
        if (!equationTextField.isFocused()) {
            // check for combination, then for simple key
            var optional = keyCombinationButtonMap.entrySet().stream()
                    .filter(entry -> entry.getKey().match(event))
                    .findFirst();
            if (optional.isPresent()) {
                Button button = optional.get().getValue();
                button.fire();
                button.getParent().requestFocus();

            } else {
                Button button = keyButtonMap.get(event.getCode());
                if (button != null) {
                    button.fire();
                    button.getParent().requestFocus();
                }
            }
        }
    }

    private void populateKeyButtonMap() {
        // '%' does not have a keycode...
        // -- numbers
        keyButtonMap.put(KeyCode.DIGIT0, zeroButton);
        keyButtonMap.put(KeyCode.DIGIT1, oneButton);
        keyButtonMap.put(KeyCode.DIGIT2, twoButton);
        keyButtonMap.put(KeyCode.DIGIT3, threeButton);
        keyButtonMap.put(KeyCode.DIGIT4, fourButton);
        keyButtonMap.put(KeyCode.DIGIT5, fiveButton);
        keyButtonMap.put(KeyCode.DIGIT6, sixButton);
        keyButtonMap.put(KeyCode.DIGIT7, sevenButton);
        keyButtonMap.put(KeyCode.DIGIT8, eightButton);
        keyButtonMap.put(KeyCode.DIGIT9, nineButton);
        keyButtonMap.put(KeyCode.I, iButton);
        keyButtonMap.put(KeyCode.PERIOD, periodButton);
        // -- operations
        keyButtonMap.put(KeyCode.F1, lnButton);
        keyButtonMap.put(KeyCode.F2, expButton);
        keyButtonMap.put(KeyCode.F3, sqrtButton);
        keyButtonMap.put(KeyCode.F4, maxButton);
        keyButtonMap.put(KeyCode.F5, minButton);
        keyButtonMap.put(KeyCode.F6, changeSgnButton);
        keyButtonMap.put(KeyCode.SLASH, divisionButton);
        keyButtonMap.put(KeyCode.STAR, multiplicationButton);
        keyButtonMap.put(KeyCode.MINUS, minusButton);
        keyButtonMap.put(KeyCode.EQUALS, equalButton);
        keyButtonMap.put(KeyCode.ENTER, equalButton);
        // -- symbols
        keyButtonMap.put(KeyCode.COMMA, commaButton);
        keyButtonMap.put(KeyCode.DELETE, clearButton);
        keyButtonMap.put(KeyCode.BACK_SPACE, backspaceButton);
    }

    private void populateKeyCombinationButtonMap() {
        keyCombinationButtonMap.put(new KeyCharacterCombination("+", KeyCombination.SHIFT_DOWN), plusButton);
        keyCombinationButtonMap.put(new KeyCharacterCombination("*", KeyCombination.SHIFT_DOWN), multiplicationButton);
        keyCombinationButtonMap.put(new KeyCharacterCombination("%", KeyCombination.SHIFT_DOWN), moduloButton);
        keyCombinationButtonMap.put(new KeyCharacterCombination("^", KeyCombination.SHIFT_DOWN), powerButton);
        keyCombinationButtonMap.put(new KeyCharacterCombination("(", KeyCombination.SHIFT_DOWN), leftBracketButton);
        keyCombinationButtonMap.put(new KeyCharacterCombination(")", KeyCombination.SHIFT_DOWN), rightBracketButton);
    }

}