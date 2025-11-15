package exceptions;

public class VariableNotDefinedException extends RuntimeException {
    @Override
    public String toString() {
        return "Variable not defined" + getMessage();
    }
}
