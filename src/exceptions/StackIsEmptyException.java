package exceptions;

public class StackIsEmptyException extends RuntimeException {
    @Override
    public String toString() {
        return "Stack is empty" + getMessage();
    }
}
