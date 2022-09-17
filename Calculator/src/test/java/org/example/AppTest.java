package org.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.calculator.domain.ComplexNumber;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.validation.Validator;
import com.example.calculator.validation.ValidatorException;
import org.junit.Test;

public class AppTest {
    /**
     * Rigorous Test :-)
     * <a href="https://www.redcrab-software.com/en/Calculator/Algebra/Complex">https://www.redcrab-software.com/en/Calculator/Algebra/Complex</a>
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void additionTests() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("100 + 2 + 12"), is(new ComplexNumber(114.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("100 + 2i + 12"), is(new ComplexNumber(112.0, 2.0)));
        assertThat(calculatorService.evaluateRPN("0+0"), is(new ComplexNumber(0.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("2i+3i"), is(new ComplexNumber(0.0, 5.0)));
        assertThat(calculatorService.evaluateRPN("2.2i+3i"), is(new ComplexNumber(0.0, 5.2)));
    }

    @Test
    public void subtractionTests() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("(-9)-5"), is(new ComplexNumber(-14.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("(-9i)-5"), is(new ComplexNumber(-5.0, -9.0)));
        assertThat(calculatorService.evaluateRPN("-9-5"), is(new ComplexNumber(-14.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("-9i-5"), is(new ComplexNumber(-5.0, -9.0)));
        assertThat(calculatorService.evaluateRPN("6-7-8"), is(new ComplexNumber(-9.0, 0.0)));
    }

    @Test
    public void multiplicationTests() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("(-9)*5"), is(new ComplexNumber(-45.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("9*(-5)"), is(new ComplexNumber(-45.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("9*5"), is(new ComplexNumber(45.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("9*5i"), is(new ComplexNumber(0.0, 45.0)));
        assertThat(calculatorService.evaluateRPN("9*5i*10"), is(new ComplexNumber(0.0, 450.0)));
        assertThat(calculatorService.evaluateRPN("1i*1i"), is(new ComplexNumber(-1.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("(-9)*(-4)").toString(), is(new ComplexNumber(36.0, 0.0).toString()));
        assertThat(calculatorService.evaluateRPN("(1+4i)*(5+1i)"), is(new ComplexNumber(1.0, 21.0)));
        assertThat(calculatorService.evaluateRPN("2i*(3-8i)"), is(new ComplexNumber(16.0, 6.0)));
        assertThat(calculatorService.evaluateRPN("(-4)*(13+5i)"), is(new ComplexNumber(-52.0, -20.0)));
        assertThat(calculatorService.evaluateRPN("(-4)*5"), is(new ComplexNumber(-20.0, 0.0)));
    }

    @Test
    public void divisionTest() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("(3+4i)/(8-2i)"),
                is(new ComplexNumber(0.23529411764705882, 0.5588235294117647)));
        assertThat(calculatorService.evaluateRPN("4/2"), is(new ComplexNumber(2.0, 0.0)));
    }

    @Test
    public void basicOperationTests() {
        // tests that only use +,-,*,/, combined
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("2+3+5.6i"), is(new ComplexNumber(5.0, 5.6)));
        assertThat(calculatorService.evaluateRPN("2+3+(-5.6i)"), is(new ComplexNumber(5.0, -5.6)));
        assertThat(calculatorService.evaluateRPN("100.5 + 2i * 12"), is(new ComplexNumber(100.5, 24.0)));
        assertThat(calculatorService.evaluateRPN("2*(5+5*2)/3+(6/2+8)"), is(new ComplexNumber(21.0, 0.0)));
    }

    @Test
    public void testMax() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("1+max(2,3)"), is(new ComplexNumber(4.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("1+2*max(2,3)"), is(new ComplexNumber(7.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("1+2*max(2,max(1,0))"), is(new ComplexNumber(5.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("1+2*max(2,max(1*5,0))"), is(new ComplexNumber(11.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("1+2*max(2,max((5+10)*2,2))"), is(new ComplexNumber(61.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("max((1+1i),(2+1i))"), is(new ComplexNumber(2.0, 1.0)));
        assertThat(calculatorService.evaluateRPN("min(3,2,1)"), is(new ComplexNumber(1.0,0.0)));
    }

    @Test
    public void testMin() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("1+min(2,3)"), is(new ComplexNumber(3.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("1+2*min(2,3)"), is(new ComplexNumber(5.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("min((1+1i),(2+1i))"), is(new ComplexNumber(1.0, 1.0)));
        assertThat(calculatorService.evaluateRPN("min(1+i,2+i)"), is(new ComplexNumber(1.0, 1.0)));
        assertThat(calculatorService.evaluateRPN("min(10/2,10/5)"), is(new ComplexNumber(2.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("min(10,100)/min(5,60)"), is(new ComplexNumber(2.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("min(0 + max(4, 4), 5)"), is(new ComplexNumber(4.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("min(4 + 5 + 9 / 3 - 3 - 5 - 4 + max(4, min(4, 6)), 5)"), is(new ComplexNumber(4.0, 0.0)));
    }

    @Test
    public void testSqrt() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("sqrt(9)"), is(new ComplexNumber(3.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("sqrt(3+6)"), is(new ComplexNumber(3.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("sqrt(min(10,9))"), is(new ComplexNumber(3.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("sqrt(1)+sqrt(1)"), is(new ComplexNumber(2.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("sqrt(3+4i)"), is(new ComplexNumber(2.0, 1.0)));
        assertThat(calculatorService.evaluateRPN("sqrt(3*3)*sqrt(90/10)"), is(new ComplexNumber(9.0, 0.0)));
    }

    @Test
    public void testLn() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("ln(5)"), is(new ComplexNumber(1.6094379124341003, 0.0)));
        assertThat(calculatorService.evaluateRPN("ln(4+3i)"), is(new ComplexNumber(1.6094379124341003, 0.6435011087932844)));
        assertThat(calculatorService.evaluateRPN("ln(4+min(3,1))"), is(new ComplexNumber(1.6094379124341003, 0.0)));
        assertThat(calculatorService.evaluateRPN("ln(4^2)"), is(new ComplexNumber(2.772588722239781, 0.0)));
    }

    @Test
    public void testPower() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("(2+5i)^4i"), is(new ComplexNumber(0.00769869670596469, 0.003732257028178401)));
        assertThat(calculatorService.evaluateRPN("2^4"), is(new ComplexNumber(16.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("(2+3i)^0"), is(new ComplexNumber(1.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("(5+5i)^(1-4i)"), is(new ComplexNumber(119.11476166505707, -112.18848572698937)));
    }

    @Test
    public void testExp() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("exp(4+3i)"), is(new ComplexNumber(-54.051758861078156, 7.704891372731154)));
        assertThat(calculatorService.evaluateRPN("exp(8)"), is(new ComplexNumber(2980.9579870417283, 0.0)));
    }

    @Test
    public void testRemainder() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("10%5"), is(new ComplexNumber(0.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("11%5"), is(new ComplexNumber(1.0, 0.0)));
    }

    @Test(expected = ArithmeticException.class)
    public void testArithmeticException() {
        CalculatorService calculatorService = new CalculatorService();
        calculatorService.evaluateRPN("5/0");
    }

    @Test
    public void testValidatorException() {
        Validator validator = new Validator();
        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate("4+(3+5"));

        String expectedMessage = "Please check parentheses again!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test(expected = Exception.class)
    public void testException() {
        CalculatorService calculatorService = new CalculatorService();
        calculatorService.evaluateRPN("5+nfgfg");
    }
}
