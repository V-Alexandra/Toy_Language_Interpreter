package model.expression;

import exceptions.DivisionByZeroException;
import exceptions.InvalidTypeException;
import exceptions.UnknownOperatorException;
import model.adt.MyDictionary;
import model.adt.MyIHeap;
import model.value.IValue;
import model.value.IntegerValue;

public record ArithmeticExpression(char operator, IExpression leftOperand,
                                   IExpression rigthOperand) implements IExpression {


    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable, MyIHeap heap) throws InvalidTypeException, DivisionByZeroException, UnknownOperatorException {
        IValue leftValue, rightValue;

        leftValue = leftOperand.evaluate(symbolTable, heap);
        if (!(leftValue instanceof IntegerValue(int leftInt)))
            throw new InvalidTypeException();

        rightValue = rigthOperand.evaluate(symbolTable, heap);
        if (!(rightValue instanceof IntegerValue(int rightInt)))
            throw new InvalidTypeException();

        int result = switch (operator) {
            case '+' -> leftInt + rightInt;
            case '-' -> leftInt - rightInt;
            case '*' -> leftInt * rightInt;
            case '/' -> divide(leftInt, rightInt);
            default -> throw new UnknownOperatorException();
        };
        return new IntegerValue(result);
    }

    private static int divide(int leftInt, int rightInt) throws DivisionByZeroException {
        if (rightInt == 0) throw new DivisionByZeroException();
        return leftInt / rightInt;
    }

    @Override
    public String toString() {
        return leftOperand.toString() + " " + operator + " " + rigthOperand.toString();
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(operator, leftOperand.deepCopy(), rigthOperand.deepCopy());
    }
}
