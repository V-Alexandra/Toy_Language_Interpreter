package model.value;

import model.type.IType;
import model.type.RefType;

public class RefValue implements IValue {
    private final int address;
    private final IType type;
    public RefValue(int address, IType locationType) {
        this.address = address;
        this.type = locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return new RefType(this.type);
    }

    @Override
    public IValue deepCopy() {
        return new  RefValue(this.address, this.type.deepCopy());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RefValue) {
            return ((RefValue) o).getAddress() == this.address &&  ((RefValue) o).getType().equals(this.type);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + type.toString() + ")";
    }
}
