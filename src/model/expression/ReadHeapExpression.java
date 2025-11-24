package model.expression;

import model.adt.MyDictionary;
import model.adt.MyIHeap;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeapExpression implements IExpression{
    IExpression expression;
    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }
    @Override
    public IValue evaluate(MyDictionary<String, IValue> symbolTable, MyIHeap heap) {
        IValue value = expression.evaluate(symbolTable, heap);
        if (!(value.getType() instanceof RefType))
            throw new RuntimeException("Heap should only be accessed through references");
        return (IValue) heap.getContent().get(((RefValue) value).getAddress());
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
