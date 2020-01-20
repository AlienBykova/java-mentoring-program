import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.regex.Pattern.quote;

public class Calculator {

    public float evaluateExpression(String expression) {
        String operatorsPattern = format("[%s]", quote("+-*/"));
        String[] operations = expression.split("\\d+");
        int[] numbers = convertToIntArray(expression.split(operatorsPattern));
        return calculate(operations, numbers);
    }

    private int[] convertToIntArray(String[] numbers) {
        int[] numbersConverted = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            numbersConverted[i] = parseInt(numbers[i]);
        }
        return numbersConverted;
    }

    private float calculate(String[] operations, int[] numbers) {

        float previous = numbers[0];
        float current = numbers[1];

        String currentOperation = operations[1];

        for (int i = 2; i < operations.length; i++) {
            if (currentOperation.equals("+") || currentOperation.equals("-")) {
                if (operations[i].equals("+") || operations[i].equals("-")) {
                    previous = makeOperation(previous, current, currentOperation);
                    current = numbers[i];
                    currentOperation = operations[i];
                } else {
                    current = makeOperation(current, numbers[i], operations[i]);
                }
            } else {
                previous = makeOperation(previous, current, currentOperation);
                current = numbers[i];
                currentOperation = operations[i];
            }

        }
        return makeOperation(previous, current, currentOperation);
    }

    private float makeOperation(float number1, float number2, String operation) {
        float result = 0;
        switch (operation) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                result = number1 / number2;
                break;
            default:
                throw new UnsupportedOperationException("Not supported operation");
        }
        return result;
    }
}
