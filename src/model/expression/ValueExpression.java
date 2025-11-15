package model.expression;

import model.adt.MyDictionary;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression {
    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable) {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
