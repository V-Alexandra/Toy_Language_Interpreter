package exceptions;

public class InvalidTypeException extends RuntimeException {
    @Override
    public String toString() {
        return "Invalid type" + getMessage();
    }
}
