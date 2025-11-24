package model.expression;

import model.adt.MyDictionary;
import model.adt.MyIHeap;
import model.value.IValue;

public record VariableExpression(String key) implements IExpression {

    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable, MyIHeap heap) {
        return symbolTable.lookup(key);
    }

    @Override
    public String toString() {
        return this.key;
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(key);
    }
}
