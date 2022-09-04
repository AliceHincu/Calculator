package org.example;

import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.example.domain.ComplexNumber;
import org.example.service.CalculatorService;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void testExceptions() {
//        App.add(2, -3);
//        sol.convert("100 * 2 + 12");
//    }

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
    }

    @Test
    public void testMin() {
        CalculatorService calculatorService = new CalculatorService();
        assertThat(calculatorService.evaluateRPN("1+min(2,3)"), is(new ComplexNumber(3.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("1+2*min(2,3)"), is(new ComplexNumber(5.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("min((1+1i),(2+1i))"), is(new ComplexNumber(1.0, 1.0)));
        assertThat(calculatorService.evaluateRPN("min(10/2,10/5)"), is(new ComplexNumber(2.0, 0.0)));
        assertThat(calculatorService.evaluateRPN("min(10,100)/min(5,60)"), is(new ComplexNumber(2.0, 0.0)));
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
}
