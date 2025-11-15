package model.type;

import model.value.IValue;

public interface IType {
    IValue getDefaultValue();
    boolean equals(Object other);
    IType deepCopy();
}
