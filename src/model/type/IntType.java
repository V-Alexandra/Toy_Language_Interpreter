package model.type;

import model.value.IValue;
import model.value.IntegerValue;

public class IntType implements IType {
    @Override
    public IValue getDefaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }
}
