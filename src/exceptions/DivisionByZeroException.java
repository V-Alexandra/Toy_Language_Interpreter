package exceptions;

public class DivisionByZeroException extends ArithmeticException {
    @Override
    public String toString() {
        return "Division by zero" + getMessage();
    }
}
