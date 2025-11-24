package model.expression;

import model.adt.MyDictionary;
import model.adt.MyIHeap;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(MyDictionary<String, IValue> symbolTable, MyIHeap heap);

    String toString();

    IExpression deepCopy();
}
