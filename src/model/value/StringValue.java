package model.value;

import model.type.IType;
import model.type.StringType;

import java.util.Objects;

public record StringValue(String value) implements IValue {

    @Override
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StringValue(String value1))) return false;
        return Objects.equals(value, value1);
    }


    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
