package model.expression;

import model.adt.MyDictionary;
import model.adt.MyIHeap;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression {
    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable, MyIHeap heap) {
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
