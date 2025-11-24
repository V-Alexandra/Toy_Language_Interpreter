package exceptions;

public class ErrorFileCloseException extends RuntimeException {
    @Override
    public String toString() {
        return "Error closing file" + getMessage();
    }
}
