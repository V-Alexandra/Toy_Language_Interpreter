package model.type;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType {
    private final IType innerType;

    public RefType(IType innerType) {
        this.innerType = innerType;
    }

    public IType getInnerType() {
        return innerType;
    }

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0, innerType);
    }

    @Override
    public IType deepCopy() {
        return new RefType(innerType.deepCopy());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RefType && this.innerType.equals(((RefType) other).innerType);
    }

    @Override
    public String toString() {
        return "Ref(" + innerType.toString() + ")";
    }
}