package model.expression;

import exceptions.InvalidTypeException;
import exceptions.UnknownOperatorException;
import model.adt.MyDictionary;
import model.adt.MyIHeap;
import model.type.IntType;
import model.value.BooleanValue;
import model.value.IValue;
import model.value.IntegerValue;

public record RelationalExpression(String operator, IExpression leftOperand, IExpression rightOperand) implements
        IExpression {
    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable, MyIHeap heap) {
        IValue leftVal = leftOperand.evaluate(symbolTable, heap);
        IValue rightVal = rightOperand.evaluate(symbolTable, heap);

        if (!leftVal.getType()
                .equals(new IntType()))
            throw new InvalidTypeException();
        if (!rightVal.getType()
                .equals(new IntType()))
            throw new InvalidTypeException();

        boolean result = isResult((IntegerValue) leftVal, (IntegerValue) rightVal);
        return new BooleanValue(result);
    }

    private boolean isResult(IntegerValue leftVal, IntegerValue rightVal) throws InvalidTypeException,
            UnknownOperatorException {
        int leftValue = leftVal.value();
        int rightValue = rightVal.value();
        if (!leftVal.getType()
                .equals(rightVal.getType()))
            throw new InvalidTypeException();
        if (!rightVal.getType()
                .equals(new IntType()))
            throw new InvalidTypeException();
        return switch (operator) {
            case "==" -> leftValue == rightValue;
            case "!=" -> leftValue != rightValue;
            case "<" -> leftValue < rightValue;
            case "<=" -> leftValue <= rightValue;
            case ">" -> leftValue > rightValue;
            case ">=" -> leftValue >= rightValue;
            default -> throw new UnknownOperatorException();
        };
    }


    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(operator, leftOperand.deepCopy(), rightOperand.deepCopy());
    }
}
