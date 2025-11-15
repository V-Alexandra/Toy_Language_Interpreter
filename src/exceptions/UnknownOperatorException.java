package exceptions;

public class UnknownOperatorException extends RuntimeException {
    @Override
    public String toString() {
        return "Unknown operator" + getMessage();
    }
}
