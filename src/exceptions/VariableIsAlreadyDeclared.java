package exceptions;

public class VariableIsAlreadyDeclared extends RuntimeException {
    @Override
    public String toString() {
        return "Variable is already declared" + getMessage();
    }
}
