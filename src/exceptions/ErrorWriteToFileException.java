package exceptions;

public class ErrorWriteToFileException extends RuntimeException {
    @Override
    public String toString() {
        return "Error writing to file" + getMessage();
    }
}
