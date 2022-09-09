package org.example.convertor;

import org.example.domain.ComplexNumber;
import org.example.operations.BinaryOperatorEnum;
import org.example.operations.UnaryOperatorEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertorExpressionToList {
    private final String expressionSplitRegex;

    public ConvertorExpressionToList() {
        String operatorsRegex = buildOperatorsRegex();
        String operandsRegex = "(\\d+(\\.\\d+)?" + ComplexNumber.IMAGINARY_UNIT + "?)";
        this.expressionSplitRegex = operandsRegex + "|" + operatorsRegex;
    }

    /**
     * Convert the expression given by the user to a list of operations and operands.
     * Example :
     * - before: "1 + 3.2 - 4i - min(-4, 6.5i + 9) / 6"
     * - after: [1, +, 3.2, -, 4i, -, min, (, -, 4, ,, 6.5i, +, 9, ), /, 6]
     *
     * @param expression: mathematical expression string given by the user
     * @return - the list with all operators and operands of the given expression
     */
    public List<String> convert(String expression) {
        List<String> resultList = new ArrayList<>();
        Pattern pattern = Pattern.compile(this.expressionSplitRegex);
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            resultList.add(matcher.group(0));
        }

        return resultList;
    }

    /**
     * Create the regex for the available operators + the special characters
     *
     * @return - regex string with operators
     */
    private String buildOperatorsRegex() {
        String result = String.join("|", getOperators());
        return "(" + result + ")";
    }

    /**
     * Parse the unary and binary operators from their respective enums and add them to the list.
     * Also add some special characters to the list (left and right parentheses, comma)
     *
     * @return - the list with all operators
     */
    private List<String> getOperators() {
        List<String> binaryOp = Arrays
                .stream(BinaryOperatorEnum.values())
                .map(BinaryOperatorEnum::getRegex)
                .toList();
        List<String> unaryOp = Arrays
                .stream(UnaryOperatorEnum.values())
                .map(UnaryOperatorEnum::getRegex)
                .toList();
        List<String> extraStuff = new ArrayList<>(Arrays.asList("\\(", "\\)", ",", ComplexNumber.IMAGINARY_UNIT));

        List<String> operations = new ArrayList<>();
        operations.addAll(binaryOp);
        operations.addAll(unaryOp);
        operations.addAll(extraStuff);

        return operations;
    }

    /* -- regex explained:
     * //      operandsRegex:
     * //           - \d+ -> matches a digit between one and unlimited times. This is the whole part
     * //           - (\.\d+)? -> This is the fractional part that can exist or not
     * //           - i? -> matches letter "i" zero or one time.
     * //      operatorsRegex:
     * //           - matches one of the available operations
     */
}
