package model.expression;

import model.adt.MyDictionary;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(MyDictionary<String, IValue> symbolTable);

    String toString();

    IExpression deepCopy();
}
