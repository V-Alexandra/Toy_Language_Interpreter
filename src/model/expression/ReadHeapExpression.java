package model.expression;

import model.adt.MyDictionary;
import model.adt.MyIHeap;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;
import exceptions.InvalidTypeException;

public record ReadHeapExpression(IExpression expression) implements IExpression {
    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable, MyIHeap heap) {
        IValue value = expression.evaluate(symbolTable, heap);
        if (!(value.getType() instanceof RefType))
            throw new InvalidTypeException();
        return (IValue) heap.getContent()
                .get(((RefValue) value).getAddress());
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("read heap(varName='%s')", expression.toString());
    }
}
