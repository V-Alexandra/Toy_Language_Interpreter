package model.value;

import model.type.IType;
import model.type.IntType;

public record IntegerValue(int value) implements IValue {
    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.format("%d", this.value);
    }

    @Override
    public int value() {
        return value;
    }

    @Override
    public IValue deepCopy() {
        return new IntegerValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerValue && ((IntegerValue) obj).value == this.value;
    }

}
