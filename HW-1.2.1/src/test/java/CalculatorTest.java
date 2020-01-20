import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testMixedExpression() {
        Calculator calculator = new Calculator();
        String expression = "1+10/2-4*5";
        float result = calculator.evaluateExpression(expression);
        assertEquals(-14, result, 0.0000000001);
    }

    @Test
    public void testExpression() {
        Calculator calculator = new Calculator();
        String expression = "10*10-2*4/8";
        float result = calculator.evaluateExpression(expression);
        assertEquals(99, result, 0.0000000001);
    }

    @Test
    public void testZero() {
        Calculator calculator = new Calculator();
        String expression = "10*10-50*4/2";
        float result = calculator.evaluateExpression(expression);
        assertEquals(0, result, 0.0000000001);
    }
}
