package exceptions;

public class FileAlreadyOpenException extends RuntimeException {
    @Override
    public String toString() {
        return "File already open" + getMessage();
    }
}
