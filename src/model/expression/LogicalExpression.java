package model.expression;

import exceptions.InvalidTypeException;
import exceptions.UnknownOperatorException;
import model.adt.MyDictionary;
import model.value.BooleanValue;
import model.value.IValue;

public class LogicalExpression implements IExpression {
    private final IExpression leftOperand;
    private final IExpression rightOperand;
    String operator;

    public LogicalExpression(IExpression leftOperand, IExpression rightOperand, String operator) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable) throws InvalidTypeException, UnknownOperatorException{
        IValue leftValue, rightValue;
        leftValue = leftOperand.evaluate(symbolTable);
        if (!(leftValue instanceof BooleanValue(boolean leftBool)))
            throw new InvalidTypeException();
        rightValue = rightOperand.evaluate(symbolTable);
        if (!(rightValue instanceof BooleanValue(boolean rightBool)))
            throw new InvalidTypeException();
        boolean result = switch (operator) {
            case "&&" -> leftBool && rightBool;
            case "||" -> leftBool || rightBool;
            default -> throw new UnknownOperatorException();
        };
        return new BooleanValue(result);
    }

    @Override
    public String toString() {
        return leftOperand.toString() + " " + operator + " " + rightOperand.toString();
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(leftOperand.deepCopy(), rightOperand.deepCopy(), operator);
    }
}
