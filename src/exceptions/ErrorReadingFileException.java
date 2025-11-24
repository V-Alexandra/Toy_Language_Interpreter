package exceptions;

public class ErrorReadingFileException extends RuntimeException {
    @Override
    public String toString() {
        return "Error reading file" + getMessage();
    }
}
