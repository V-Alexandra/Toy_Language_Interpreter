package model.expression;

import model.adt.MyDictionary;
import model.value.IValue;

public record VariableExpression(String key) implements IExpression {

    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable) {
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
