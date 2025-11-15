package model.value;

import model.type.BooleanType;
import model.type.IType;

public record BooleanValue(boolean value) implements IValue {

    @Override
    public IType getType() {
        return new BooleanType();
    }

    @Override
    public boolean value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }

    @Override
    public IValue deepCopy() {
        return new BooleanValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanValue && ((BooleanValue) obj).value == this.value;
    }
}
