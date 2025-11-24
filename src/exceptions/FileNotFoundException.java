package exceptions;

public class FileNotFoundException extends RuntimeException {
    @Override
    public String toString() {
        return "File not found" + getMessage();
    }
}
