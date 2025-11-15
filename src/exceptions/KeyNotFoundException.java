package exceptions;

public class KeyNotFoundException extends RuntimeException {
    @Override
    public String toString() {
        return "Key not found" + getMessage();
    }
}
